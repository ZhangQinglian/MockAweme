package com.zqlite.android.mockaweme.base.usecase

import android.os.Handler

import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * Created by scott on 2017/12/8.
 */

class UseCaseThreadPoolScheduler : UseCaseScheduler {

    private val mHandler = Handler()

    internal var mThreadPoolExecutor: ThreadPoolExecutor

    init {
        mThreadPoolExecutor = ThreadPoolExecutor(POOL_SIZE, MAX_POOL_SIZE, TIME_OUT.toLong(), TimeUnit.SECONDS, ArrayBlockingQueue(POOL_SIZE))
    }

    override fun execute(runnable: Runnable) {
        mThreadPoolExecutor.execute(runnable)
    }

    override fun <V : UseCase.ResponseValues> notifyResponse(response: V, useCaseCallback: UseCase.UseCaseCallback<V>) {
        mHandler.post { useCaseCallback.onSuccess(response) }
    }

    override fun <V : UseCase.ResponseValues> onError(useCaseCallback: UseCase.UseCaseCallback<V>) {
        mHandler.post { useCaseCallback.onError() }
    }

    companion object {

        val POOL_SIZE = 2

        val MAX_POOL_SIZE = 4

        val TIME_OUT = 30
    }
}
