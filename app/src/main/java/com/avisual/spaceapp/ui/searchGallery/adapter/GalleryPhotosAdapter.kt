package com.avisual.spaceapp.ui.searchGallery.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.avisual.spaceapp.database.PhotoGallery
import com.avisual.spaceapp.databinding.ItemGalleryPhotosBinding
import com.bumptech.glide.Glide

class GalleryPhotosAdapter(
    var photos: List<PhotoGallery>,
    var photoOnClickLister: (PhotoGallery) -> Unit
) :
    RecyclerView.Adapter<GalleryPhotosAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGalleryPhotosBinding
            .inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(photos[position])
        holder.itemView.setOnClickListener { photoOnClickLister(photos[position]) }
    }

    override fun getItemCount(): Int = photos.size

    class ViewHolder(var binding: ItemGalleryPhotosBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(photo: PhotoGallery) {
            Glide.with(binding.root.context).load(photo.url).into(binding.photoNasa)
        }
    }

    fun setItems(photos: List<PhotoGallery>) {
        this.photos = photos
        notifyDataSetChanged()
    }

}