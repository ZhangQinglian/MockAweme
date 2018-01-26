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

package com.zqlite.android.mockaweme.fragment.follow

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Rect
import android.media.Image
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.zqlite.android.mockaweme.R
import com.zqlite.android.mockaweme.base.BaseFragment
import com.zqlite.android.mockaweme.entity.VideoEntity
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_feeds.*
import kotlinx.android.synthetic.main.listitem_video.*

/**
 * Created by scott on 2018/1/26.
 */
class FollowFragment : BaseFragment() {

    private var mViewModel :FollowViewModel? = null
    private val mFollowAdapter = VideoAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list.adapter = mFollowAdapter
        val layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        list.layoutManager = layoutManager
        list.addItemDecoration(FollowDecoration())

        swipe.isRefreshing = true
        swipe.setProgressViewOffset(false, 100, 250)
        swipe.setOnRefreshListener {
            loadVideos()
        }
        mViewModel = ViewModelProviders.of(this)[FollowViewModel::class.java]
        mViewModel?.getVideoList()?.observe(this, Observer<MutableList<VideoEntity>> {
            Log.d("scott", it!![0].videoUrl)
            mFollowAdapter?.update(it!!.toList())
            swipe.isRefreshing = false
        })
        loadVideos()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_follow
    }

    private fun loadVideos() {
        Log.d("scott", "load videos")
        mViewModel?.loadVideoList(context!!)
    }

    companion object Instance {
        fun getInstance(args: Bundle?): FollowFragment {
            val fragment = FollowFragment()
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
        private val avatar:CircleImageView = view.findViewById(R.id.avatar)
        fun update(videoEntity: VideoEntity) {
            Glide.with(context!!).load(videoEntity.videoThumb).into(thumb)
            Glide.with(context!!).load(videoEntity.videoThumb).into(avatar)
        }
    }

    inner class FollowDecoration : RecyclerView.ItemDecoration(){

        override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
            val position = parent!!.getChildAdapterPosition(view)
            val offset = 2
            if (position % 2 == 0){
                outRect!!.left = offset
                outRect.top = offset/2
                outRect.right = offset/2
                outRect.left = offset/2
            }
            if(position % 2 == 1){
                outRect!!.left = offset/2
                outRect.top = offset/2
                outRect.right = offset
                outRect.left = offset/2
            }
            if(position < 2){
                outRect!!.top = offset
            }
        }
    }
}