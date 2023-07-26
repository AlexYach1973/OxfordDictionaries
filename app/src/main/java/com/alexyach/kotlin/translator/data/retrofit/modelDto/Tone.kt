package com.alexyach.kotlin.translator.data.retrofit.modelDto


import com.google.gson.annotations.SerializedName

data class Tone(
    @SerializedName("type")
    val type: String,
    @SerializedName("value")
    val value: String
)