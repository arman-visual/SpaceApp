package com.avisual.spaceapp.ui.asteroidsNeo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.avisual.spaceapp.databinding.ItemNeoAsteroidBinding
import com.avisual.spaceapp.model.Neo

class AsteroidsNeoAdapter(var listTotalNeo: List<Neo>) :
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
        holder.bind(listTotalNeo[position])
    }

    override fun getItemCount(): Int = listTotalNeo.size

    class ViewHolder(var binding: ItemNeoAsteroidBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(neo: Neo) {
            binding.titleNeo.text = neo.name
        }
    }

    fun setItems(asteroids: List<Neo>) {
        this.listTotalNeo = asteroids
        notifyDataSetChanged()
    }
}