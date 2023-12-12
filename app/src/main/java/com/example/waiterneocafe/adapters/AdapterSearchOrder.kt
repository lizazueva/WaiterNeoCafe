package com.example.waiterneocafe.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.clientneowaiter.databinding.ItemMewOrderPositionBinding
import com.example.waiterneocafe.model.menu.SearchResultResponse

class AdapterSearchOrder: RecyclerView.Adapter<AdapterSearchOrder.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMewOrderPositionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return differ.currentList.size

    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = differ.currentList[position]
        with(holder.binding){

            textTitle.text = product.name
            val productAmount = product.price.toString()
            textAmount.text = "$productAmount c"
        }

    }

    inner class ViewHolder (var binding: ItemMewOrderPositionBinding): RecyclerView.ViewHolder(binding.root) {
    }

    private val differCallBack = object: DiffUtil.ItemCallback<SearchResultResponse>(){
        override fun areItemsTheSame(oldItem: SearchResultResponse, newItem: SearchResultResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SearchResultResponse, newItem: SearchResultResponse): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)
}