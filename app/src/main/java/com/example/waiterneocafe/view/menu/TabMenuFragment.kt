package com.example.waiterneocafe.view.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.clientneowaiter.R
import com.example.clientneowaiter.databinding.FragmentTabMenuBinding
import com.example.waiterneocafe.adapters.SliderAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class TabMenuFragment : Fragment() {

    private lateinit var binding: FragmentTabMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTabMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager()


    }

    private fun viewPager() {
        val tabLayout = binding.tabLayout
        val viewPager2 = binding.pager
        val tabArray = arrayOf(
            "Кофе",
            "Выпечка",
            "Коктейли",
            "Десерты",
            "Чай"
        )
        val adapter = SliderAdapter(childFragmentManager, lifecycle)
        viewPager2.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = tabArray[position]
        }.attach()

        tabLayout.getTabAt(0)?.select()

        for (i in 0 until tabArray.size) {
            val tab = tabLayout.getTabAt(i)
            when (i) {
                0 -> tab?.view?.background = ContextCompat.getDrawable(requireContext(), R.drawable.tab_indicator_coffee)
                1 -> tab?.view?.background = ContextCompat.getDrawable(requireContext(), R.drawable.tab_indicator_cooki)
                2 -> tab?.view?.background = ContextCompat.getDrawable(requireContext(), R.drawable.tab_indicator_cocktails)
                3 -> tab?.view?.background = ContextCompat.getDrawable(requireContext(), R.drawable.tab_indicator_desert)
                4 -> tab?.view?.background = ContextCompat.getDrawable(requireContext(), R.drawable.tab_indicator_tea)
                else -> tab?.view?.background = ContextCompat.getDrawable(requireContext(), R.drawable.default_tab_indicator)
            }
            if (i != 0) {
                tab?.view?.background?.alpha = 90
            }
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    when (it.position) {
                        0 -> it.view.background = ContextCompat.getDrawable(requireContext(), R.drawable.tab_indicator_coffee)
                        1 -> it.view.background = ContextCompat.getDrawable(requireContext(), R.drawable.tab_indicator_cooki)
                        2 -> it.view.background = ContextCompat.getDrawable(requireContext(), R.drawable.tab_indicator_cocktails)
                        3 -> it.view.background = ContextCompat.getDrawable(requireContext(), R.drawable.tab_indicator_desert)
                        4 -> it.view.background = ContextCompat.getDrawable(requireContext(), R.drawable.tab_indicator_tea)
                        else -> it.view.background = ContextCompat.getDrawable(requireContext(), R.drawable.default_tab_indicator)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.view?.background?.alpha = 90
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

}