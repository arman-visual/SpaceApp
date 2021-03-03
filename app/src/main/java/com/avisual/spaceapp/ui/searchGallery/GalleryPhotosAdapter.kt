package com.avisual.spaceapp.ui.searchGallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.avisual.spaceapp.databinding.ItemGalleryPhotosBinding
import com.avisual.spaceapp.model.nasaLibraryResponse.Item
import com.bumptech.glide.Glide

class GalleryPhotosAdapter(
    var photos: List<Item>,
    var photoOnClickLister: (Item)-> Unit
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

        fun bind(photo: Item) {
            Glide.with(binding.root.context).load(photo.links[0].href).into(binding.photoNasa)
        }
    }

    fun setItems(photos: List<Item>) {
        this.photos = photos
        notifyDataSetChanged()
    }

}