package com.example.waiterneocafe.view.newOrder

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clientneowaiter.R
import com.example.clientneowaiter.databinding.BottomSheetCoffeeBinding
import com.example.clientneowaiter.databinding.BottomSheetOrderBinding
import com.example.clientneowaiter.databinding.FragmentCategoryNewOrderBinding
import com.example.waiterneocafe.adapters.AdapterMilk1
import com.example.waiterneocafe.adapters.AdapterNewOrderPosition
import com.example.waiterneocafe.adapters.AdapterOrder
import com.example.waiterneocafe.adapters.AdapterSyrup
import com.example.waiterneocafe.model.Milk
import com.example.waiterneocafe.model.Syrup
import com.example.waiterneocafe.model.menu.CheckPosition
import com.example.waiterneocafe.model.menu.Products
import com.example.waiterneocafe.utils.OrderUtils
import com.example.waiterneocafe.utils.Resource
import com.example.waiterneocafe.viewModel.MenuViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoryNewOrderFragment : Fragment() {

    private lateinit var binding: FragmentCategoryNewOrderBinding
    private lateinit var adapterProduct: AdapterNewOrderPosition
    private lateinit var adapterMilk: AdapterMilk1
    private lateinit var adapterSyrup: AdapterSyrup
    private lateinit var adapterOrder: AdapterOrder
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
        dataOrderButton()


    }

    private fun dataOrderButton() {
        val order = OrderUtils.getCartItems()
        if (order.isNotEmpty()){
            binding.layoutOrder.visibility = View.VISIBLE
            val sum = order.sumBy { products ->
                (products.price.toDouble() * products.quantityForCard).toInt()
            }
            binding.textAmount.text = "$sum c"
            binding.textOrder.text = getString(R.string.text_order_btn, 1)
        } else {
            binding.layoutOrder.visibility = View.GONE
        }
    }

    private fun setUpListeners() {
        binding.layoutOrder.setOnClickListener {
            val order = OrderUtils.getCartItems()
            val sum = order.sumBy { products ->
                (products.price.toDouble() * products.quantityForCard).toInt()}
            dialogOrder(requireContext(), order, sum)
        }

    }

    private fun dialogOrder(context: Context, order: List<Products>, sum: Int) {
        val bindingOrder: BottomSheetOrderBinding =
            BottomSheetOrderBinding.inflate(LayoutInflater.from(context))
        val dialog = BottomSheetDialog(context)
        dialog.setContentView(bindingOrder.root)
        bindingOrder.textNumberOrder.text = getString(R.string.text_number_order, 1)
        bindingOrder.textResultSum.text = getString(R.string.text_result_sum, sum)
        setUpAdapterOrder(order, bindingOrder, dialog)
        dialog.show()
        bindingOrder.imageCancel.setOnClickListener {
            dialog.dismiss()
            dataOrderButton()
        }
        bindingOrder.btnAdd.setOnClickListener {

            //для теста
            dialog.dismiss()
            findNavController().navigate(R.id.action_newOrderChosedTableFragment_to_orderGoodFragment)

        }

    }

    private fun setUpAdapterOrder(
        order: List<Products>,
        bindingOrder: BottomSheetOrderBinding,
        dialog: BottomSheetDialog
    ) {
        adapterOrder = AdapterOrder()
        bindingOrder.recyclerOrder.adapter = adapterOrder
        bindingOrder.recyclerOrder.layoutManager = LinearLayoutManager(requireContext())
        adapterOrder.differ.submitList(order)

        adapterOrder.setOnItemClick(object: AdapterOrder.ListClickListener<Products>{
            override fun onClick(data: Products, position: Int) {

            }

            override fun onAddClick(data: Products, position: Int) {
                if (OrderUtils.isInCart(data.id)) {
                    val quantity = OrderUtils.getQuantity(data.id) + 1
                    checkPositionOrder(
                        data,
                        CheckPosition(data.is_ready_made_product, data.id, quantity),
                        bindingOrder, dialog)
                } else {
                    checkPositionOrder(
                        data, CheckPosition(
                            data.is_ready_made_product,
                            data.id,
                            1
                        ), bindingOrder, dialog
                    )
                }
            }

            override fun onRemoveClick(data: Products, position: Int) {
                if (data.quantityForCard > 1) {
                    OrderUtils.removeItem(data)
                } else {
                    OrderUtils.removeItem(data)
                    adapterOrder.removeItem(position)
                    // Обновление списка после удаления
                }
                setDataOrder(bindingOrder, dialog)
            }

        })

    }

    private fun setDataOrder(bindingOrder: BottomSheetOrderBinding, dialog: BottomSheetDialog) {
        val order = OrderUtils.getCartItems()
        if (order.isNullOrEmpty()) {
            //закрыть диалог
            dialog.dismiss()
            dataOrderButton()


        } else {
            updateTotalAmount(order, bindingOrder)
            dataOrderButton()
        }
    }

    private fun updateTotalAmount(order: List<Products>, bindingOrder: BottomSheetOrderBinding) {
        val totalOrderAmount =  order.sumBy { products ->
            (products.price.toDouble() * products.quantityForCard).toInt()}
        bindingOrder.textResultSum.text = getString(R.string.text_result_sum, totalOrderAmount)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun checkPositionOrder(
        data: Products,
        checkPosition: CheckPosition,
        bindingOrder: BottomSheetOrderBinding,
        dialog: BottomSheetDialog
    ) {
        menuViewModel.createProduct(checkPosition,
            onSuccess = {
                OrderUtils.addItem(data)
                adapterOrder.notifyDataSetChanged()
                setDataOrder(bindingOrder, dialog)

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

    private fun dataMenuCategory(categoryId: Int) {
        menuViewModel.getMenuCategory(categoryId)
    }

    private fun observeMenuCategory() {
        menuViewModel.menuCategory.observe(viewLifecycleOwner){menuCategory ->
            when(menuCategory){
                is Resource.Success ->{
                    menuCategory.data?.let { menu ->
                        setUpAdapter(menu)
                        dataOrderButton()
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
//                if (data.category.name == "Кофе"){
//                    dialog(requireContext(), data)
//                }
                if (OrderUtils.isInCart(data.id)) {
                    val quantity = OrderUtils.getQuantity(data.id) + 1
                    checkPosition(data, CheckPosition(
                        data.is_ready_made_product,
                        data.id,
                        quantity))
                } else {
                    checkPosition(data, CheckPosition(
                        data.is_ready_made_product,
                        data.id,
                        1))
                    dataOrderButton()
                }

            }
        })
    }

    private fun checkPosition(data: Products, checkPosition: CheckPosition) {
        menuViewModel.createProduct(checkPosition,
            onSuccess = {
                OrderUtils.addItem(data)
                dataOrderButton()
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

    override fun onResume() {
        super.onResume()
        dataOrderButton()
    }

}
