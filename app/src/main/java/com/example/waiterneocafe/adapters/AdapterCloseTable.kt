package com.example.waiterneocafe.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.clientneowaiter.databinding.ItemCloseTableBinding
import com.example.waiterneocafe.model.order.DetailOrder

class AdapterCloseTable: RecyclerView.Adapter<AdapterCloseTable.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCloseTableBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var items = differ.currentList[position]
        with(holder.binding){
            textTitle.text = items.item_name
            textCount.text = items.quantity.toString()
            textAmount.text= "${items.item_total_price.toInt()} c"
            textCountPosition.text= "(${items.item_price.toInt()} c за шт)"
        }
    }

    inner class ViewHolder ( var binding: ItemCloseTableBinding): RecyclerView.ViewHolder(binding.root)  {
    }

    private val differCallBack = object: DiffUtil.ItemCallback<DetailOrder.Item>(){
        override fun areItemsTheSame(oldItem: DetailOrder.Item, newItem: DetailOrder.Item): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DetailOrder.Item, newItem: DetailOrder.Item): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)
}