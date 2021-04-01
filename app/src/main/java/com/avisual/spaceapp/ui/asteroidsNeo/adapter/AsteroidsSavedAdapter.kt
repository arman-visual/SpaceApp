package com.avisual.spaceapp.ui.asteroidsNeo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.avisual.spaceapp.R
import com.avisual.spaceapp.databinding.ItemNeoSavedBinding
import com.avisual.spaceapp.model.Neo

class AsteroidsSavedAdapter(
    var asteroids: List<Neo>,
    var onButtonRemoveClick: (Neo) -> Unit
) :
    RecyclerView.Adapter<AsteroidsSavedAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNeoSavedBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(asteroids[position])
        holder.itemView.setOnClickListener { onButtonRemoveClick(asteroids[position]) }
    }

    override fun getItemCount(): Int = asteroids.size

    class ViewHolder(var binding: ItemNeoSavedBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(asteroid: Neo) {
            binding.ivAsteroid.setImageResource(R.drawable.asteroid)
            binding.nameNeo.text = asteroid.name
            binding.missDistance.text = asteroid.missDistance
            when (asteroid.isPotentiallyHazardousAsteroid) {
                true -> binding.danger.setImageResource(R.drawable.danger_on)
                false -> binding.danger.setImageResource(R.drawable.danger_off)
            }
        }
    }

    fun setItems(asteroids: List<Neo>) {
        this.asteroids = asteroids
        notifyDataSetChanged()
    }
}