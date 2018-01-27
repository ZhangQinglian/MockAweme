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
import com.zqlite.android.mockaweme.base.view.BottomNavigation
import com.zqlite.android.mockaweme.entity.VideoEntity
import com.zqlite.android.mockaweme.fragment.feed.FeedsFragment
import com.zqlite.android.mockaweme.fragment.follow.FollowFragment
import com.zqlite.android.mockaweme.fragment.home.HomeFragment
import com.zqlite.android.mockaweme.fragment.profile.ProfileFragment
import kotlinx.android.synthetic.main.fragment_main.*
/**
 * Created by scott on 2018/1/14.
 */
class MainFragment : BaseFragment() {



    val mHomeFragment = HomeFragment.getInstance(null)
    val mMeFragment :ProfileFragment
    private val mFollowFragment:FollowFragment
    init {
        val args = Bundle()
        args.putBoolean("me",true)
        mMeFragment = ProfileFragment.getInstance(args)
        mFollowFragment = FollowFragment.getInstance(null)
    }
    override fun getLayoutId(): Int {
        return R.layout.fragment_main
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mHomeFragment.setFeedsFragmentCallback(object :FeedsFragment.Callback{
            override fun videoSelected(videoEntity: VideoEntity) {

            }

            override fun stopLoad() {
                //bottom_navigation.stopProgress()
            }

            override fun startLoad() {
                //bottom_navigation.startProgress()
            }

        })

        addFragment(R.id.container,mMeFragment)
        hideFragment(mMeFragment)
        addFragment(R.id.container,mFollowFragment)
        hideFragment(mFollowFragment)
        addFragment(R.id.container,mHomeFragment)
        mHomeFragment.setOnTabClickCallback(object :BottomNavigation.OnTabClick{
            override fun onTabClick(index: Int) {
                if(index > 0){
                    bottom_navigation.visibility = View.VISIBLE
                    bottom_navigation.startCheck(index)
                    mHomeFragment.hideInnerBottomNav()
                }
            }
        })
        bottom_navigation.setCallback(object :BottomNavigation.OnTabClick{
            override fun onTabClick(index: Int) {
                if(index == 0){
                    bottom_navigation.visibility = View.GONE
                    mHomeFragment.showInnerBottomNav()
                    hideFragment(mMeFragment)
                    hideFragment(mFollowFragment)
                    showFragment(mHomeFragment)
                }
                if(index == 1){
                    hideFragment(mHomeFragment)
                    hideFragment(mMeFragment)
                    showFragment(mFollowFragment)
                }
                if(index ==3 ){
                    hideFragment(mHomeFragment)
                    hideFragment(mFollowFragment)
                    showFragment(mMeFragment)
                }
            }

        })
    }

    override fun onBackPress(): Boolean {
        return mHomeFragment.onBackPress()
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