package com.alexyach.kotlin.translator.data.retrofit.modelDto


import com.google.gson.annotations.SerializedName

data class Pronunciation(
    @SerializedName("audioFile")
    val audioFile: String,
    @SerializedName("dialects")
    val dialects: List<String>,
    @SerializedName("phoneticNotation")
    val phoneticNotation: String,
    @SerializedName("phoneticSpelling")
    val phoneticSpelling: String,
    @SerializedName("regions")
    val regions: List<Region>,
    @SerializedName("registers")
    val registers: List<Register>
)