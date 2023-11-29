package com.example.plantwiki

import android.view.LayoutInflater

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.plantwiki.databinding.GridItemBinding
import com.example.plantwiki.model.Data

import com.example.plantwiki.model.PlantInfo

class PlantsAdapter(/*private var dataset : List<Data>*/) :
    RecyclerView.Adapter<PlantsAdapter.MyViewHolder>() {

    lateinit var  dataset : List<Data>

    class MyViewHolder( val binding : GridItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantsAdapter.MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = GridItemBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentPlant = dataset[position]
        holder.binding.plantImage.load(currentPlant.imageUrl)
        holder.binding.plantName.text = currentPlant.commonName
    }


    fun setData(newData: PlantInfo) {
        dataset = newData.data
    }


}