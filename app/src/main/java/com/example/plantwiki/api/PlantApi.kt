package com.example.plantwiki.api

import com.example.plantwiki.model.PlantInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PlantApi {

    @GET("/api/v1/plants")
    suspend fun getPlants(
        @Query("token") token:String
    ):Response<PlantInfo>
}