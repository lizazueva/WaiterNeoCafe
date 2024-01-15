package com.neobis.waiterneocafe.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.neobis.clientneowaiter.databinding.ItemMilkBinding
import com.neobis.waiterneocafe.model.Milk

class AdapterMilk1: RecyclerView.Adapter<AdapterMilk1.ViewHolder>() {

    private var selectedPosition = -1

    inner class ViewHolder (var binding: ItemMilkBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = ItemMilkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return  differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val milk = differ.currentList[position]
        with(holder.binding){
            checkboxMilk.text = milk.title
            checkboxMilk.isChecked = holder.adapterPosition == selectedPosition
            checkboxMilk.setOnClickListener {
                if (holder.adapterPosition != selectedPosition) {
                    selectedPosition = holder.adapterPosition
                    notifyDataSetChanged()
                }
            }

        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Milk>(){
        override fun areItemsTheSame(oldItem: Milk, newItem: Milk): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Milk, newItem: Milk): Boolean {
            return  oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)


}