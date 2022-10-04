package com.alexyach.kotlin.translator.retrofit.modelDto


import com.google.gson.annotations.SerializedName

data class Inflection(
    @SerializedName("domains")
    val domains: List<Domain>,
    @SerializedName("grammaticalFeatures")
    val grammaticalFeatures: List<GrammaticalFeature>,
    @SerializedName("inflectedForm")
    val inflectedForm: String,
    @SerializedName("lexicalCategory")
    val lexicalCategory: LexicalCategory,
    @SerializedName("pronunciations")
    val pronunciations: List<Pronunciation>,
    @SerializedName("regions")
    val regions: List<Region>,
    @SerializedName("registers")
    val registers: List<Register>
)