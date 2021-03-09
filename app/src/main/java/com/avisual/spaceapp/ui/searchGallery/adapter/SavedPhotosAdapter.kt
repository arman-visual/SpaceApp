package com.avisual.spaceapp.ui.searchGallery.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.avisual.spaceapp.R
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
            binding.ivPhoto.setImageResource(R.drawable.ic_photo_saved)
            binding.titlePhoto.text = photo.title
            binding.photographer.text = photo.photographer
        }
    }

    fun setItems(photos: List<PhotoGallery>) {
        this.photos = photos
        notifyDataSetChanged()
    }
}