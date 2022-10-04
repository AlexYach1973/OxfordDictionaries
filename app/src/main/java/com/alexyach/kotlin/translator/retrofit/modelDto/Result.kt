package com.alexyach.kotlin.translator.retrofit.modelDto


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("id")
    val id: String,
    @SerializedName("language")
    val language: String,
    @SerializedName("lexicalEntries")
    val lexicalEntries: List<LexicalEntry>,
    @SerializedName("pronunciations")
    val pronunciations: List<Pronunciation>,
    @SerializedName("type")
    val type: String,
    @SerializedName("word")
    val word: String
)