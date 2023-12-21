package com.example.waiterneocafe.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.clientneowaiter.R
import com.example.clientneowaiter.databinding.ItemStatusOrderBinding

import com.example.waiterneocafe.model.order.Orders

class AdapterListOrders: RecyclerView.Adapter<AdapterListOrders.ViewHolder>(){

    var onItemClickListener: ((Orders.Order) -> Unit)? = null



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemStatusOrderBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = differ.currentList[position]
        with(holder.binding){
            textTitle.text = "Cтол №${order.table_number}"
            textNumber.text = "№ ${order.order_number}"
            when(order.order_status) {
                "new" -> imageStatus.setImageResource(R.drawable.img_ellipse_red).also {
                    textStatusOrder.text = "Новый"
                }
                "in progress" -> imageStatus.setImageResource(R.drawable.img_ellipse_yellow).also {
                    textStatusOrder.text = "В процессе"
                }
                "ready" -> imageStatus.setImageResource(R.drawable.img_ellipse_green).also {
                    textStatusOrder.text = "Готово"
                }
                "canceled" -> imageStatus.setImageResource(R.drawable.img_ellipse_black).also {
                    textStatusOrder.text = "Отменено"
                }
                "completed" -> imageStatus.setImageResource(R.drawable.img_ellipse_grey).also {
                    textStatusOrder.text = "Завершено"
                }
            }

            root.setOnClickListener {
                onItemClickListener?.invoke(order)
            }
        }
    }

    inner class ViewHolder( var binding: ItemStatusOrderBinding): RecyclerView.ViewHolder(binding.root) {
    }

    private  val differCallBack = object: DiffUtil.ItemCallback<Orders.Order>(){
        override fun areItemsTheSame(oldItem: Orders.Order, newItem: Orders.Order): Boolean {
            return oldItem.order_number == newItem.order_number
        }

        override fun areContentsTheSame(oldItem: Orders.Order, newItem: Orders.Order): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,differCallBack)

}
