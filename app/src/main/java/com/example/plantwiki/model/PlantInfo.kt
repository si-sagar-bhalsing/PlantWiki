package com.example.plantwiki.model


import com.google.gson.annotations.SerializedName

data class PlantInfo(
    @SerializedName("data")
    val `data`: List<Data>,
)