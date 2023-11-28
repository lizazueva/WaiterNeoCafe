package com.example.waiterneocafe.view.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.clientneowaiter.R
import com.example.clientneowaiter.databinding.FragmentMenuBinding
import com.example.waiterneocafe.adapters.SliderAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MenuFragment : Fragment() {

    private lateinit var binding: FragmentMenuBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpFragment()
        showStartMenuFragment()
        setUpListeners()
    }
    private fun setUpListeners() {
        binding.imageNotification.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_notificationsFragment)
        }
        binding.imageProfile.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_profileFragment)
        }
    }

    private fun showStartMenuFragment() {
        val transaction = childFragmentManager.beginTransaction()
        val fragment = TabMenuFragment()
        transaction.replace(R.id.fragment_start_menu, fragment)
        transaction.commit()
    }

    private fun setUpFragment() {
        binding.searchMenu.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                val query = p0?.trim()
                if (query.isNullOrEmpty()) {
                    val transaction = childFragmentManager.beginTransaction()
                    val fragment = TabMenuFragment()
                    transaction.replace(R.id.fragment_start_menu, fragment)
                    transaction.commit()
                } else {
                    val transaction = childFragmentManager.beginTransaction()
                    val fragment = SearchFragment()
                    transaction.replace(R.id.fragment_start_menu, fragment)
                    transaction.commit()
                }
                return true
            }
        })
    }

}