package com.alexyach.kotlin.translator.data.retrofit.modelDto


import com.google.gson.annotations.SerializedName

data class ToneGroup(
    @SerializedName("tones")
    val tones: List<Tone>
)