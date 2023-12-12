package com.example.waiterneocafe.view.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clientneowaiter.databinding.FragmentCategoryBinding
import com.example.waiterneocafe.adapters.AdapterMenu
import com.example.waiterneocafe.model.menu.Products
import com.example.waiterneocafe.utils.Resource
import com.example.waiterneocafe.viewModel.MenuViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.Serializable

class CategoryFragment : Fragment() {

    private lateinit var binding: FragmentCategoryBinding
    private lateinit var adapterProduct: AdapterMenu
    private val menuViewModel: MenuViewModel by viewModel()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
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
        adapterProduct = AdapterMenu()
        binding.recyclerCategory.adapter = adapterProduct
        binding.recyclerCategory.layoutManager = LinearLayoutManager(requireContext())
        adapterProduct.differ.submitList(menu)

    }

    private fun dataMenuCategory(categoryId: Int) {
        menuViewModel.getMenuCategory(categoryId)
    }


    companion object {
        fun newInstance(categoryId: Int): Fragment {
            val fragment = CategoryFragment()
            val args = Bundle()
            args.putInt("categoryId", categoryId)
            fragment.arguments = args
            return fragment
        }
    }

}