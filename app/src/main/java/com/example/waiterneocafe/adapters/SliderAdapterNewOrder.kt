package com.example.waiterneocafe.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.waiterneocafe.model.menu.Products
import com.example.waiterneocafe.view.menu.CategoryFragment
import com.example.waiterneocafe.view.newOrder.CategoryNewOrderFragment

class SliderAdapterNewOrder(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val categories: List<Products.Category>
) :
FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return categories.size
    }

    override fun createFragment(position: Int): Fragment {
        val category = categories[position]
        return CategoryNewOrderFragment.newInstance(category.id)
    }
}