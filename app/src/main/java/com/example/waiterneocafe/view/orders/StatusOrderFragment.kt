package com.example.waiterneocafe.view.orders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clientneowaiter.R
import com.example.clientneowaiter.databinding.FragmentStatusOrderBinding
import com.example.waiterneocafe.adapters.AdapterListOrders
import com.example.waiterneocafe.model.order.Orders
import java.io.Serializable

class StatusOrderFragment : Fragment() {

    private lateinit var binding: FragmentStatusOrderBinding
    private lateinit var adapterStatusOrders: AdapterListOrders


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStatusOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val orders = arguments?.getSerializable("orders") as List<Orders.Order>

        setUpAdapterOrder(orders)



    }

    private fun setUpAdapterOrder(orders: List<Orders.Order>) {
        adapterStatusOrders = AdapterListOrders()
        binding.recyclerCategory.adapter = adapterStatusOrders
        binding.recyclerCategory.layoutManager = LinearLayoutManager(requireContext())
        adapterStatusOrders.differ.submitList(orders)

        adapterStatusOrders.onItemClickListener = { order ->
            val id = order.table_number
            val action = OrdersFragmentDirections.actionOrdersFragmentToOrderDetailFragment(id)
            findNavController().navigate(action)
        }
        }

    fun newInstance(ordersList: List<Orders.Order>): Fragment {
            val fragment = StatusOrderFragment()
            val args = Bundle()
            args.putSerializable("orders", ordersList as Serializable)
            fragment.arguments = args
            return fragment

    }

}