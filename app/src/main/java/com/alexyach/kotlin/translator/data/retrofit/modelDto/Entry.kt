package com.alexyach.kotlin.translator.data.retrofit.modelDto


import com.google.gson.annotations.SerializedName

data class Entry(
    @SerializedName("crossReferenceMarkers")
    val crossReferenceMarkers: List<String>,
    @SerializedName("crossReferences")
    val crossReferences: List<CrossReference>,
    @SerializedName("etymologies")
    val etymologies: List<String>,
    @SerializedName("grammaticalFeatures")
    val grammaticalFeatures: List<GrammaticalFeature>,
    @SerializedName("homographNumber")
    val homographNumber: String,
    @SerializedName("inflections")
    val inflections: List<Inflection>,
    @SerializedName("notes")
    val notes: List<Note>,
    @SerializedName("pronunciations")
    val pronunciations: List<Pronunciation>,
    @SerializedName("senses")
    val senses: List<Sense>,
    @SerializedName("variantForms")
    val variantForms: List<VariantForm>
)