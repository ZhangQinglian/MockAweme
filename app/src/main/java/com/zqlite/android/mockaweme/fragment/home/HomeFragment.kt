package com.zqlite.android.mockaweme.fragment.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mPagerAdapter = HomePagerAdapter(childFragmentManager)
        pager.adapter = mPagerAdapter
        pager.currentItem = 1

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

        init {
            fragments.add(CameraFragment.getInstance(null))
            fragments.add(FeedsFragment.getInstance(null))
            fragments.add(ProfileFragment.getInstance(null))
        }

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return 3
        }

    }
}