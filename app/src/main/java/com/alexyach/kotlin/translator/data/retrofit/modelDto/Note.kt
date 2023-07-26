package com.alexyach.kotlin.translator.data.retrofit.modelDto


import com.google.gson.annotations.SerializedName

data class Note(
    @SerializedName("id")
    val id: String,
    @SerializedName("text")
    val text: String,
    @SerializedName("type")
    val type: String
)