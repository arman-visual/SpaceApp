package com.avisual.spaceapp.ui.roverMars.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.avisual.spaceapp.common.loadUrl
import com.avisual.spaceapp.databinding.ItemRoverMarsBinding
import com.avisual.spaceapp.model.PhotoCuriosity

class PhotosRoverAdapter(var photosRover: List<PhotoCuriosity>) :
    RecyclerView.Adapter<PhotosRoverAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRoverMarsBinding
            .inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(photosRover[position])
    }

    override fun getItemCount(): Int = photosRover.size

    fun setItems(photosResult: List<PhotoCuriosity>) {
        this.photosRover = photosResult
        notifyDataSetChanged()
    }

    class ViewHolder(var binding: ItemRoverMarsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(photosRover: PhotoCuriosity) {
            binding.photoRover.loadUrl(photosRover.img_src)
        }
    }
}