package com.neobis.waiterneocafe.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.neobis.clientneowaiter.databinding.ItemNotificationBinding
import com.neobis.waiterneocafe.model.notifications.NotificationsResponse

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
            textDiscrNotification.text= notifications.body
            textTimeNotification.text = notifications.exactly_time

        }
    }

    inner class ViewHolder ( var binding: ItemNotificationBinding): RecyclerView.ViewHolder(binding.root)  {
    }

    private val differCallBack = object: DiffUtil.ItemCallback<NotificationsResponse.Notifications>(){
        override fun areItemsTheSame(oldItem: NotificationsResponse.Notifications, newItem: NotificationsResponse.Notifications): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NotificationsResponse.Notifications, newItem: NotificationsResponse.Notifications): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    fun removeItem(position: Int) {
        val newList = ArrayList(differ.currentList)
        newList.removeAt(position)
        differ.submitList(newList)
    }
    fun deleteAll() {
        differ.submitList(emptyList())
    }
}