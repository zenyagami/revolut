package com.developer.revolut.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.developer.revolut.databinding.ViewRateRowBinding
import com.developer.revolut.domain.entities.ConversionRateModel

class RateAdapter : RecyclerView.Adapter<RateAdapter.ViewHolder>() {
    private val dataSet = AsyncListDiffer<ConversionRateModel>(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ViewRateRowBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount() = dataSet.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.item = dataSet.currentList[position]
    }

    fun setItems(newItems: List<ConversionRateModel>) {
        dataSet.submitList(newItems)
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

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<ConversionRateModel>() {
            override fun areItemsTheSame(oldItem: ConversionRateModel, newItem: ConversionRateModel) = oldItem.countryCode == newItem.countryCode

            override fun areContentsTheSame(oldItem: ConversionRateModel, newItem: ConversionRateModel) = oldItem == newItem
        }
    }
}