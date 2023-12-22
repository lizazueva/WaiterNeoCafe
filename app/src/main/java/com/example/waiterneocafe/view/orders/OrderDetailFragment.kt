package com.example.waiterneocafe.view.orders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clientneowaiter.R
import com.example.clientneowaiter.databinding.FragmentOrderDetailBinding
import com.example.waiterneocafe.adapters.AdapterOrder
import com.example.waiterneocafe.adapters.AdapterTableOrder
import com.example.waiterneocafe.model.menu.CheckPosition
import com.example.waiterneocafe.model.menu.Products
import com.example.waiterneocafe.model.order.DetailOrder
import com.example.waiterneocafe.utils.OrderUtils
import com.example.waiterneocafe.utils.Resource
import com.example.waiterneocafe.viewModel.OrdersViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderDetailFragment : Fragment() {

    private var _binding: FragmentOrderDetailBinding? = null
    private val binding get() = _binding!!
    private val ordersViewModel: OrdersViewModel by viewModel()
    private lateinit var adapterOrderDetail: AdapterTableOrder



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrderDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpListeners()
        setDataDetailProfile()
    }

    private fun setUpListeners() {
        binding.imageBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.imageNotification.setOnClickListener {
            findNavController().navigate(R.id.action_orderDetailFragment_to_notificationsFragment)
        }
    }

    private fun setDataDetailProfile() {
        val orderId = arguments?.getInt("id")
        if (orderId != null) {
            ordersViewModel.orderDetail(orderId)
        }
        ordersViewModel.detailProduct.observe(viewLifecycleOwner){order->
            when (order) {
                is Resource.Success -> {
                    order.data?.let { detailInfo ->
                        setDataDetail(detailInfo)
                    }
                }

                is Resource.Error -> {
                    order.message?.let {
                        Toast.makeText(
                            requireContext(),
                            "Не удалось загрузить заказ",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                is Resource.Loading -> {

                }
            }
        }
    }

    private fun setDataDetail(detailInfo: DetailOrder) {
        when(detailInfo.order.status) {
            "new" -> binding.imageStatus.setImageResource(R.drawable.img_ellipse_red).also {
                binding.textStatusOrder.text = "Новый"
            }
            "in progress" -> binding.imageStatus.setImageResource(R.drawable.img_ellipse_yellow).also {
                binding.textStatusOrder.text = "В процессе"
            }
            "ready" -> binding.imageStatus.setImageResource(R.drawable.img_ellipse_green).also {
                binding.textStatusOrder.text = "Готово"
            }
            "canceled" -> binding.imageStatus.setImageResource(R.drawable.img_ellipse_black).also {
                binding.textStatusOrder.text = "Отменено"
            }
            "completed" -> binding.imageStatus.setImageResource(R.drawable.img_ellipse_grey).also {
                binding.textStatusOrder.text = "Завершено"
            }
        }
        binding.textWaiter.text = getString(R.string.text_waiter, detailInfo.order.waiter_name)
        binding.textTable.text = getString(R.string.text_choosed, detailInfo.order.table.toString())
        binding.textChoosed.text = getString(R.string.text_choosed, detailInfo.order.table.toString())
        binding.textResultSum.text =getString(R.string.text_result_sum, detailInfo.order.total_price.toDouble().toInt())
        binding.textOpenedTime.text = getString(R.string.text_opened_time, detailInfo.order.exact_time)

        setUpAdapter(detailInfo.order.items)


    }

    private fun setUpAdapter(items: List<DetailOrder.Order.Item>) {
        adapterOrderDetail = AdapterTableOrder()
        binding.recyclerOrder.adapter = adapterOrderDetail
        binding.recyclerOrder.layoutManager = LinearLayoutManager(requireContext())
        adapterOrderDetail.differ.submitList(items)

        adapterOrderDetail.setOnItemClick(object: AdapterTableOrder.ListClickListener<DetailOrder.Order.Item>{

            override fun onAddClick(data: DetailOrder.Order.Item, position: Int) {

            }

            override fun onRemoveClick(data: DetailOrder.Order.Item, position: Int) {
            }

        })

    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}