package com.example.waiterneocafe.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.waiterneocafe.view.menu.CategoryFragment
import com.example.waiterneocafe.view.newOrder.CategoryNewOrderFragment

class SliderAdapterNewOrder(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        return CategoryNewOrderFragment()
    }
}