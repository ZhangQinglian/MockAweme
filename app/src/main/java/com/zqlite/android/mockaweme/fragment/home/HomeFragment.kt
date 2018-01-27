package com.zqlite.android.mockaweme.fragment.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.*
import com.zqlite.android.mockaweme.R
import com.zqlite.android.mockaweme.base.BaseFragment
import com.zqlite.android.mockaweme.base.view.BottomNavigation
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
    private var mOnTabClick : BottomNavigation.OnTabClick? = null
    fun setFeedsFragmentCallback(callback:FeedsFragment.Callback){
        mFeedsFragmentCallback = callback
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mPagerAdapter = HomePagerAdapter(childFragmentManager)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        pager.adapter = mPagerAdapter
        pager.currentItem = 1
        pager.addOnPageChangeListener(object :ViewPager.SimpleOnPageChangeListener(){
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                //Log.d("scott","position = $position   positionOffset = $positionOffset   positionOffsetPixels = $positionOffsetPixels")
            }

            override fun onPageSelected(position: Int) {
                if(position != 1){
                    mPagerAdapter?.pausePlay()
                }else{
                    mPagerAdapter?.resumePlay()
                }
                if(position == 0){
                    mPagerAdapter?.startPreview()
                    hideSystemBar()
                }else{
                    mPagerAdapter?.stopPreview()
                    showSystemBar()
                }
            }
        })
        mViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        mViewModel!!.loadVideoList(context!!).observe(this, Observer<MutableList<VideoEntity>> {

        })
    }

    fun setOnTabClickCallback(callback:BottomNavigation.OnTabClick?){
        mOnTabClick = callback
    }

    fun hideInnerBottomNav(){
        mPagerAdapter!!.hideInnerBottomNav()
    }
    fun showInnerBottomNav(){
        mPagerAdapter!!.showInnerBottomNav()
    }
    fun hideSystemBar(){
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    fun showSystemBar(){
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }
    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }
    override fun onHide() {
        mPagerAdapter?.pausePlay()
    }

    override fun onShow() {
        mPagerAdapter?.resumePlay()
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
                override fun videoSelected(videoEntity: VideoEntity) {
                    profileFragment.updateVideoEntity(videoEntity)
                }

                override fun startLoad() {
                    //mFeedsFragmentCallback?.startLoad()
                }

                override fun stopLoad() {
                    //mFeedsFragmentCallback?.stopLoad()
                }

            })
            feedsFragment.setTabClickCallback(mOnTabClick)
            cameraFragment.setCallback(object :CameraFragment.Callback{
                override fun onBackPress() {
                    pager.setCurrentItem(1,true)
                }
            })
        }

        fun startPreview(){
            cameraFragment.startPreview()
        }

        fun stopPreview(){
            cameraFragment.stopPreview()
        }
        fun hideInnerBottomNav(){
            feedsFragment.hideInnerBottomNav()
        }
        fun showInnerBottomNav(){
            feedsFragment.showInnerBottomNav()
        }
        fun pausePlay(){
            feedsFragment.pause()
        }
        fun resumePlay(){
            feedsFragment.resumePlay()
        }
        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return 3
        }

    }
}