package com.zqlite.android.mockaweme.fragment.profile

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.zqlite.android.mockaweme.R
import com.zqlite.android.mockaweme.base.BaseFragment
import com.zqlite.android.mockaweme.entity.VideoEntity
import kotlinx.android.synthetic.main.fragment_profile.*

/**
 * Created by scott on 2018/1/12.
 */
class ProfileFragment : BaseFragment() {

    private var mProfileViewModel: ProfileViewModel? = null
    private var mAdapter: VideoAdapter? = null


    fun updateVideoEntity(videoEntity: VideoEntity){
        Glide.with(context!!).load(videoEntity.videoThumb).into(avatar)
        Glide.with(context!!).load(videoEntity.videoThumb).into(big_avatar)
        video_list.scrollToPosition(0)
        app_bar.setExpanded(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mAdapter = VideoAdapter()
        video_list.adapter = mAdapter
        val layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
        video_list.layoutManager = layoutManager

        mProfileViewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        mProfileViewModel?.loadVideoList(context!!)?.observe(this, Observer<MutableList<VideoEntity>> { t -> mAdapter?.update(t!!.toList()) })
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_profile
    }

    companion object Instance {
        fun getInstance(args: Bundle?): ProfileFragment {
            val fragment = ProfileFragment()
            if (args != null) {
                fragment.arguments = args
            }
            return fragment
        }
    }

    inner class VideoAdapter : RecyclerView.Adapter<ProfileFragment.VideoHolder>() {

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

        override fun onBindViewHolder(holder: ProfileFragment.VideoHolder?, position: Int) {
            holder?.update(videoList[position])
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ProfileFragment.VideoHolder {
            val view = LayoutInflater.from(context!!).inflate(R.layout.listitem_my_video, parent, false)
            return VideoHolder(view)
        }

    }

    inner class VideoHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val thumb: ImageView = view.findViewById(R.id.thumb)
        fun update(videoEntity: VideoEntity) {
            Glide.with(context!!).load(videoEntity.videoThumb).into(thumb)
        }
    }
}