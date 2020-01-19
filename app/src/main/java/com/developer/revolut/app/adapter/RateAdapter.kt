package com.developer.revolut.app.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.developer.revolut.app.util.OnPriceChangeListener
import com.developer.revolut.databinding.ViewRateRowBinding
import com.developer.revolut.domain.entities.ConversionRateModel

class RateAdapter(val onPriceChangeListener: OnPriceChangeListener) : RecyclerView.Adapter<RateAdapter.ViewHolder>() {
    private val dataSet = AsyncListDiffer<ConversionRateModel>(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ViewRateRowBinding.inflate(inflater, parent, false), onPriceChangeListener)
    }

    override fun getItemCount() = dataSet.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(dataSet.currentList[position])
    }

    fun setItems(newItems: List<ConversionRateModel>) {
        dataSet.submitList(newItems)
    }

    inner class ViewHolder(private val binding: ViewRateRowBinding,
                           private val onPriceChangeListener: OnPriceChangeListener) : RecyclerView.ViewHolder(binding.root) {
        private val watcher: TextWatcher

        init {
            watcher = object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                    //no op
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    //no op
                }

                override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    // TODO better handling classcastException
                    val newPrice = if (charSequence.isNullOrEmpty()) 0.0 else charSequence.toString().toDouble()
                    onPriceChangeListener.onPriceChanged(dataSet.currentList,
                            dataSet.currentList[adapterPosition],
                            newPrice)
                }
            }
            binding.ratePrice.addTextChangedListener(watcher)
        }

        fun bindView(conversionRateModel: ConversionRateModel) {
            binding.rate = conversionRateModel
            binding.ratePrice.apply {
                if (hasFocus().not()) {
                    this.removeTextChangedListener(watcher)
                    this.setText(conversionRateModel.price.toString())
                    addTextChangedListener(watcher)
                    setSelection(this.length())
                }
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<ConversionRateModel>() {
            override fun areItemsTheSame(oldItem: ConversionRateModel, newItem: ConversionRateModel) = oldItem.currencyCode == newItem.currencyCode

            override fun areContentsTheSame(oldItem: ConversionRateModel, newItem: ConversionRateModel) = oldItem == newItem
        }
    }
}