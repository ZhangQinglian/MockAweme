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
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.support.v4.view.animation.FastOutLinearInInterpolator
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.ImageView

/**
 * Created by scott on 2018/1/13.
 */
class AutoMoveImage(context: Context) : ImageView (context){

    fun init(drawableId:Int){
        setImageDrawable(context.resources.getDrawable(drawableId))
        startMove()
    }

    private fun startMove(){

        val animatorX1 = ObjectAnimator.ofFloat(this,View.TRANSLATION_X,0f,-100f).setDuration(1500)

        val animatorX2 = if(System.currentTimeMillis() % 2 == 0L){
            ObjectAnimator.ofFloat(this,View.TRANSLATION_X,-100f,-130f).setDuration(2000)
        }else{
            ObjectAnimator.ofFloat(this,View.TRANSLATION_X,-100f,-70f).setDuration(2000)
        }
        val animatorY = ObjectAnimator.ofFloat(this,View.TRANSLATION_Y,0f,-200f).setDuration(3000)
        val animatorAlpha1 = ObjectAnimator.ofFloat(this,View.ALPHA,0f,1f).setDuration(1500)
        val animatorAlpha2 = ObjectAnimator.ofFloat(this,View.ALPHA,1f,0f).setDuration(1500)
        var animatorRotation = if(System.currentTimeMillis() % 2 == 0L){
            ObjectAnimator.ofFloat(this,View.ROTATION,0f,30f).setDuration(3000)
        }else{
            ObjectAnimator.ofFloat(this,View.ROTATION,0f,-30f).setDuration(3000)
        }
        val animationScaleX = ObjectAnimator.ofFloat(this,View.SCALE_X,1f,3f).setDuration(3000)
        val animationScaleY = ObjectAnimator.ofFloat(this,View.SCALE_Y,1f,3f).setDuration(3000)
        val set = AnimatorSet()
        set.playTogether(animatorY,animatorRotation,animationScaleX,animationScaleY)
        set.playSequentially(animatorAlpha1,animatorAlpha2)
        set.playSequentially(animatorX1,animatorX2)
        set.start()
        set.addListener(object :AnimatorListenerAdapter(){
            override fun onAnimationEnd(animation: Animator?) {
                (parent as ViewGroup).removeView(this@AutoMoveImage)
            }
        })
    }
}