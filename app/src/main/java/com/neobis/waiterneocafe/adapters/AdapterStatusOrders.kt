package com.neobis.waiterneocafe.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.neobis.waiterneocafe.model.order.Orders
import com.neobis.waiterneocafe.view.orders.StatusOrderFragment

class AdapterStatusOrders(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private var orders: List<Orders.Order>
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 6
    }

    override fun createFragment(position: Int): Fragment {
        val filteredOrders = filterOrdersByTabPosition(position)
        return StatusOrderFragment().newInstance(filteredOrders)
    }

    private fun filterOrdersByTabPosition(tabPosition: Int): List<Orders.Order> {
        return when (tabPosition) {
            0 -> orders
            1 -> orders.filter { it.order_status == "new" }
            2 -> orders.filter { it.order_status == "in_progress" }
            3 -> orders.filter { it.order_status == "ready" }
            4 -> orders.filter { it.order_status == "canceled" }
            5 -> orders.filter { it.order_status == "completed" }
            else -> emptyList()
        }
    }
}