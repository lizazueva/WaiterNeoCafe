package com.example.waiterneocafe.view.orders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.clientneowaiter.R
import com.example.clientneowaiter.databinding.FragmentTablesBinding

class TablesFragment : Fragment() {

    private lateinit var binding: FragmentTablesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTablesBinding.inflate(inflater, container, false)
        return binding.root
    }
}