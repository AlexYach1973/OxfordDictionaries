package com.alexyach.kotlin.translator.retrofit.modelDto


import com.google.gson.annotations.SerializedName

data class VariantForm(
    @SerializedName("domains")
    val domains: List<Domain>,
    @SerializedName("notes")
    val notes: List<Note>,
    @SerializedName("pronunciations")
    val pronunciations: List<Pronunciation>,
    @SerializedName("regions")
    val regions: List<Region>,
    @SerializedName("registers")
    val registers: List<Register>,
    @SerializedName("text")
    val text: String
)