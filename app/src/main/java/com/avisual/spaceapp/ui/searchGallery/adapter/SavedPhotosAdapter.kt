package com.avisual.spaceapp.ui.searchGallery.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.avisual.spaceapp.database.PhotoGallery
import com.avisual.spaceapp.databinding.ItemGallerySavephotoBinding

class SavedPhotosAdapter(var photos: List<PhotoGallery>) :
    RecyclerView.Adapter<SavedPhotosAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGallerySavephotoBinding
            .inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(photos[position])
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    class ViewHolder(var binding: ItemGallerySavephotoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: PhotoGallery) {
            binding.titlePhoto.text = photo.title
            binding.photographer.text = photo.photographer
        }
    }
}