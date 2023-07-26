package com.alexyach.kotlin.translator.data.retrofit.modelDto


import com.google.gson.annotations.SerializedName

data class Translation(
    @SerializedName("collocations")
    val collocations: List<Collocation>,
    @SerializedName("domains")
    val domains: List<Domain>,
    @SerializedName("grammaticalFeatures")
    val grammaticalFeatures: List<GrammaticalFeature>,
    @SerializedName("language")
    val language: String,
    @SerializedName("notes")
    val notes: List<Note>,
    @SerializedName("regions")
    val regions: List<Region>,
    @SerializedName("registers")
    val registers: List<Register>,
    @SerializedName("text")
    val text: String,
    @SerializedName("toneGroups")
    val toneGroups: List<ToneGroup>,
    @SerializedName("type")
    val type: String
)