package com.neobis.waiterneocafe.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.neobis.waiterneocafe.model.menu.Products
import com.neobis.waiterneocafe.view.orders.CategoryForAddItemsFragment

class SliderAdapterForAddItems(
    private val fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val categories: List<Products.Category>,
    private val orderId: Int?
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun createFragment(position: Int): Fragment {

        return CategoryForAddItemsFragment.newInstance(categories[position].id, orderId)

    }

}