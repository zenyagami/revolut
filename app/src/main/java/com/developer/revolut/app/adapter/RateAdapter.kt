package com.developer.revolut.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.developer.revolut.databinding.ViewRateRowBinding
import com.developer.revolut.domain.entities.ConversionRateModel

class RateAdapter : RecyclerView.Adapter<RateAdapter.ViewHolder>() {
    private val items: MutableList<ConversionRateModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ViewRateRowBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.item = items[position]
    }

    fun setItems(newItems: List<ConversionRateModel>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ViewRateRowBinding) : RecyclerView.ViewHolder(binding.root) {
        var item: ConversionRateModel? = null
            set(value) {
                field = value
                value?.let {
                    binding.rate = value
                }

            }
    }
}