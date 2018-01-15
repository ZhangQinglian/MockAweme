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

    private var mOnTabClick: OnTabClick? = null

    private var mHomeTab: BottomNavgationTab? = null
    private var mFollowTab: BottomNavgationTab? = null
    private var mMsgTab: BottomNavgationTab? = null
    private var mMeTab: BottomNavgationTab? = null

    interface OnTabClick {
        fun onTabClick(index: Int)
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    public fun setCallback(callback:OnTabClick?){
        mOnTabClick = callback
    }
    fun startCheck(index:Int){
        innerClick(index)
    }
    private fun init() {
        View.inflate(context, R.layout.bottom_navigation, this)
        mHomeTab = findViewById(R.id.home)
        mFollowTab = findViewById(R.id.follow)
        mMsgTab = findViewById(R.id.msg)
        mMeTab = findViewById(R.id.me)

        mHomeTab?.setText(R.string.home)
        mFollowTab?.setText(R.string.follow)
        mMsgTab?.setText(R.string.msg)
        mMeTab?.setText(R.string.me)
        mHomeTab?.setOnClickListener {
            mOnTabClick?.onTabClick(0)
            innerClick(0)
        }
        mFollowTab?.setOnClickListener {
            mOnTabClick?.onTabClick(1)
            innerClick(1)
        }
        mMsgTab?.setOnClickListener {
            mOnTabClick?.onTabClick(2)
            innerClick(2)
        }
        mMeTab?.setOnClickListener {
            mOnTabClick?.onTabClick(3)
            innerClick(3)
        }

        startProgress()
    }

    private fun innerClick(index: Int) {
        when (index) {
            0 -> {
                mHomeTab?.check(R.drawable.a8j)
                mFollowTab?.unCheck()
                mMsgTab?.unCheck()
                mMeTab?.unCheck()
            }
            1 -> {
                mHomeTab?.unCheck()
                mFollowTab?.check(null)
                mMsgTab?.unCheck()
                mMeTab?.unCheck()
            }
            2 -> {
                mHomeTab?.unCheck()
                mFollowTab?.unCheck()
                mMsgTab?.check(null)
                mMeTab?.unCheck()
            }
            3 -> {
                mHomeTab?.unCheck()
                mFollowTab?.unCheck()
                mMsgTab?.unCheck()
                mMeTab?.check(null)
            }
        }
    }

    fun startProgress() {
        val progress = findViewById<BottomNavigationProgressLine>(R.id.bottom_progress)
        progress.startProgress()
    }

    fun stopProgress() {
        val progress = findViewById<BottomNavigationProgressLine>(R.id.bottom_progress)
        progress.stopProgress()
    }
}