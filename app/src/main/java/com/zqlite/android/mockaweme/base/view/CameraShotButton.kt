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

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.LinearInterpolator

/**
 * Created by scott on 2018/1/27.
 */
class CameraShotButton :View {

    private val paintWhite : Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paintGreen : Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var w = 0
    private var h = 0
    private var animation:ValueAnimator
    private var degree = 0
    private var rectF : RectF?=null

    private var callback:Callback? = null
    interface Callback{
        fun onPress()
        fun onRelease()
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        paintWhite.apply {
            color = Color.WHITE
            style = Paint.Style.FILL_AND_STROKE
        }
        paintGreen.apply {
            color = Color.parseColor("#22C7FC")
            style = Paint.Style.FILL_AND_STROKE
        }
        viewTreeObserver.addOnPreDrawListener(object:ViewTreeObserver.OnPreDrawListener{
            override fun onPreDraw(): Boolean {
                w = width
                h = height
                viewTreeObserver.removeOnPreDrawListener(this)
                rectF = RectF(0f,0f,w.toFloat(),h.toFloat())
                return false
            }

        })
        animation = ValueAnimator.ofFloat(0.0F, 360.0F)
        animation.apply {
            duration = 15000
            interpolator = LinearInterpolator()
            addUpdateListener {
                Log.d("scott","animation value = ${it.animatedValue}")
                degree = (it.animatedValue as Float).toInt()
                invalidate()
            }
        }
    }

    public fun setCallback(callback: Callback){
        this.callback = callback
    }
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action){
            MotionEvent.ACTION_DOWN->{
                Log.d("scott","CameraShotButton  press")
                if(animation.isRunning) animation.cancel()
                animation.start()
                animate().scaleX(1.2F).scaleY(1.2F).setDuration(300).start()
                callback?.onPress()
                return true
            }
            MotionEvent.ACTION_UP->{
                Log.d("scott","CameraShotButton  release")
                if(animation.isRunning) animation.cancel()
                callback?.onRelease()
                return true
            }
        }
        return super.onTouchEvent(event)
    }
    override fun onDraw(canvas: Canvas?) {
        canvas?.drawArc(rectF,-90F,degree.toFloat(),true,paintGreen)
        canvas?.drawCircle(w/2.toFloat(),h/2.toFloat(),(w/2.toFloat())/5*4,paintWhite)
    }
}