package com.alexyach.kotlin.translator.retrofit.modelDto


import com.google.gson.annotations.SerializedName

data class ToneGroup(
    @SerializedName("tones")
    val tones: List<Tone>
)