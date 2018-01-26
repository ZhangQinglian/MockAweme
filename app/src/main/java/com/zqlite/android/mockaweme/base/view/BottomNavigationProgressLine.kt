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

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.util.AttributeSet
import android.view.View
import android.view.ViewTreeObserver

/**
 * Created by scott on 2018/1/14.
 */
class BottomNavigationProgressLine : View {

    var h = 0
    var w = 0
    val mainHandler = Handler()
    val fps: Long = 1000 / 60
    var progressDrawing = false
    var paint = Paint()
    var paintBG = Paint()
    var currentProgress = 0
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        paint.color = Color.WHITE
        paint.strokeWidth = 6f
        paintBG.color = Color.parseColor("#77eeeeee")
        paintBG.strokeWidth = 6f
        viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                h = height
                w = width
                currentProgress = w/8
                viewTreeObserver.removeOnPreDrawListener(this)
                return false
            }

        })
    }

    fun startProgress() {
        progressDrawing = true
        mainHandler.postDelayed(object : Runnable {
            override fun run() {
                currentProgress +=12
                if(currentProgress > w/2) currentProgress = 0
                invalidate()
                if (!progressDrawing) return
                mainHandler.postDelayed(this,fps)
            }

        }, fps)
    }

    fun stopProgress() {
        progressDrawing = false
        currentProgress = 0
    }

    override fun onDraw(canvas: Canvas?) {
        canvas!!.drawLine(0f,0f,w*1f,0f,paintBG)
        if(progressDrawing){
            canvas!!.drawLine(w/2f-currentProgress,0f,w/2f+currentProgress,0f,paint)
        }
    }
}