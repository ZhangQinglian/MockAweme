package com.zqlite.android.mockaweme.fragment.profile

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Rect
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.zqlite.android.mockaweme.R
import com.zqlite.android.mockaweme.base.BaseFragment
import com.zqlite.android.mockaweme.entity.VideoEntity
import kotlinx.android.synthetic.main.fragment_profile.*
import java.util.*

/**
 * Created by scott on 2018/1/12.
 */
class ProfileFragment : BaseFragment() {

    private var mProfileViewModel: ProfileViewModel? = null
    private var mAdapter: VideoAdapter? = null


    fun updateVideoEntity(videoEntity: VideoEntity) {
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
        video_list.addItemDecoration(MyAdapterDecoration())
        app_bar.addOnOffsetChangedListener { _, verticalOffset ->

            val left = app_bar.totalScrollRange + verticalOffset
            val titlebarH = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,70f,resources.displayMetrics).toInt()
            val percent :Float = (1f*(app_bar.totalScrollRange.toFloat() - left)/(app_bar.totalScrollRange-titlebarH))
            title_bar_panel.alpha = percent*2
            if(left <= titlebarH){
                mock_tab_container.visibility = View.VISIBLE
            }else{
                mock_tab_container.visibility = View.GONE
            }
        }


        mProfileViewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        mProfileViewModel?.loadVideoList(context!!)?.observe(this, Observer<MutableList<VideoEntity>> { t -> mAdapter?.update(t!!.toList()) })

        val args = arguments
        if(args != null){
            if(args["me"] as Boolean){
                Glide.with(context!!).load("http://p2c5nlwg0.bkt.clouddn.com/my_avatar.png").into(avatar)
                Glide.with(context!!).load("http://p2c5nlwg0.bkt.clouddn.com/my_avatar.png").into(big_avatar)
                follow.visibility = View.GONE
            }
        }else{
            share.visibility = View.GONE
        }
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
        init {
            val random = Random()
            if(random.nextInt(10) % 7 == 0){
                view.findViewById<View>(R.id.pop).visibility = View.VISIBLE
            }else{
                view.findViewById<View>(R.id.pop).visibility = View.GONE
            }
        }
        private val thumb: ImageView = view.findViewById(R.id.thumb)
        fun update(videoEntity: VideoEntity) {

            Glide.with(context!!).load(videoEntity.videoThumb).into(thumb)
        }
    }

    private class MyAdapterDecoration : RecyclerView.ItemDecoration(){
        override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
            val position = parent!!.getChildAdapterPosition(view)
            val padding = 2
            if(position %3 == 0){
                if(position == 0){
                    outRect!!.left = padding
                    outRect.top = padding
                    outRect.right = padding/2
                    outRect.bottom = padding/2
                }else{
                    outRect!!.left = padding
                    outRect.top = padding/2
                    outRect.right = padding/2
                    outRect.bottom = padding/2
                }
            }
            if(position %3 == 1){
                if(position == 1){
                    outRect!!.left = padding/2
                    outRect.top = padding
                    outRect.right = padding/2
                    outRect.bottom = padding/2
                }else{
                    outRect!!.left = padding/2
                    outRect.top = padding/2
                    outRect.right = padding/2
                    outRect.bottom = padding/2
                }
            }
            if(position %3 == 2){
                if(position == 2){
                    outRect!!.left = padding/2
                    outRect.top = padding
                    outRect.right = padding
                    outRect.bottom = padding/2
                }else{
                    outRect!!.left = padding/2
                    outRect.top = padding/2
                    outRect.right = padding
                    outRect.bottom = padding/2
                }
            }
        }
    }
}