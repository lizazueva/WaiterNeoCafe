package com.example.waiterneocafe.view.newOrder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.navigation.fragment.findNavController
import com.example.clientneowaiter.R
import com.example.clientneowaiter.databinding.FragmentNewOrderChosedTableBinding
import com.example.waiterneocafe.utils.Utils
import com.example.waiterneocafe.view.menu.SearchFragment
import com.example.waiterneocafe.view.menu.TabMenuFragment
import com.example.waiterneocafe.viewModel.MenuViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class NewOrderChosedTableFragment : Fragment() {

    private lateinit var binding: FragmentNewOrderChosedTableBinding
    private val menuViewModel: MenuViewModel by sharedViewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewOrderChosedTableBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpFragment()
        showStartMenuFragment()
        setUpListeners()
        dataTable()
    }

    private fun dataTable() {
        val idTable = arguments?.getString("id") as String
        Utils.table =  idTable
        binding.textChoosed.text = getString(R.string.text_choosed, idTable)
    }

    private fun setUpListeners() {
        binding.imageNotification.setOnClickListener {
            findNavController().navigate(R.id.action_newOrderChosedTableFragment_to_notificationsFragment)
        }
        binding.imageBack.setOnClickListener {
            findNavController().navigate(R.id.action_newOrderChosedTableFragment_to_newOrderFragment)
        }
    }

    private fun showStartMenuFragment() {
        val transaction = childFragmentManager.beginTransaction()
        val fragment = TabMenuNewOrderFragment()
        transaction.replace(R.id.fragment_new_order, fragment)
        transaction.commit()
    }

    private fun setUpFragment() {
        binding.searchMenu.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                val query = p0?.trim()
                if (query.isNullOrEmpty()) {
                    val transaction = childFragmentManager.beginTransaction()
                    val fragment = TabMenuNewOrderFragment()
                    transaction.replace(R.id.fragment_new_order, fragment)
                    transaction.commit()
                } else {
                    query.let {
                        menuViewModel.getSearchResult(query)
                    }
                    val transaction = childFragmentManager.beginTransaction()
                    val fragment = SearchNewOrderFragment()
                    transaction.replace(R.id.fragment_new_order, fragment)
                    transaction.commit()
                }
                return true
            }
        })
    }
}
