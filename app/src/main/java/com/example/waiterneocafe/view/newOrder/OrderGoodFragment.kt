package com.example.waiterneocafe.view.newOrder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.clientneowaiter.R
import com.example.clientneowaiter.databinding.FragmentOrderGoodBinding

class OrderGoodFragment : Fragment() {
    private var _binding: FragmentOrderGoodBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrderGoodBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpListeners()

    }

    private fun setUpListeners() {
        binding.imageBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnNewOrder.setOnClickListener {
            findNavController().navigate(R.id.action_orderGoodFragment_to_newOrderFragment)
        }
        binding.imageNotification.setOnClickListener {
            findNavController().navigate(R.id.action_orderGoodFragment_to_notificationsFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}