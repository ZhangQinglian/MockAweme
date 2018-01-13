package com.zqlite.android.mockaweme.fragment.feed

import android.os.Bundle
import com.zqlite.android.mockaweme.R
import com.zqlite.android.mockaweme.base.BaseFragment

/**
 * Created by scott on 2018/1/13.
 */
class FeedsFragment : BaseFragment() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_feeds
    }

    companion object Instance{
        fun getInstance(args: Bundle?): FeedsFragment {
            val fragment = FeedsFragment()
            if(args != null){
                fragment.arguments = args
            }
            return fragment
        }
    }
}