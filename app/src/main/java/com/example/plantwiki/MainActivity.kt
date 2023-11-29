package com.example.plantwiki

import android.content.ContentProvider
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.DumpableContainer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.plantwiki.model.Data
import com.example.plantwiki.model.PlantInfo
import com.example.plantwiki.ui.theme.PlantWikiTheme
import retrofit2.Response

class MainActivity : ComponentActivity() {


   private  val mainViewModel: MainViewModel by viewModels ()
    //private lateinit var response : Response<PlantInfo>
   private var mAdapter :PlantsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        mAdapter = PlantsAdapter()
        val gridView: GridView = findViewById(R.id.gridView)
        gridView?.adapter = mAdapter

        requestApiData()
        //mAdapter = PlantsAdapter(requestApiData())

    }

   private fun requestApiData() {
         mainViewModel.getPlants()
        //return response.body()
       mainViewModel.PlantsResponse.observe(this, { response ->
           Log.i("RECIPE DATA RESPONSE:", response.toString())
           when (response) {
               is NetworkResult.Success -> {

                   response.data?.let { mAdapter.setData(it) }
               }

               is NetworkResult.Error -> {

                   Toast.makeText(
                       requireContext(),
                       response.message.toString(),
                       Toast.LENGTH_SHORT
                   ).show()
               }

               is NetworkResult.Loading -> {

               }

           }
       })
    }
}

