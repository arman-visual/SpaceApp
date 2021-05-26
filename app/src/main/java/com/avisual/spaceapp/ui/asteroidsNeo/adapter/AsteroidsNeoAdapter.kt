package com.avisual.spaceapp.ui.asteroidsNeo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.avisual.spaceapp.R
import com.avisual.spaceapp.databinding.ItemNeoAsteroidBinding
import com.avisual.spaceapp.data.model.Neo

class AsteroidsNeoAdapter(
    var asteroids: List<Neo>,
    var neoOnClick: (Neo) -> Unit
) :
    RecyclerView.Adapter<AsteroidsNeoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNeoAsteroidBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(asteroids[position])
        holder.itemView.setOnClickListener { neoOnClick(asteroids[position]) }
    }

    override fun getItemCount(): Int = asteroids.size

    class ViewHolder(var binding: ItemNeoAsteroidBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(neo: Neo) {
            binding.ivAsteroid.setImageResource(R.drawable.asteroid)
            binding.nameNeo.text = neo.name
            binding.missDistance.text = neo.missDistance
            when (neo.isPotentiallyHazardousAsteroid) {
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