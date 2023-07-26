package com.alexyach.kotlin.translator.data.retrofit.modelDto


import com.google.gson.annotations.SerializedName

data class ThesaurusLink(
    @SerializedName("entry_id")
    val entryId: String,
    @SerializedName("sense_id")
    val senseId: String
)