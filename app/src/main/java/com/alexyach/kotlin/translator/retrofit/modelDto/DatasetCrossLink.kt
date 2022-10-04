package com.alexyach.kotlin.translator.retrofit.modelDto


import com.google.gson.annotations.SerializedName

data class DatasetCrossLink(
    @SerializedName("entry_id")
    val entryId: String,
    @SerializedName("language")
    val language: String,
    @SerializedName("sense_id")
    val senseId: String
)