package com.neobis.waiterneocafe.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.neobis.clientneowaiter.R
import com.neobis.clientneowaiter.databinding.ItemTableBinding
import com.neobis.waiterneocafe.model.Table

class AdapterTable(private val context: Context): RecyclerView.Adapter<AdapterTable.ViewHolder>(){

    var onItemClickListener: ((Table) -> Unit)? = null



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTableBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val table = differ.currentList[position]
        with(holder.binding){
            number.text = table.number
            if (table.free == "free"){
                cardForTable.setCardBackgroundColor(ContextCompat.getColor(context, R.color.green));
            }else{
                cardForTable.setCardBackgroundColor(ContextCompat.getColor(context, R.color.grey_table))
                cardForTable.isEnabled = false
            }
            cardForTable.setOnClickListener {
                onItemClickListener?.invoke(table)
            }
        }
    }

    inner class ViewHolder( var binding: ItemTableBinding): RecyclerView.ViewHolder(binding.root) {
    }

    private  val differCallBack = object: DiffUtil.ItemCallback<Table>(){
        override fun areItemsTheSame(oldItem: Table, newItem: Table): Boolean {
            return oldItem.number == newItem.number
        }

        override fun areContentsTheSame(oldItem: Table, newItem: Table): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,differCallBack)

}