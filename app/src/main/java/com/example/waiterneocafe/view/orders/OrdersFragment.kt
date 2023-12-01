package com.example.waiterneocafe.view.orders

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.clientneowaiter.R
import com.example.clientneowaiter.databinding.FragmentOrdersBinding
import com.example.waiterneocafe.adapters.SliderAdapterOrders
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class OrdersFragment : Fragment() {

    private lateinit var binding: FragmentOrdersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpListeners()
        viewPager()
    }


    private fun setUpListeners() {
        binding.imageNotification.setOnClickListener {
            findNavController().navigate(R.id.action_ordersFragment_to_notificationsFragment)
        }
        binding.imageProfile.setOnClickListener {
            findNavController().navigate(R.id.action_ordersFragment_to_profileFragment)
        }
    }

    private fun viewPager() {
        val tabLayout = binding.tabLayout
        val viewPager2 = binding.pager
        val tabArray = arrayOf(
            "Столы",
            "Заказы"
        )
        val adapter = SliderAdapterOrders(childFragmentManager, lifecycle)
        viewPager2.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = tabArray[position]
        }.attach()

        tabLayout.getTabAt(0)?.select()

        for (i in 0 until tabArray.size) {
            val tab = tabLayout.getTabAt(i)
            when (i) {
                    0 -> tab?.view?.background = ContextCompat.getDrawable(requireContext(), R.drawable.backgr_tab_select_orders)
                else -> tab?.view?.background = ContextCompat.getDrawable(requireContext(), R.drawable.backgr_tab_select_table)
            }
            if (i != 0) {
                tab?.view?.background = null
            }
        }

//
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    for (i in 0 until tabLayout.tabCount) {
                        val currentTab = tabLayout.getTabAt(i)
                        if (i == it.position) {
                            currentTab?.view?.background = ContextCompat.getDrawable(
                                requireContext(),
                                when (i) {
                                    0 -> R.drawable.backgr_tab_select_orders
                                    else -> R.drawable.backgr_tab_select_table
                                }
                            )
                        } else {
                            currentTab?.view?.background = ColorDrawable(Color.TRANSPARENT)
                        }
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.view?.background = ColorDrawable(Color.TRANSPARENT)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

}