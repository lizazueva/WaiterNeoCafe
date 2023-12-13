package com.example.waiterneocafe.view.newOrder

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clientneowaiter.databinding.BottomSheetCoffeeBinding
import com.example.clientneowaiter.databinding.FragmentCategoryNewOrderBinding
import com.example.waiterneocafe.adapters.AdapterMilk1
import com.example.waiterneocafe.adapters.AdapterNewOrderPosition
import com.example.waiterneocafe.adapters.AdapterSyrup
import com.example.waiterneocafe.model.Milk
import com.example.waiterneocafe.model.Syrup
import com.example.waiterneocafe.model.menu.Products
import com.example.waiterneocafe.utils.Resource
import com.example.waiterneocafe.viewModel.MenuViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoryNewOrderFragment : Fragment() {

    private lateinit var binding: FragmentCategoryNewOrderBinding
    private lateinit var adapterProduct: AdapterNewOrderPosition
    private lateinit var adapterMilk: AdapterMilk1
    private lateinit var adapterSyrup: AdapterSyrup
    private val menuViewModel: MenuViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryNewOrderBinding.inflate(inflater, container, false)
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
                if (data.category.name == "Кофе"){
                    dialog(requireContext(), data)


                }
            }
        })
    }

    fun dialog(context: Context, currentItem: Products) {
        val binding: BottomSheetCoffeeBinding =
            BottomSheetCoffeeBinding.inflate(LayoutInflater.from(context))
        val dialog = BottomSheetDialog(context)
        dialog.setContentView(binding.root)
        binding.textTitle.text = currentItem.name
        setUpAdapterSyrup(binding)
        setUpAdapterMilk(binding)
        dialog.show()

    }

    private fun setUpAdapterMilk(bindingMilk: BottomSheetCoffeeBinding) {
        val milk = arrayListOf(
            Milk(1, false, "Безлактозное"),
            Milk(1, false, "Соевое"),
            Milk(3, false, "Кокосовое")
        )
        adapterMilk = AdapterMilk1()
        bindingMilk.recyclerMilk.adapter = adapterMilk
        bindingMilk.recyclerMilk.layoutManager = LinearLayoutManager(requireContext())
        adapterMilk.differ.submitList(milk)
    }

    private fun setUpAdapterSyrup(bindingSyrup: BottomSheetCoffeeBinding) {
        val syrup = arrayListOf(
            Syrup(1, false, "Кленовый"),
            Syrup(2, false, "Малиновый"),
            Syrup(3, false, "Вишневый")
        )

        adapterSyrup = AdapterSyrup()
        bindingSyrup.recyclerSyrup.adapter = adapterSyrup
        bindingSyrup.recyclerSyrup.layoutManager = LinearLayoutManager(requireContext())
        adapterSyrup.differ.submitList(syrup)
    }

    companion object {
    fun newInstance(id: Int): Fragment {
        val fragment = CategoryNewOrderFragment()
        val args = Bundle()
        args.putInt("categoryId", id)
        fragment.arguments = args
        return fragment
    }
}

}
