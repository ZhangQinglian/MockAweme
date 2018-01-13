package com.zqlite.android.mockaweme.base.usecase

/**
 * Created by scott on 2017/12/8.
 */

abstract class UseCase<Q : UseCase.RequestValues, P : UseCase.ResponseValues> {

    var requestValues: Q? = null

    var useCaseCallback: UseCaseCallback<P>? = null

    internal fun run() {
        executeUseCase(requestValues)
    }

    protected abstract fun executeUseCase(requestValues: Q?)

    interface RequestValues

    interface ResponseValues

    interface UseCaseCallback<R> {
        fun onSuccess(response: R)
        fun onError()
    }
}
