package com.example.waiterneocafe.view.newOrder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clientneowaiter.R
import com.example.clientneowaiter.databinding.FragmentNewOrderBinding
import com.example.waiterneocafe.adapters.AdapterNewOrderPosition
import com.example.waiterneocafe.adapters.AdapterSyrup
import com.example.waiterneocafe.adapters.AdapterTable
import com.example.waiterneocafe.model.Syrup
import com.example.waiterneocafe.model.Table
import com.example.waiterneocafe.model.menu.Products
import com.google.android.material.tabs.TabLayout.Tab

class NewOrderFragment : Fragment() {

    private lateinit var binding: FragmentNewOrderBinding
    private lateinit var adapterTable: AdapterTable


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpListeners()
        setUpAdapter()


    }

    private fun setUpListeners() {
        binding.imageNotification.setOnClickListener {
            findNavController().navigate(R.id.action_newOrderFragment_to_notificationsFragment)
        }
        binding.imageProfile.setOnClickListener {
            findNavController().navigate(R.id.action_newOrderFragment_to_profileFragment)
        }
    }

    private fun setUpAdapter() {
        val tables = arrayListOf(
            Table(true, 1),
            Table(false, 2),
            Table(true, 3),
            Table(false, 4)

        )
        adapterTable = AdapterTable(requireContext())
        binding.recyclerTable.adapter = adapterTable
        binding.recyclerTable.layoutManager = GridLayoutManager(requireContext(),3)
        adapterTable.differ.submitList(tables)
        adapterTable.onItemClickListener = { table ->

            val bundle = Bundle().apply {
                putInt("id", table.number)
            }
            findNavController().navigate(
                R.id.action_newOrderFragment_to_newOrderChosedTableFragment,
                bundle
            )
        }
    }
}