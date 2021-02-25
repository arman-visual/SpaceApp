package com.avisual.spaceapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.avisual.spaceapp.databinding.ItemRoverMarsBinding
import com.avisual.spaceapp.model.nasaRoverResponse.Photo
import com.avisual.spaceapp.model.nasaRoverResponse.RoverPhotosResult
import com.bumptech.glide.Glide

class PhotosRoverAdapter(var photosRover: RoverPhotosResult) :
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
        holder.bind(photosRover.photos[position])
    }

    override fun getItemCount(): Int = photosRover.photos.size

    fun setItems(photosResult: RoverPhotosResult) {
        this.photosRover = photosResult
        notifyDataSetChanged()
    }

    class ViewHolder(var binding: ItemRoverMarsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(photosRover: Photo) {
            Glide.with(binding.root.context).load(photosRover.img_src).into(binding.photoRover);
        }
    }
}