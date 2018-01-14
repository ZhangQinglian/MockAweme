/*
 *    Copyright 2018 Qinglian.Zhang
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.zqlite.android.mockaweme.fragment.feed

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.tencent.rtmp.ITXVodPlayListener
import com.tencent.rtmp.TXLiveConstants
import com.tencent.rtmp.TXVodPlayer
import com.tencent.rtmp.ui.TXCloudVideoView
import com.zqlite.android.mockaweme.base.view.MockPlayingView
import com.zqlite.android.mockaweme.R
import com.zqlite.android.mockaweme.base.BaseFragment
import com.zqlite.android.mockaweme.entity.VideoEntity
import kotlinx.android.synthetic.main.fragment_feeds.*

/**
 * Created by scott on 2018/1/13.
 */
class FeedsFragment() : BaseFragment() {

    private var mViewModel: FeedsViewModel? = null
    private var mFeedsAdapter: VideoAdapter? = null
    private var mVideoPageAdapter: VideoPagerAdapter? = null
    private var mPlayer: TXVodPlayer? = null
    private var mVideoView: TXCloudVideoView? = null
    private var mCurrentMockPlayingView : MockPlayingView? = null
    private var mCurrentPagePosition = 0
    private var mRoomId = -1
    private var mMockPlayingCallback : MockPlayingView.Callback? = null
    private var mCallback : FeedsFragment.Callback ? = null
    interface Callback{
        fun startLoad()
        fun stopLoad()
    }

