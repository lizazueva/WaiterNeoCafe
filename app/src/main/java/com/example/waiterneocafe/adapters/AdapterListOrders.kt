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
import java.text.SimpleDateFormat
import java.util.Locale

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
            val formattedTime = convertIso8601ToTime(order.order_created_at)
            textTime.text = formattedTime

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

    fun convertIso8601ToTime(iso8601String: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX", Locale.getDefault())
        val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        return try {
            val date = inputFormat.parse(iso8601String)
            outputFormat.format(date!!)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

}
