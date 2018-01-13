package com.zqlite.android.mockaweme.fragment.profile

import android.os.Bundle
import com.zqlite.android.mockaweme.R
import com.zqlite.android.mockaweme.base.BaseFragment

/**
 * Created by scott on 2018/1/12.
 */
class ProfileFragment : BaseFragment() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_profile
    }
    companion object Instance{
        fun getInstance(args: Bundle?): ProfileFragment {
            val fragment = ProfileFragment()
            if(args != null){
                fragment.arguments = args
            }
            return fragment
        }
    }
}