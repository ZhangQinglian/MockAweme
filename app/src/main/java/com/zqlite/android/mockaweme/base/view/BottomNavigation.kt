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
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.zqlite.android.mockaweme.R

/**
 * Created by scott on 2018/1/14.
 */
class BottomNavigation : RelativeLayout {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init(){
        View.inflate(context, R.layout.bottom_navigation,this)
        val tabHome = findViewById<BottomNavgationTab>(R.id.home)
        val tabFollow = findViewById<BottomNavgationTab>(R.id.follow)
        val tabMsg = findViewById<BottomNavgationTab>(R.id.msg)
        val tabMe = findViewById<BottomNavgationTab>(R.id.me)
        tabHome.setText(R.string.home)
        tabFollow.setText(R.string.follow)
        tabMsg.setText(R.string.msg)
        tabMe.setText(R.string.me)
        startProgress()
    }

    fun startProgress(){
        val progress = findViewById<BottomNavigationProgressLine>(R.id.bottom_progress)
        progress.startProgress()
    }

    fun stopProgress(){
        val progress = findViewById<BottomNavigationProgressLine>(R.id.bottom_progress)
        progress.stopProgress()
    }
}