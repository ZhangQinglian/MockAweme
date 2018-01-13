package com.zqlite.android.mockaweme

import android.content.Context
import android.widget.ImageView
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.zqlite.android.mockaweme.entity.VideoEntity

/**
 * Created by scott on 2018/1/13.
 */
class MockPlayingView(context: Context) : RelativeLayout(context) {

    init {
        initView()
    }

    private fun initView(){
        inflate(context,R.layout.view_mock_playing,this)
    }

    fun update(videoEntity: VideoEntity){
        val imageView : ImageView = findViewById(R.id.thumb)
        Glide.with(this).load(videoEntity.videoThumb).into(imageView)
    }
}