package com.example.waiterneocafe.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.clientneowaiter.databinding.ItemOrderPositionBinding
import com.example.waiterneocafe.model.order.DetailOrder

class AdapterTableOrder: RecyclerView.Adapter<AdapterTableOrder.ViewHolder>() {

    var onItemClickListener: ListClickListener<DetailOrder.Order.Item>? = null

    fun setOnItemClick(listClickListener: ListClickListener<DetailOrder.Order.Item>){
        this.onItemClickListener = listClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemOrderPositionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return differ.currentList.size

    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = differ.currentList[position]
        with(holder.binding){

            textTitle.text = product.item_name
            val productAmount = product.item_price.toString()
            textAmount.text = "${productAmount.toDouble().toInt()} c"

            if (product.quantity>1) {
                imageAdd.isEnabled = product.quantity <9
                textCount.visibility = View.VISIBLE
                textCount.text = product.quantity.toString()
                imageRemove.visibility = View.VISIBLE
            } else {
                textCount.visibility = View.INVISIBLE
                imageRemove.visibility = View.INVISIBLE
            }

            imageRemove.setOnClickListener {
                onItemClickListener?.onRemoveClick(product, position)
                notifyItemChanged(position, product)
            }
            imageAdd.setOnClickListener {
                onItemClickListener?.onAddClick(product, position)
                notifyItemChanged(position, product)
            }
        }
    }

    inner class ViewHolder (var binding: ItemOrderPositionBinding): RecyclerView.ViewHolder(binding.root) {

    }

    interface ListClickListener<T>{
        fun onAddClick(data:T, position: Int)
        fun onRemoveClick(data:T, position: Int)
    }

    private val differCallBack = object: DiffUtil.ItemCallback<DetailOrder.Order.Item>(){
        override fun areItemsTheSame(oldItem: DetailOrder.Order.Item, newItem: DetailOrder.Order.Item): Boolean {
            return oldItem.item_id == newItem.item_id
        }

        override fun areContentsTheSame(oldItem: DetailOrder.Order.Item, newItem: DetailOrder.Order.Item): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    fun removeItem(position: Int) {
        val newList = ArrayList(differ.currentList)
        if (position in 0 until newList.size) {
            newList.removeAt(position)
            differ.submitList(newList) {
                notifyItemRemoved(position)
            }
        }
    }
}