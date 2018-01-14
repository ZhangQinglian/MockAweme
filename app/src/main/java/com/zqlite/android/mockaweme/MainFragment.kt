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

package com.zqlite.android.mockaweme

import android.os.Bundle
import android.view.View
import com.zqlite.android.mockaweme.base.BaseFragment
import com.zqlite.android.mockaweme.fragment.feed.FeedsFragment
import com.zqlite.android.mockaweme.fragment.home.HomeFragment
import kotlinx.android.synthetic.main.fragment_main.*
/**
 * Created by scott on 2018/1/14.
 */
class MainFragment : BaseFragment() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_main
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val homeFragment = HomeFragment.getInstance(null)
        homeFragment.setFeedsFragmentCallback(object :FeedsFragment.Callback{
            override fun stopLoad() {
                //bottom_navigation.stopProgress()
            }

            override fun startLoad() {
                //bottom_navigation.startProgress()
            }

        })
        childFragmentManager.beginTransaction().add(R.id.container,homeFragment).commit()
    }

    companion object Instance{
        fun getInstance(args:Bundle?): MainFragment {
            val fragment = MainFragment()
            if(args != null){
                fragment.arguments = args
            }
            return fragment
        }
    }
}