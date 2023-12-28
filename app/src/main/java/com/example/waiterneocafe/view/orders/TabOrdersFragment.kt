package com.example.waiterneocafe.view.orders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.clientneowaiter.R
import com.example.clientneowaiter.databinding.FragmentTabOrdersBinding
import com.example.waiterneocafe.adapters.AdapterStatusOrders
import com.example.waiterneocafe.model.order.Orders
import com.example.waiterneocafe.viewModel.OrdersViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class TabOrdersFragment : Fragment() {

    private lateinit var binding: FragmentTabOrdersBinding
    private val ordersViewModel: OrdersViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTabOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeStatusList()
    }

    private fun observeStatusList() {
        ordersViewModel.getOrders(
            onSuccess = {
                viewPager(it)

            },
            onError = {
                Toast.makeText(
                    requireContext(),
                    "Невозможно загрузить заказы",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )
    }

    private fun viewPager(orders: List<Orders.Order>) {
        val tabLayout = binding.tabLayout
        val viewPager2 = binding.pager
        val tabArray = arrayOf(
            "Все",
            "Новый",
            "В процессе",
            "Готово",
            "Отменено",
            "Завершено"
        )
        val adapter = AdapterStatusOrders(childFragmentManager, lifecycle, orders)
        viewPager2.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = tabArray[position]
        }.attach()

        tabLayout.getTabAt(0)?.select()


        for (i in 0 until tabArray.size) {
            val tab = tabLayout.getTabAt(i)
            when (i) {
                0 -> tab?.view?.background = ContextCompat.getDrawable(requireContext(), R.drawable.tab_indicator_status_all)
                1 -> tab?.view?.background = ContextCompat.getDrawable(requireContext(), R.drawable.tab_indicator_status_new)
                2 -> tab?.view?.background = ContextCompat.getDrawable(requireContext(), R.drawable.tab_indicator_status_in_progress)
                3 -> tab?.view?.background = ContextCompat.getDrawable(requireContext(), R.drawable.tab_indicator_status_ready)
                4 -> tab?.view?.background = ContextCompat.getDrawable(requireContext(), R.drawable.tab_indicator_status_canceled)
                5 -> tab?.view?.background = ContextCompat.getDrawable(requireContext(), R.drawable.tab_indicator_status_completed)
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
                        0 -> it.view.background = ContextCompat.getDrawable(requireContext(), R.drawable.tab_indicator_status_all)
                        1 -> it.view.background = ContextCompat.getDrawable(requireContext(), R.drawable.tab_indicator_status_new)
                        2 -> it.view.background = ContextCompat.getDrawable(requireContext(), R.drawable.tab_indicator_status_in_progress)
                        3 -> it.view.background = ContextCompat.getDrawable(requireContext(), R.drawable.tab_indicator_status_ready)
                        4 -> it.view.background = ContextCompat.getDrawable(requireContext(), R.drawable.tab_indicator_status_canceled)
                        5 -> it.view.background = ContextCompat.getDrawable(requireContext(), R.drawable.tab_indicator_status_completed)
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

    override fun onResume() {
        super.onResume()
        observeStatusList()
    }

}