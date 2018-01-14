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
import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.bottom_navigation_tab.*
import com.zqlite.android.mockaweme.R
import kotlinx.android.synthetic.main.bottom_navigation_tab.view.*

/**
 * Created by scott on 2018/1/14.
 */
class BottomNavgationTab : FrameLayout {

    private var mChecked = false
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
        View.inflate(context, R.layout.bottom_navigation_tab, this)
    }

    fun setText(txtId:Int){
        val textView = findViewById<TextView>(R.id.text)
        textView.setText(txtId)
    }

    fun check(drawableId:Int?){
        if(mChecked && drawableId != null){
            icon.animate().rotationBy(-360f).setDuration(600).start()
        }
        if(drawableId == null){
            popAnimation(text)
            text.setTextColor(resources.getColor(R.color.bottom_nav_tab_text_select))
        }else{
            icon.setImageDrawable(resources.getDrawable(drawableId))
            icon.animate().scaleY(1f).scaleX(1f).alpha(1f).setDuration(200).start()
            text.animate().scaleX(0f).scaleY(0f).alpha(0f).setDuration(200).start()
        }
        line.animate().translationY(0f).setDuration(200).start()
        mChecked = true

    }
    fun unCheck(){
        text.setTextColor(resources.getColor(R.color.bottom_nav_tab_text_normal))
        val h = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,2f,resources.displayMetrics)
        line.animate().translationY(h).setDuration(200).start()
        icon.animate().scaleY(0f).scaleX(0f).alpha(0f).setDuration(200).start()
        text.animate().scaleX(1f).scaleY(1f).alpha(1f).setDuration(200).start()
        mChecked = false
    }
    private fun popAnimation(view: View) {
        val animator = ObjectAnimator.ofFloat(1f, 1.05f, 1f)
        animator.setDuration(300).addUpdateListener { animation ->
            val v = animation.animatedValue as Float
            view.scaleX = v
            view.scaleY = v
        }
        animator.start()
    }
}