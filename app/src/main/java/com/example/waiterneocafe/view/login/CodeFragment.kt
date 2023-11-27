package com.example.waiterneocafe.view.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.clientneowaiter.R
import com.example.clientneowaiter.databinding.FragmentCodeBinding

class CodeFragment : Fragment() {
    private lateinit var binding: FragmentCodeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setUpTextPhone()
    }

    private fun setupListeners() {
        binding.imageBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnEnter.setOnClickListener {
            data()
        }
        binding.textSendAgain.setOnClickListener {
            repeatSentCode()
        }
    }

    private fun repeatSentCode() {

    }

    private fun data() {
        val codeInput1 = binding.editCode1.text.toString().trim()
        val codeInput2 = binding.editCode2.text.toString().trim()
        val codeInput3 = binding.editCode3.text.toString().trim()
        val codeInput4 = binding.editCode4.text.toString().trim()
        val code = "$codeInput1$codeInput2$codeInput3$codeInput4"

    }

    private fun setUpTextPhone() {
        val phone = "444 555 444"
        binding.textErrorCode.text = getString(R.string.text_code, phone)
    }

}