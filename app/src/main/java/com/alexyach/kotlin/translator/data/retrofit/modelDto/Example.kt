package com.alexyach.kotlin.translator.data.retrofit.modelDto


import com.google.gson.annotations.SerializedName

data class Example(
    @SerializedName("collocations")
    val collocations: List<Collocation>,
    @SerializedName("crossReferenceMarkers")
    val crossReferenceMarkers: List<String>,
    @SerializedName("crossReferences")
    val crossReferences: List<CrossReference>,
    @SerializedName("definitions")
    val definitions: List<String>,
    @SerializedName("domains")
    val domains: List<Domain>,
    @SerializedName("notes")
    val notes: List<Note>,
    @SerializedName("regions")
    val regions: List<Region>,
    @SerializedName("registers")
    val registers: List<Register>,
    @SerializedName("senseIds")
    val senseIds: List<String>,
    @SerializedName("text")
    val text: String,
    @SerializedName("translations")
    val translations: List<Translation>
)