package com.neobis.waiterneocafe.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.neobis.waiterneocafe.model.menu.Products
import com.neobis.waiterneocafe.view.menu.CategoryFragment

class SliderAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val menuCategory: List<Products.Category>
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return menuCategory.size
    }

    override fun createFragment(position: Int): Fragment {
        val category = menuCategory[position]
        return CategoryFragment.newInstance(category.id)
    }
}