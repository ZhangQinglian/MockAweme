package com.zqlite.android.mockaweme.usecase

import android.content.Context
import android.util.Log
import com.android.volley.Response
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.zqlite.android.mockaweme.base.usecase.UseCase
import com.zqlite.android.mockaweme.entity.VideoEntity
import com.zqlite.android.mockaweme.repository.NetRepo
import org.json.JSONObject

/**
 * Created by scott on 2018/1/13.
 */
class GetResUseCase : UseCase<GetResUseCase.RequestValues, GetResUseCase.ResponseValues>(){

    override fun executeUseCase(requestValues: GetResUseCase.RequestValues?) {

        val netRepo = NetRepo(requestValues!!.context)
        netRepo.getVideoList(Response.Listener {
            val gson = Gson()
            val jsonObject = JSONObject(it)
            val videoArrayObject = jsonObject["videos"]
            val turnsType = object : TypeToken<MutableList<VideoEntity>>() {}.type
            val videoList : MutableList<VideoEntity> =  gson.fromJson(videoArrayObject.toString(),turnsType)
            val responseValues = GetResUseCase.ResponseValues(videoList)
            useCaseCallback!!.onSuccess(responseValues)
        }, Response.ErrorListener {

        })
    }

    class RequestValues(val context:Context) : UseCase.RequestValues

    class ResponseValues(val videoList:MutableList<VideoEntity>) : UseCase.ResponseValues
}


