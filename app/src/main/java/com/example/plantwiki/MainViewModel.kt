package com.example.plantwiki

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.plantwiki.data.Repository
import com.example.plantwiki.model.PlantInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {


    var PlantsResponse: MutableLiveData<NetworkResult<PlantInfo>> = MutableLiveData()

    fun getPlants()= viewModelScope.launch {
        getPlantsSafeCall()
    }

    private suspend fun getPlantsSafeCall() {
        PlantsResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getPlants()
                PlantsResponse.value = handleFoodRecipesResponse(response)
            } catch (e: Exception) {
                PlantsResponse.value = NetworkResult.Error("Plants not found.")
            }
        } else {
            PlantsResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    private fun handleFoodRecipesResponse(response: Response<PlantInfo>): NetworkResult<PlantInfo>? {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("API Key Limited.")
            }
            response.body()?.data.isNullOrEmpty() -> {
                return NetworkResult.Error("Recipes not found.")
            }
            response.isSuccessful -> {
                val plantInfo = response.body()
                return NetworkResult.Success(plantInfo!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }
    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}