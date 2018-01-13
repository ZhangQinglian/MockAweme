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

package com.zqlite.android.mockaweme.base.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.zqlite.android.mockaweme.R
import com.zqlite.android.mockaweme.entity.VideoEntity
import kotlinx.android.synthetic.main.view_mock_playing.view.*

/**
 * Created by scott on 2018/1/13.
 */
class MockPlayingView(context: Context,val callback: Callback) : RelativeLayout(context) {

    interface Callback{
        fun onPause()
        fun onStart()
        fun isPlaying():Boolean
    }

    init {
        initView()
    }

    fun startPlay(){
        pause.visibility = View.GONE
        pause.scaleX = 2.5f
        pause.scaleY = 2.5f
    }

    private fun initView(){
        inflate(context, R.layout.view_mock_playing,this)
        pause.alpha = 0.3f
        pause.scaleX = 2.5f
        pause.scaleY = 2.5f
        setOnClickListener {
            if(callback.isPlaying()){
                callback.onPause()
                pause.animate().scaleX(1.0f).scaleY(1.0f).setDuration(200).setListener(object :Animator.AnimatorListener{
                    override fun onAnimationRepeat(p0: Animator?) {

                    }

                    override fun onAnimationEnd(p0: Animator?) {
                    }

                    override fun onAnimationCancel(p0: Animator?) {
                    }

                    override fun onAnimationStart(p0: Animator?) {
                        pause.visibility = View.VISIBLE

                    }

                }).start()
            }else{
                callback.onStart()
                pause.visibility = View.GONE
                pause.scaleX = 2.5f
                pause.scaleY = 2.5f
            }
        }
        like.setOnClickListener { popAnimation(like) }
        comment.setOnClickListener { popAnimation(comment) }
        share.setOnClickListener { popAnimation(share) }
        cd.setOnClickListener { popAnimation(cd) }
        avatar.setOnClickListener { popAnimation(avatar) }
        val rotation = ObjectAnimator.ofFloat(cd,View.ROTATION,0.0f,360.0f)
        rotation.repeatCount = Animation.INFINITE
        rotation.repeatMode = ValueAnimator.RESTART
        rotation.duration = 9000
        rotation.interpolator = LinearInterpolator()
        rotation.start()
        val time = ValueAnimator.ofInt(0,1)
        time.duration = 900
        time.repeatCount = Animation.INFINITE
        time.repeatMode = ValueAnimator.RESTART
        time.addListener(object :AnimatorListenerAdapter(){
            override fun onAnimationRepeat(animation: Animator?) {
                addMusic()
            }
        })
        time.start()
    }

    fun update(videoEntity: VideoEntity){
        Glide.with(this).load(videoEntity.videoThumb).into(thumb)
        Glide.with(this).load(videoEntity.videoThumb).into(avatar)
        Glide.with(this).load(videoEntity.videoThumb).into(mini_avatar)
        val x = cd.x + cd.width/2
        val y = cd.y + cd.height/2
        Log.d("scott", "x = $x  y = $y")


    }

    var musicCount = 0
    private fun addMusic(){
        val autoMove = AutoMoveImage(context)
        val lp = RelativeLayout.LayoutParams(20,20)
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
        lp.rightMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,45f,resources.displayMetrics).toInt()
        lp.bottomMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,50f,resources.displayMetrics).toInt()
        autoMove.layoutParams = lp
        buttonPanel.addView(autoMove)
        if(musicCount%2 == 1){
            autoMove.init(R.drawable.sc)
        }else{
            autoMove.init(R.drawable.sd)
        }
        musicCount ++
    }

    private fun popAnimation(view: View) {
        val animator = ObjectAnimator.ofFloat(1f, 0.8f, 1f)
        animator.setDuration(500).addUpdateListener { animation ->
            val v = animation.animatedValue as Float
            view.scaleX = v
            view.scaleY = v
        }
        animator.interpolator = OvershootInterpolator()
        animator.start()
    }
}