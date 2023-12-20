package com.example.waiterneocafe.adapters

import androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior.setTag
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager.widget.PagerAdapter.POSITION_NONE
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.waiterneocafe.model.menu.Products
import com.example.waiterneocafe.view.newOrder.CategoryNewOrderFragment

//class CustomPagerAdapter(private val viewPager: ViewPager, fragmentManager: FragmentManager, lifecycle: Lifecycle, private val categories: List<Products.Category>) :
//    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
//
//    override fun getCount(): Int = categories.size
//
//    override fun getItem(position: Int): Fragment {
//        val fragmentTag = "f$position"
//        val existingFragment = fragmentManager.findFragmentByTag(fragmentTag)
//
//        return existingFragment ?: when (position) {
//            0 -> CategoryNewOrderFragment.newInstance(categories[position].id)
//            1 -> CategoryNewOrderFragment.newInstance(categories[position].id)
//            2 -> CategoryNewOrderFragment.newInstance(categories[position].id)
//            // Add more cases as needed
//            else -> throw IllegalArgumentException("Invalid position: $position")
//        }.apply {
//            // Set a unique tag for the fragment
//            setTag(fragmentTag)
//        }
//    }
//
//    override fun getItemId(position: Int): Long {
//        // Unique identifier for each item to distinguish fragments
//        return viewPager.id.toLong() + position
//    }
//}