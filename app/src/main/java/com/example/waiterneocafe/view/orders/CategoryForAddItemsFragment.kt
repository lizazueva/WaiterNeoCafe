package com.example.waiterneocafe.view.orders

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clientneowaiter.R
import com.example.clientneowaiter.databinding.FragmentCategoryForAddItemsBinding
import com.example.waiterneocafe.adapters.AdapterNewOrderPosition
import com.example.waiterneocafe.model.menu.Products
import com.example.waiterneocafe.utils.Resource
import com.example.waiterneocafe.utils.Utils
import com.example.waiterneocafe.viewModel.MenuViewModel
import com.example.waiterneocafe.viewModel.OrdersViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoryForAddItemsFragment : Fragment() {
    private var _binding: FragmentCategoryForAddItemsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapterProduct: AdapterNewOrderPosition
    private val menuViewModel: MenuViewModel by viewModel()
    private val ordersViewModel: OrdersViewModel by viewModel()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoryForAddItemsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val categoryId = arguments?.getInt("categoryId")

        setUpListeners()

        if (categoryId != null) {
            dataMenuCategory(categoryId)
        }
        observeMenuCategory()

    }

    private fun setUpListeners() {

    }

    private fun dataMenuCategory(categoryId: Int) {
        menuViewModel.getMenuCategory(categoryId)
    }

    private fun observeMenuCategory() {
        menuViewModel.menuCategory.observe(viewLifecycleOwner){menuCategory ->
            when(menuCategory){
                is Resource.Success ->{
                    menuCategory.data?.let { menu ->
                        setUpAdapter(menu)
                    }

                }
                is Resource.Error ->{
                    menuCategory.message?.let {
                        Toast.makeText(requireContext(),
                            "Не удалось загрузить товары по категории",
                            Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Loading ->{

                }
            }
        }

    }

    private fun setUpAdapter(menu: List<Products>) {
        adapterProduct = AdapterNewOrderPosition()
        binding.recyclerCategory.adapter = adapterProduct
        binding.recyclerCategory.layoutManager = LinearLayoutManager(requireContext())
        adapterProduct.differ.submitList(menu)
        adapterProduct.setOnItemClick(object: AdapterNewOrderPosition.ListClickListener<Products> {

            override fun onAddClick(data: Products, position: Int) {
                observeAddItem(data)

            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeAddItem(data: Products) {
        val orderId = arguments?.getInt("orderId")

        if (orderId != null) {
            ordersViewModel.addItemOrder(orderId, data.id, data.is_ready_made_product, 1,
                onSuccess = {
                    Toast.makeText(
                        requireContext(),
                        "Позиция успешно добавлена",
                        Toast.LENGTH_SHORT
                    ).show()
                    findNavController().navigate(R.id.action_tabForAddItemsFragment_to_orderDetailFragment)
                },
                onError = {
                    Toast.makeText(
                        requireContext(),
                        "Товара больше нет",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }
    }

    companion object {
        fun newInstance(id: Int, orderId: Int?): Fragment {
            val fragment = CategoryForAddItemsFragment()
            val args = Bundle()
            args.putInt("categoryId", id)
            orderId?.let { args.putInt("orderId", it) }
            fragment.arguments = args
            return fragment
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}