    fun setCallback(callback: Callback){
        mCallback = callback
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_feeds
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mVideoView = TXCloudVideoView(context!!)
        mPlayer = TXVodPlayer(context!!)
        videoLocaleCache()
        mFeedsAdapter = VideoAdapter()
        list.adapter = mFeedsAdapter
        val layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        list.layoutManager = layoutManager

        mVideoPageAdapter = VideoPagerAdapter()
        video_pager.adapter = mVideoPageAdapter

        video_pager.setOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener(){
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                mCurrentPagePosition = position
            }
        })

        video_pager.setPageTransformer(false) { page, position ->
            if(mCurrentPagePosition == page.id && position == 0f && mRoomId != mCurrentPagePosition){
                if(mVideoView!!.parent != null && mVideoView!!.parent is FrameLayout){
                    mPlayer!!.stopPlay(true)
                    ((mVideoView!!.parent) as FrameLayout).removeView(mVideoView)
                }
                attachVideoView(mCurrentPagePosition,page)
            }
        }


        mMockPlayingCallback = object : MockPlayingView.Callback{
            override fun onStart() {
                mPlayer!!.resume()
            }

            override fun isPlaying(): Boolean {
                return mPlayer!!.isPlaying
            }

            override fun onPause() {
                mPlayer!!.pause()
            }

        }

        mViewModel = ViewModelProviders.of(this)[FeedsViewModel::class.java]
        mViewModel?.loadVideoList(context!!)?.observe(this, Observer<MutableList<VideoEntity>> {
            mFeedsAdapter?.update(it!!.toList())
            mVideoPageAdapter?.update(it!!.toList())
        })

        bottom_navigation.startCheck(0)
    }

    private fun videoLocaleCache() {
//        val mConfig = TXVodPlayConfig()
//        mConfig.setCacheFolderPath(
//                Environment.getExternalStorageDirectory().path + "/MockAweme/.cache")
//        mConfig.setMaxCacheItems(50)
//        mPlayer!!.setConfig(mConfig)
    }
    override fun onResume() {
        super.onResume()
        resumePlay()
    }

    override fun onPause() {
        super.onPause()
        pause()

    }

    override fun onDestroy() {
        super.onDestroy()
        stopPlay()
    }

    private fun resumePlay(){
        mPlayer!!.resume()
        mCurrentMockPlayingView?.startPlay()
    }
    private fun stopPlay(){
        mPlayer!!.stopPlay(true)
        mVideoView!!.onDestroy()
    }

    private fun pause(){
        if(mPlayer!!.isPlaying){
            mPlayer!!.pause()
        }
    }
    private fun attachVideoView(position: Int,page:View){
        val container : FrameLayout = page.findViewById(R.id.container)
        container.addView(mVideoView)
        val videoEntity = mVideoPageAdapter!!.getVideoEntity(position)
        mPlayer!!.setVodListener(object : ITXVodPlayListener{
            override fun onPlayEvent(p0: TXVodPlayer?, p1: Int, p2: Bundle?) {
                if(TXLiveConstants.PLAY_EVT_PLAY_END == p1){
                    mPlayer!!.seek(0)
                    mPlayer!!.resume()
                }
                if(TXLiveConstants.PLAY_EVT_PLAY_BEGIN == p1){
                    //mCallback?.stopLoad()
                    bottom_navigation.stopProgress()
                }
                if(TXLiveConstants.PLAY_EVT_PLAY_LOADING == p1){
                    Log.d("scott","loading")
                    //mCallback?.startLoad()
                    bottom_navigation.startProgress()
                }
            }

            override fun onNetStatus(p0: TXVodPlayer?, p1: Bundle?) {
            }

        })
        mPlayer!!.setPlayerView(mVideoView)
        mPlayer!!.startPlay(videoEntity.videoUrl)
        mCallback?.startLoad()
        mRoomId = position
        mCurrentMockPlayingView = page as MockPlayingView
    }

    companion object Instance {
        fun getInstance(args: Bundle?): FeedsFragment {
            val fragment = FeedsFragment()
            if (args != null) {
                fragment.arguments = args
            }
            return fragment
        }
    }

    inner class VideoAdapter : RecyclerView.Adapter<VideoHolder>() {

        private val videoList: MutableList<VideoEntity> = mutableListOf()

        fun update(dataSet: List<VideoEntity>) {
            videoList.clear()
            videoList.addAll(dataSet)
            videoList.addAll(dataSet)
            videoList.addAll(dataSet)
            videoList.addAll(dataSet)
            videoList.addAll(dataSet)
            videoList.addAll(dataSet)
            videoList.addAll(dataSet)
            videoList.addAll(dataSet)
            videoList.addAll(dataSet)
            videoList.addAll(dataSet)
            notifyDataSetChanged()
        }

        override fun getItemCount(): Int {
            return videoList.size
        }

        override fun onBindViewHolder(holder: VideoHolder?, position: Int) {
            holder?.update(videoList[position])
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): VideoHolder {
            val view = LayoutInflater.from(context!!).inflate(R.layout.listitem_video, parent, false)
            return VideoHolder(view)
        }

    }

    inner class VideoHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val thumb: ImageView = view.findViewById(R.id.thumb)
        fun update(videoEntity: VideoEntity) {
            Glide.with(context!!).load(videoEntity.videoThumb).into(thumb)
        }
    }

    inner class VideoPagerAdapter : PagerAdapter() {
        private val videoList: MutableList<VideoEntity> = mutableListOf()

        fun update(dataSet: List<VideoEntity>) {
            videoList.clear()
            videoList.addAll(dataSet)
            videoList.addAll(dataSet)
            videoList.addAll(dataSet)
            videoList.addAll(dataSet)
            videoList.addAll(dataSet)
            videoList.addAll(dataSet)
            videoList.addAll(dataSet)
            videoList.addAll(dataSet)
            videoList.addAll(dataSet)
            videoList.addAll(dataSet)
            notifyDataSetChanged()
        }

        fun getVideoEntity(position: Int):VideoEntity{
            return videoList[position]
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        override fun getCount(): Int {
            return videoList.size
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val mockPlayingView = MockPlayingView(context!!, mMockPlayingCallback!!)
            mockPlayingView.id = position
            container.addView(mockPlayingView)
            mockPlayingView.update(videoList[position])
            return mockPlayingView
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(container.findViewById(position))
        }

    }
}