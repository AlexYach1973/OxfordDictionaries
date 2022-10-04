package com.alexyach.kotlin.translator.retrofit.modelDto


import com.google.gson.annotations.SerializedName

data class Construction(
    @SerializedName("domains")
    val domains: List<Domain>,
    @SerializedName("examples")
    val examples: List<List<String>>,
    @SerializedName("notes")
    val notes: List<Note>,
    @SerializedName("regions")
    val regions: List<Region>,
    @SerializedName("registers")
    val registers: List<Register>,
    @SerializedName("text")
    val text: String,
    @SerializedName("translations")
    val translations: List<Translation>
)