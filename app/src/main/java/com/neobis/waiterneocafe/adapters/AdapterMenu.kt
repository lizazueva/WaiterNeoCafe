package com.neobis.waiterneocafe.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.neobis.clientneowaiter.databinding.ItemMenuBinding
import com.neobis.waiterneocafe.model.menu.Products

class AdapterMenu: RecyclerView.Adapter<AdapterMenu.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterMenu.ViewHolder {
        val binding = ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdapterMenu.ViewHolder, position: Int) {
        val product = differ.currentList[position]
        with(holder.binding) {
            textTitle.text = product.name
            textAmount.text = "${product.price.toDouble().toInt()} сом"
        }
    }

        override fun getItemCount(): Int {
            return differ.currentList.size
        }

        inner class ViewHolder(var binding: ItemMenuBinding) : RecyclerView.ViewHolder(binding.root) {
        }

        private val differCallBack = object : DiffUtil.ItemCallback<Products>() {
            override fun areItemsTheSame(oldItem: Products, newItem: Products): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Products, newItem: Products): Boolean {
                return oldItem == newItem
            }
        }

        val differ = AsyncListDiffer(this, differCallBack)
}