package com.neobis.waiterneocafe.view.orders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.neobis.clientneowaiter.databinding.FragmentTablesBinding
import com.neobis.waiterneocafe.adapters.AdapterTable
import com.neobis.waiterneocafe.model.Table
import com.neobis.waiterneocafe.model.TableResponse
import com.neobis.waiterneocafe.utils.Resource
import com.neobis.waiterneocafe.viewModel.NewOrderViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TablesFragment : Fragment() {

    private var _binding: FragmentTablesBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapterTable: AdapterTable
    private val newOrderViewModel: NewOrderViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTablesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpTable()

    }

    private fun setUpTable() {
        newOrderViewModel.getTables()
        newOrderViewModel.table.observe(viewLifecycleOwner){table->
            when (table) {
                is Resource.Success -> {
                    val tableList = table.data
                    tableList?.let { table ->
                        setUpAdapter(tableList)
                    }
                }

                is Resource.Error -> {
                    table.message?.let {
                        Toast.makeText(
                            requireContext(),
                            "Не удалось загрузить позиции по категориям",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                is Resource.Loading -> {
                }
            }
        }

    }

    private fun setUpAdapter(tableList: TableResponse) {

        val tables = convertTableResponse(tableList)

        adapterTable = AdapterTable(requireContext())
        binding.recyclerTable.adapter = adapterTable
        binding.recyclerTable.layoutManager = GridLayoutManager(requireContext(),3)
        adapterTable.differ.submitList(tables)
    }

    fun convertTableResponse(tableResponse: TableResponse): List<Table> {
        val tableList = mutableListOf<Table>()
        tableResponse.tables.forEach { (number, status) ->
            tableList.add(Table(number, status))
        }
        return tableList
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}