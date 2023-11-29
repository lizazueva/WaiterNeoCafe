package com.example.waiterneocafe.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.clientneowaiter.databinding.ItemMenuBinding
import com.example.waiterneocafe.model.menu.Product

class AdapterMenu: RecyclerView.Adapter<AdapterMenu.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterMenu.ViewHolder {
        val binding = ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdapterMenu.ViewHolder, position: Int) {
        val product = differ.currentList[position]
        with(holder.binding) {
            textTitle.text = product.title
            textAmount.text = "${product.amount} сом"
        }
    }

        override fun getItemCount(): Int {
            return differ.currentList.size
        }

        inner class ViewHolder(var binding: ItemMenuBinding) : RecyclerView.ViewHolder(binding.root) {
        }

        private val differCallBack = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem == newItem
            }
        }

        val differ = AsyncListDiffer(this, differCallBack)
}