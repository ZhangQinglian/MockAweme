package com.zqlite.android.mockaweme.base.usecase

/**
 * Created by scott on 2017/12/8.
 */

interface UseCaseScheduler {

    fun execute(runnable: Runnable)

    fun <V : UseCase.ResponseValues> notifyResponse(response: V, useCaseCallback: UseCase.UseCaseCallback<V>)

    fun <V : UseCase.ResponseValues> onError(useCaseCallback: UseCase.UseCaseCallback<V>)
}
