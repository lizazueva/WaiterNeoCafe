package com.example.waiterneocafe.view.newOrder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.clientneowaiter.R
import com.example.clientneowaiter.databinding.FragmentNewOrderBinding

class NewOrderFragment : Fragment() {

    private lateinit var binding: FragmentNewOrderBinding

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

    }

    private fun setUpListeners() {
        binding.imageNotification.setOnClickListener {
            findNavController().navigate(R.id.action_newOrderFragment_to_notificationsFragment)
        }
        binding.imageProfile.setOnClickListener {
            findNavController().navigate(R.id.action_newOrderFragment_to_profileFragment)
        }
    }
}