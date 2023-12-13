package com.example.waiterneocafe.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.clientneowaiter.databinding.ItemMewOrderPositionBinding
import com.example.waiterneocafe.model.menu.Products

class AdapterNewOrderPosition: RecyclerView.Adapter<AdapterNewOrderPosition.ViewHolder>() {

    var onItemClickListener: ListClickListener<Products>? = null

    fun setOnItemClick(listClickListener: ListClickListener<Products>) {
        this.onItemClickListener = listClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterNewOrderPosition.ViewHolder {
        val binding =
            ItemMewOrderPositionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = differ.currentList[position]
        with(holder.binding) {
            textTitle.text = product.name
            textAmount.text = "${product.price.toDouble().toInt()} сом"
            imageAdd.setOnClickListener {
                onItemClickListener?.onAddClick(product, position)
            }
        }
    }

        override fun getItemCount(): Int {
            return differ.currentList.size
        }

    inner class ViewHolder (var binding: ItemMewOrderPositionBinding): RecyclerView.ViewHolder(binding.root)



        private val differCallBack = object : DiffUtil.ItemCallback<Products>() {
            override fun areItemsTheSame(oldItem: Products, newItem: Products): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Products, newItem: Products): Boolean {
                return oldItem == newItem
            }
        }

    interface ListClickListener<T> {
        fun onAddClick(data: T, position: Int)
    }

        val differ = AsyncListDiffer(this, differCallBack)

}