package com.alexyach.kotlin.translator.retrofit.modelDto


import com.google.gson.annotations.SerializedName

data class LexicalEntry(
    @SerializedName("compounds")
    val compounds: List<Compound>,
    @SerializedName("derivativeOf")
    val derivativeOf: List<DerivativeOf>,
    @SerializedName("derivatives")
    val derivatives: List<Derivative>,
    @SerializedName("entries")
    val entries: List<Entry>,
    @SerializedName("grammaticalFeatures")
    val grammaticalFeatures: List<GrammaticalFeature>,
    @SerializedName("language")
    val language: String,
    @SerializedName("lexicalCategory")
    val lexicalCategory: LexicalCategory,
    @SerializedName("notes")
    val notes: List<Note>,
    @SerializedName("phrasalVerbs")
    val phrasalVerbs: List<PhrasalVerb>,
    @SerializedName("phrases")
    val phrases: List<Phrase>,
    @SerializedName("pronunciations")
    val pronunciations: List<Pronunciation>,
    @SerializedName("root")
    val root: String,
    @SerializedName("text")
    val text: String,
    @SerializedName("variantForms")
    val variantForms: List<VariantForm>
)