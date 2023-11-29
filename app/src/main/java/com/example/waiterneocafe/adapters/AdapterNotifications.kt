package com.example.waiterneocafe.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.clientneowaiter.databinding.ItemNotificationBinding
import com.example.waiterneocafe.model.notifications.Notifications

class AdapterNotifications: RecyclerView.Adapter<AdapterNotifications.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var notifications = differ.currentList[position]
        with(holder.binding){
            textTitleNotification.text = notifications.title
            textDiscrNotification.text= notifications.discr
            textTimeNotification.text = notifications.time

        }
    }

    inner class ViewHolder ( var binding: ItemNotificationBinding): RecyclerView.ViewHolder(binding.root)  {
    }

    private val differCallBack = object: DiffUtil.ItemCallback<Notifications>(){
        override fun areItemsTheSame(oldItem: Notifications, newItem: Notifications): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Notifications, newItem: Notifications): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    fun removeItem(position: Int) {
        val newList = ArrayList(differ.currentList)
        newList.removeAt(position)
        differ.submitList(newList)
    }

    fun deleteAllItems() {
        val newList = emptyList<Notifications>()
        differ.submitList(newList)
    }
}