package com.zqlite.android.mockaweme.fragment.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.View
import com.zqlite.android.mockaweme.R
import com.zqlite.android.mockaweme.base.BaseFragment
import com.zqlite.android.mockaweme.entity.VideoEntity
import com.zqlite.android.mockaweme.fragment.feed.FeedsFragment
import com.zqlite.android.mockaweme.fragment.camera.CameraFragment
import com.zqlite.android.mockaweme.fragment.profile.ProfileFragment
import kotlinx.android.synthetic.main.fragment_home.*
/**
 * Created by scott on 2018/1/12.
 */
class HomeFragment : BaseFragment() {

    private var mPagerAdapter : HomePagerAdapter? = null
    private var mViewModel : HomeViewModel? = null
    private var mFeedsFragmentCallback : FeedsFragment.Callback? = null

    fun setFeedsFragmentCallback(callback:FeedsFragment.Callback){
        mFeedsFragmentCallback = callback
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mPagerAdapter = HomePagerAdapter(childFragmentManager)
        pager.adapter = mPagerAdapter
        pager.currentItem = 1
        pager.addOnPageChangeListener(object :ViewPager.SimpleOnPageChangeListener(){
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                Log.d("scott","position = $position   positionOffset = $positionOffset   positionOffsetPixels = $positionOffsetPixels")
            }
        })
        mViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        mViewModel!!.loadVideoList(context!!).observe(this, Observer<MutableList<VideoEntity>> {

        })
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    companion object Instance{
        fun getInstance(args:Bundle?):HomeFragment{
            val fragment = HomeFragment()
            if(args != null){
                fragment.arguments = args
            }
            return fragment
        }
    }

    inner class HomePagerAdapter(fm:FragmentManager) : FragmentPagerAdapter(fm){

        private val fragments : MutableList<Fragment> = mutableListOf()
        private val feedsFragment = FeedsFragment.getInstance(null)
        private val cameraFragment = CameraFragment.getInstance(null)
        private val profileFragment = ProfileFragment.getInstance(null)
        init {
            fragments.add(cameraFragment)
            fragments.add(feedsFragment)
            fragments.add(profileFragment)
            feedsFragment.setCallback(object :FeedsFragment.Callback{
                override fun startLoad() {
                    //mFeedsFragmentCallback?.startLoad()
                }

                override fun stopLoad() {
                    //mFeedsFragmentCallback?.stopLoad()
                }

            })
        }

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return 3
        }

    }
}