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

package com.zqlite.android.mockaweme.fragment.follow

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.zqlite.android.mockaweme.base.usecase.UseCase
import com.zqlite.android.mockaweme.base.usecase.UseCaseHandler
import com.zqlite.android.mockaweme.entity.VideoEntity
import com.zqlite.android.mockaweme.usecase.GetResUseCase

/**
 * Created by scott on 2018/1/26.
 */
class FollowViewModel : ViewModel() {

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

    fun getVideoList(): LiveData<MutableList<VideoEntity>> {
        return videoList
    }
}