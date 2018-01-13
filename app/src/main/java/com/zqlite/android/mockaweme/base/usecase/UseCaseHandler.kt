package com.zqlite.android.mockaweme.base.usecase

import android.os.Handler
import android.os.Looper

/**
 * Created by scott on 2017/12/8.
 */

class UseCaseHandler(private val mUseCaseScheduler: UseCaseScheduler) {

    private val mHandler = Handler(Looper.getMainLooper())
    /**
     * 在线程池程执行 UseCase
     * @param useCase 待执行的 UseCase
     * @param values 请求参数
     * @param caseCallback UseCase 执行回调
     * @param <T> 请求类型
     * @param <R> 响应类型
    </R></T> */
    fun <T : UseCase.RequestValues, R : UseCase.ResponseValues> execute(
            useCase: UseCase<T, R>, values: T, caseCallback: UseCase.UseCaseCallback<R>) {
        useCase.requestValues = values
        useCase.useCaseCallback = UiCallbackWrapper(caseCallback, this)

        mUseCaseScheduler.execute(Runnable { useCase.run() })
    }

    /**
     * 在主线程执行 UseCase
     * @param useCase 待执行的 UseCase
     * @param values 请求参数
     * @param caseCallback UseCase 执行回调
     * @param <T> 请求类型
     * @param <R> 响应类型
    </R></T> */
    fun <T : UseCase.RequestValues, R : UseCase.ResponseValues> runOnUiThread(
            useCase: UseCase<T, R>, values: T, caseCallback: UseCase.UseCaseCallback<R>) {
        useCase.requestValues = values
        useCase.useCaseCallback = UiCallbackWrapper(caseCallback, this)
        mHandler.post { useCase.run() }
    }

    fun <V : UseCase.ResponseValues> notifyResponse(response: V, useCaseCallback: UseCase.UseCaseCallback<V>) {
        mUseCaseScheduler.notifyResponse(response, useCaseCallback)
    }

    private fun <V : UseCase.ResponseValues> notifyError(useCaseCallback: UseCase.UseCaseCallback<V>) {
        mUseCaseScheduler.onError(useCaseCallback)
    }

    private class UiCallbackWrapper<V : UseCase.ResponseValues>(private val mCallback: UseCase.UseCaseCallback<V>, private val mUseCaseHandler: UseCaseHandler) : UseCase.UseCaseCallback<V> {


        override fun onSuccess(response: V) {
            mUseCaseHandler.notifyResponse(response, mCallback)
        }

        override fun onError() {
            mUseCaseHandler.notifyError(mCallback)
        }
    }

    companion object {

        private var INSTANCE: UseCaseHandler? = null

        val instance: UseCaseHandler
            @Synchronized get() {
                if (INSTANCE == null) {
                    INSTANCE = UseCaseHandler(UseCaseThreadPoolScheduler())
                }
                return INSTANCE!!
            }
    }
}
