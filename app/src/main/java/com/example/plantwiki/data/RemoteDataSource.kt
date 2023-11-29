package com.example.plantwiki.data

import com.example.plantwiki.Constants.Companion.TOKEN
import com.example.plantwiki.api.PlantApi
import com.example.plantwiki.model.PlantInfo
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val plantApi: PlantApi
) {
    suspend fun getPlants(): Response<PlantInfo> {
        return plantApi.getPlants(TOKEN)
    }
}