package com.zqlite.android.mockaweme.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by scott on 2018/1/12.
 */
abstract class BaseFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(),container,false)
    }

    abstract fun getLayoutId():Int

    open fun onBackPress():Boolean{
        return false
    }

    open fun onHide(){}

    open fun onShow(){}

    open fun onAdd(){}

    protected fun hideFragment(fragment:BaseFragment){
        childFragmentManager.beginTransaction().hide(fragment).commit()
        fragment.onHide()
    }
    protected fun addFragment(containerId:Int,fragment: BaseFragment){
        childFragmentManager.beginTransaction().add(containerId,fragment).commit()
        fragment.onAdd()
    }

    protected fun showFragment(fragment:BaseFragment){
        childFragmentManager.beginTransaction().show(fragment).commit()
        fragment.onShow()
    }

}