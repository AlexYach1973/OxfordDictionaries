package com.alexyach.kotlin.translator.data.retrofit.modelDto


import com.google.gson.annotations.SerializedName

data class Sense(
    @SerializedName("antonyms")
    val antonyms: List<Antonym>,
    @SerializedName("constructions")
    val constructions: List<Construction>,
    @SerializedName("crossReferenceMarkers")
    val crossReferenceMarkers: List<String>,
    @SerializedName("crossReferences")
    val crossReferences: List<CrossReference>,
    @SerializedName("datasetCrossLinks")
    val datasetCrossLinks: List<DatasetCrossLink>,
    @SerializedName("definitions")
    val definitions: List<String>,
    @SerializedName("domainClasses")
    val domainClasses: List<DomainClasse>,
    @SerializedName("domains")
    val domains: List<Domain>,
    @SerializedName("etymologies")
    val etymologies: List<String>,
    @SerializedName("examples")
    val examples: List<Example>,
    @SerializedName("id")
    val id: String,
    @SerializedName("inflections")
    val inflections: List<Inflection>,
    @SerializedName("notes")
    val notes: List<Note>,
    @SerializedName("pronunciations")
    val pronunciations: List<Pronunciation>,
    @SerializedName("regions")
    val regions: List<Region>,
    @SerializedName("registers")
    val registers: List<Register>,
    @SerializedName("semanticClasses")
    val semanticClasses: List<SemanticClasse>,
    @SerializedName("shortDefinitions")
    val shortDefinitions: List<String>,
    @SerializedName("subsenses")
    val subsenses: List<Subsense>,
    @SerializedName("synonyms")
    val synonyms: List<Synonym>,
    @SerializedName("thesaurusLinks")
    val thesaurusLinks: List<ThesaurusLink>,
    @SerializedName("translations")
    val translations: List<Translation>,
    @SerializedName("variantForms")
    val variantForms: List<VariantForm>
)