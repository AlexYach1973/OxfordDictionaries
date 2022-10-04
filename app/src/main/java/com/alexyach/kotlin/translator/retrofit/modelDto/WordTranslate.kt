package com.alexyach.kotlin.translator.retrofit.modelDto


import com.google.gson.annotations.SerializedName

data class WordTranslate(
    @SerializedName("metadata")
    val metadata: Metadata,
    @SerializedName("results")
    val results: List<Result>
)