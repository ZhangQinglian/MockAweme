package com.zqlite.android.mockaweme.fragment.camera

import android.os.Bundle
import com.zqlite.android.mockaweme.R
import com.zqlite.android.mockaweme.base.BaseFragment

/**
 * Created by scott on 2018/1/12.
 */
class CameraFragment : BaseFragment() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_camera
    }

    companion object Instance{
        fun getInstance(args: Bundle?): CameraFragment {
            val fragment = CameraFragment()
            if(args != null){
                fragment.arguments = args
            }
            return fragment
        }
    }
}