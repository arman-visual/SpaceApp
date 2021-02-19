package com.avisual.spaceapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.avisual.spaceapp.databinding.ItemMainBinding
import com.avisual.spaceapp.model.roverPhotos.Photo
import com.avisual.spaceapp.model.roverPhotos.RoverPhotosResult
import com.bumptech.glide.Glide

class PhotosRoverAdapter(var photosRover: RoverPhotosResult)
    : RecyclerView.Adapter<PhotosRoverAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMainBinding
            .inflate(
                LayoutInflater.from(parent.context),
            parent,
            false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(photosRover.photos[position])
    }

    override fun getItemCount(): Int = photosRover.photos.size


    class ViewHolder(var binding: ItemMainBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(photosRover: Photo){
            Glide.with(binding.root.context).load(photosRover.img_src).into(binding.photoRover);
        }
    }
}