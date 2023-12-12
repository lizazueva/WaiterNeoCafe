package com.example.waiterneocafe.view.newOrder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clientneowaiter.R
import com.example.clientneowaiter.databinding.FragmentCategoryNewOrderBinding
import com.example.waiterneocafe.adapters.AdapterMenu
import com.example.waiterneocafe.adapters.AdapterNewOrderPosition

class CategoryNewOrderFragment : Fragment() {

    private lateinit var binding: FragmentCategoryNewOrderBinding
    private lateinit var adapterProduct: AdapterNewOrderPosition

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryNewOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        setUpAdapter()

    }

//    private fun setUpAdapter() {
//        adapterProduct = AdapterNewOrderPosition()
//        binding.recyclerCategory.adapter = adapterProduct
//        binding.recyclerCategory.layoutManager = LinearLayoutManager(requireContext())
//        testProduct = arrayListOf (
//            Product(1,"Кофе", "170"),
//            Product(2,"Выпечка", "170"),
//            Product(3,"Коктейли", "170"),
//            Product(4,"Десерты", "170"),
//            Product(5,"Чай", "170"))
//        adapterProduct.differ.submitList(testProduct)
//    }

}