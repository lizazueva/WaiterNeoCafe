package com.neobis.waiterneocafe.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.neobis.clientneowaiter.databinding.ItemOrderPositionBinding
import com.neobis.waiterneocafe.model.order.DetailOrder

class AdapterTableOrder: RecyclerView.Adapter<AdapterTableOrder.ViewHolder>() {

    var onItemClickListener: ListClickListener<DetailOrder.Item>? = null

    fun setOnItemClick(listClickListener: ListClickListener<DetailOrder.Item>){
        this.onItemClickListener = listClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemOrderPositionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return differ.currentList.size

    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = differ.currentList[position]
        with(holder.binding){

            textTitle.text = "${product.item_name} (${product.item_price.toInt()} с за шт)"
            val productAmount = product.item_total_price.toString()
            textAmount.text = "${productAmount.toDouble().toInt()} c"

            if (product.quantity>0) {
                imageAdd.isEnabled = product.quantity <9
                textCount.visibility = View.VISIBLE
                textCount.text = product.quantity.toString()
                imageRemove.visibility = View.VISIBLE
            } else {
                textCount.visibility = View.INVISIBLE
                imageRemove.isEnabled = false
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

    private val differCallBack = object: DiffUtil.ItemCallback<DetailOrder.Item>(){
        override fun areItemsTheSame(oldItem: DetailOrder.Item, newItem: DetailOrder.Item): Boolean {
            return oldItem.item_id == newItem.item_id
        }

        override fun areContentsTheSame(oldItem: DetailOrder.Item, newItem: DetailOrder.Item): Boolean {
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