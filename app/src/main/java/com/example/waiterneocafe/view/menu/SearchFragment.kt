package com.example.waiterneocafe.view.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clientneowaiter.R
import com.example.clientneowaiter.databinding.FragmentSearchBinding
import com.example.waiterneocafe.adapters.AdapterMenu
import com.example.waiterneocafe.model.menu.Product

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapterProduct: AdapterMenu

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()

    }

    private fun setUpAdapter() {
        adapterProduct = AdapterMenu()
        binding.recyclerSearch.adapter = adapterProduct
        binding.recyclerSearch.layoutManager = LinearLayoutManager(requireContext())
    }



}
