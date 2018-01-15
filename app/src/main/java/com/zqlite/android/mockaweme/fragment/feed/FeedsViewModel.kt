package com.zqlite.android.mockaweme.fragment.feed

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.zqlite.android.mockaweme.base.usecase.UseCase
import com.zqlite.android.mockaweme.base.usecase.UseCaseHandler
import com.zqlite.android.mockaweme.entity.VideoEntity
import com.zqlite.android.mockaweme.usecase.GetResUseCase

/**
 * Created by scott on 2018/1/13.
 */
class FeedsViewModel:ViewModel(){

    private val videoList : MutableLiveData<MutableList<VideoEntity>> = MutableLiveData()

    fun loadVideoList(context: Context) {

        val getVideoUseCase = GetResUseCase()
        val requestValue = GetResUseCase.RequestValues(context)
        UseCaseHandler.instance.execute(getVideoUseCase,requestValue,object : UseCase.UseCaseCallback<GetResUseCase.ResponseValues>{
            override fun onSuccess(response: GetResUseCase.ResponseValues) {
                videoList.value = response.videoList
            }

            override fun onError() {
            }

        })

    }

    fun getVideoList(): LiveData<MutableList<VideoEntity>>{
        return videoList
    }
}