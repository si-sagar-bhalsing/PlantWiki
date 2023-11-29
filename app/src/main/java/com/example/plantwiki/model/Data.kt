package com.example.plantwiki.model


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("author")
    val author: String,
    @SerializedName("common_name")
    val commonName: String,
    @SerializedName("family")
    val family: String,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("scientific_name")
    val scientificName: String,
    @SerializedName("synonyms")
    val synonyms: List<String>,
    @SerializedName("year")
    val year: Int
)