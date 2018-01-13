package com.zqlite.android.mockaweme.repository

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

/**
 * Created by scott on 2018/1/13.
 */
class NetRepo(context: Context) {
    private val url = "http://p2c5nlwg0.bkt.clouddn.com/videolist.json"
    private val requestQueue: RequestQueue = Volley.newRequestQueue(context)

    fun getVideoList(listener: Response.Listener<String>, error: Response.ErrorListener) {
        val request = StringRequest(Request.Method.GET, url, Response.Listener<String> {
            listener.onResponse(it)
        }, Response.ErrorListener {
            error.onErrorResponse(it)
        })
        requestQueue.add(request)
    }
}