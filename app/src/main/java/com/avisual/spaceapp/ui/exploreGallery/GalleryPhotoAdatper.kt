package com.avisual.spaceapp.ui.exploreGallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.avisual.spaceapp.databinding.ItemGalleryPhotosBinding
import com.avisual.spaceapp.model.nasaLibraryResponse.Item
import com.bumptech.glide.Glide

class GalleryPhotoAdatper(var items: List<Item>) :
    RecyclerView.Adapter<GalleryPhotoAdatper.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = ItemGalleryPhotosBinding
            .inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(var binding: ItemGalleryPhotosBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item) {
            Glide.with(binding.root.context).load(item.href).into(binding.photoRover)
        }
    }
}