package com.neobis.waiterneocafe.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.neobis.clientneowaiter.databinding.ItemOrderPositionBinding
import com.neobis.waiterneocafe.model.menu.Products
import com.neobis.waiterneocafe.utils.OrderUtils

class AdapterOrder: RecyclerView.Adapter<AdapterOrder.ViewHolder>() {

    var onItemClickListener: ListClickListener<Products>? = null

    fun setOnItemClick(listClickListener: AdapterOrder.ListClickListener<Products>){
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

            textTitle.text = product.name
            val productAmount = product.price.toString()
            textAmount.text = "${productAmount.toDouble().toInt()} c"

            if (OrderUtils.isInCart(product.id)) {
                val quantity = OrderUtils.getQuantity(product.id)
                imageAdd.isEnabled = quantity <9
                textCount.visibility = View.VISIBLE
                textCount.text = quantity.toString()
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
        fun onClick(data:T, position: Int)
        fun onAddClick(data:T, position: Int)
        fun onRemoveClick(data:T, position: Int)
    }

    private val differCallBack = object: DiffUtil.ItemCallback<Products>(){
        override fun areItemsTheSame(oldItem: Products, newItem: Products): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Products, newItem: Products): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    fun removeItem(position: Int) {
        val newList = ArrayList(differ.currentList)
        if (position in 0 until newList.size) {
            newList.removeAt(position)
            differ.submitList(newList) { // Используем callback для правильного уведомления
                notifyItemRemoved(position)
            }
        }
    }
}