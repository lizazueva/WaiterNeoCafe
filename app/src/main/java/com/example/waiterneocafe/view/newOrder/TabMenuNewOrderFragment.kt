package com.example.waiterneocafe.view.newOrder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.clientneowaiter.R
import com.example.clientneowaiter.databinding.FragmentTabMenuNewOrderBinding
import com.example.waiterneocafe.adapters.SliderAdapterNewOrder
import com.example.waiterneocafe.model.menu.Products
import com.example.waiterneocafe.utils.Resource
import com.example.waiterneocafe.viewModel.MenuViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class TabMenuNewOrderFragment : Fragment() {

    private lateinit var binding: FragmentTabMenuNewOrderBinding
    private val menuViewModel: MenuViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTabMenuNewOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
        dataCategories()
        observeCategories()


    }

    private fun dataCategories() {
        menuViewModel.getCategories()
    }

    private fun observeCategories() {
        menuViewModel.categories.observe(viewLifecycleOwner){categories ->
            when(categories){
                is Resource.Success ->{
                    val categoryList = categories.data
                    categoryList?.let { categories ->
                        viewPager(categories)
                    }

                }
                is Resource.Error ->{
                    categories.message?.let {
                        Toast.makeText(requireContext(),
                            "Не удалось загрузить позиции по категориям",
                            Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Loading ->{

                }
            }
        }
    }

    private fun setUpListeners() {

    }

    private fun viewPager(categories: List<Products.Category>) {
        val tabLayout = binding.tabLayout
        val viewPager2 = binding.pager
        val tabArray = categories.map { it.name }.toTypedArray()
        val adapter = SliderAdapterNewOrder(childFragmentManager, lifecycle, categories)
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