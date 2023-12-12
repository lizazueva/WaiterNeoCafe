package com.example.waiterneocafe.view.newOrder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clientneowaiter.R
import com.example.clientneowaiter.databinding.FragmentNewOrderChosedTableBinding
import com.example.clientneowaiter.databinding.FragmentSearchNewOrderBinding
import com.example.waiterneocafe.adapters.AdapterMenu
import com.example.waiterneocafe.adapters.AdapterNewOrderPosition
import com.example.waiterneocafe.adapters.AdapterSearch
import com.example.waiterneocafe.adapters.AdapterSearchOrder
import com.example.waiterneocafe.model.menu.SearchResultResponse
import com.example.waiterneocafe.utils.Resource
import com.example.waiterneocafe.viewModel.MenuViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SearchNewOrderFragment : Fragment() {

    private lateinit var binding: FragmentSearchNewOrderBinding
    private lateinit var adapterProduct: AdapterSearchOrder
    private val menuViewModel: MenuViewModel by sharedViewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchNewOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeSearchResult()


    }

    private fun observeSearchResult() {
        menuViewModel.searchItems.observe(viewLifecycleOwner) { searchResult ->
            when (searchResult) {
                is Resource.Success -> {
                    val searchItems = searchResult.data
                    if (searchItems.isNullOrEmpty()) {
                        Toast.makeText(
                            requireContext(),
                            "Товары не найдены",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        setUpAdapter(searchItems)
                    }
                    hideProgressBar()
                }
                is Resource.Error -> {
                    searchResult.message?.let {
                        Toast.makeText(requireContext(),
                            "Не удалось загрузить найденные товары",
                            Toast.LENGTH_SHORT).show()
                    }
                    hideProgressBar()
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }
    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

    private fun setUpAdapter(searchItems: List<SearchResultResponse>) {
        adapterProduct = AdapterSearchOrder()
        binding.recyclerSearch.adapter = adapterProduct
        binding.recyclerSearch.layoutManager = LinearLayoutManager(requireContext())
        adapterProduct.differ.submitList(searchItems)
    }
}