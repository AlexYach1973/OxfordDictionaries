package com.alexyach.kotlin.translator.data.retrofit.modelDto


import com.google.gson.annotations.SerializedName

data class Register(
    @SerializedName("id")
    val id: String,
    @SerializedName("text")
    val text: String
)