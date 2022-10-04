package com.alexyach.kotlin.translator.retrofit.modelDto


import com.google.gson.annotations.SerializedName

data class Collocation(
    @SerializedName("id")
    val id: String,
    @SerializedName("text")
    val text: String,
    @SerializedName("type")
    val type: String
)