package com.avisual.spaceapp.ui.roverMars.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.avisual.spaceapp.ui.common.loadUrl
import com.avisual.spaceapp.databinding.ItemRoverMarsBinding
import com.avisual.spaceapp.data.model.PhotoRover

class PhotosRoverAdapter(
    var photosRover: List<PhotoRover>,
    var photoOnclick: (photo: PhotoRover) -> Unit
) :
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
        holder.itemView.setOnClickListener { photoOnclick(photosRover[position]) }
    }

    override fun getItemCount(): Int = photosRover.size

    fun setItems(photosResult: List<PhotoRover>) {
        this.photosRover = photosResult
        notifyDataSetChanged()
    }

    class ViewHolder(var binding: ItemRoverMarsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(photosRover: PhotoRover) {
            binding.photoRover.loadUrl(photosRover.img_src)
        }
    }
}