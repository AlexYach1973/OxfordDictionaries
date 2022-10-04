package com.alexyach.kotlin.translator.retrofit.modelDto


import com.google.gson.annotations.SerializedName

data class PhrasalVerb(
    @SerializedName("domains")
    val domains: List<Domain>,
    @SerializedName("id")
    val id: String,
    @SerializedName("language")
    val language: String,
    @SerializedName("regions")
    val regions: List<Region>,
    @SerializedName("registers")
    val registers: List<Register>,
    @SerializedName("text")
    val text: String
)