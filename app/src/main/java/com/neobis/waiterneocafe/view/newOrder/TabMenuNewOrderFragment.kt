package com.neobis.waiterneocafe.view.newOrder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.neobis.clientneowaiter.R
import com.neobis.clientneowaiter.databinding.FragmentTabMenuNewOrderBinding
import com.neobis.waiterneocafe.adapters.SliderAdapterNewOrder
import com.neobis.waiterneocafe.model.menu.Products
import com.neobis.waiterneocafe.utils.Resource
import com.neobis.waiterneocafe.viewModel.MenuViewModel
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
        menuViewModel.categories.observe(viewLifecycleOwner) { categories ->
            when (categories) {
                is Resource.Success -> {
                    val categoryList = categories.data
                    categoryList?.let { categories ->
                        viewPager(categories)
                    }
                }

                is Resource.Error -> {
                    categories.message?.let {
                        Toast.makeText(
                            requireContext(),
                            "Не удалось загрузить позиции по категориям",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                is Resource.Loading -> {
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

        val adapter = SliderAdapterNewOrder(viewPager2, childFragmentManager,lifecycle, categories)
        viewPager2.adapter = adapter

        viewPager2.offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT // Отключение повторного использования фрагментов

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = tabArray[position]
        }.attach()

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                updateTabLayout(tabLayout, position)
                adapter.notifyDataSetChanged()
            }
        })

        // Начальная настройка для первой вкладки
        updateTabLayout(tabLayout, 0)
    }

    private fun updateTabLayout(tabLayout: TabLayout, position: Int) {
        for (i in 0 until tabLayout.tabCount) {
            val tab = tabLayout.getTabAt(i)
            when (i) {
                0 -> tab?.view?.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.tab_indicator_coffee)

                1 -> tab?.view?.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.tab_indicator_cooki)

                2 -> tab?.view?.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.tab_indicator_cocktails)

                3 -> tab?.view?.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.tab_indicator_desert)

                4 -> tab?.view?.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.tab_indicator_tea)

                else -> tab?.view?.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.default_tab_indicator)
            }
            if (i != position) {
                tab?.view?.background?.alpha = 90
            }
        }
    }
}