package com.zqlite.android.mockaweme.fragment.feed

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.zqlite.android.mockaweme.R
import com.zqlite.android.mockaweme.base.BaseFragment
import com.zqlite.android.mockaweme.entity.VideoEntity
import kotlinx.android.synthetic.main.fragment_feeds.*
/**
 * Created by scott on 2018/1/13.
 */
class FeedsFragment : BaseFragment() {

    private var mViewModel : FeedsViewModel? = null
    private var mAdapter : VideoAdapter? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_feeds
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mAdapter = VideoAdapter()
        list.adapter = mAdapter
        val layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
        list.layoutManager = layoutManager

        mViewModel = ViewModelProviders.of(this)[FeedsViewModel::class.java]
        mViewModel?.loadVideoList(context!!)?.observe(this, Observer<MutableList<VideoEntity>> {
            mAdapter?.update(it!!.toList())
        })
    }

    companion object Instance{
        fun getInstance(args: Bundle?): FeedsFragment {
            val fragment = FeedsFragment()
            if(args != null){
                fragment.arguments = args
            }
            return fragment
        }
    }

    inner class VideoAdapter : RecyclerView.Adapter<VideoHolder>(){

        private val videoList : MutableList<VideoEntity> = mutableListOf()

        fun update(dataSet:List<VideoEntity>){
            videoList.clear()
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
            val view =  LayoutInflater.from(context!!).inflate(R.layout.listitem_video,parent,false)
            return VideoHolder(view)
        }

    }

    inner class VideoHolder(view:View) : RecyclerView.ViewHolder(view){
        private val thumb :ImageView = view.findViewById(R.id.thumb)
        fun update(videoEntity: VideoEntity){
            Glide.with(context!!).load(videoEntity.videoThumb).into(thumb)
        }
    }
}