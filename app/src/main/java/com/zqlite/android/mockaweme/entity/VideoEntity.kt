package com.zqlite.android.mockaweme.entity

import com.google.gson.annotations.SerializedName

/**
 * Created by scott on 2018/1/13.
 */
data class VideoEntity(
        @SerializedName("video_url") val videoUrl: String,
        @SerializedName("video_thumb") val videoThumb: String
)