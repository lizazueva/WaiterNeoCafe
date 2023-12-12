package com.example.waiterneocafe.view.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.clientneowaiter.R
import com.example.clientneowaiter.databinding.FragmentCodeBinding
import com.example.waiterneocafe.utils.Resource
import com.example.waiterneocafe.viewModel.CodeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CodeFragment : Fragment() {
    private lateinit var binding: FragmentCodeBinding
    private val codeViewModel: CodeViewModel by viewModel()


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
//            data()

//            //для теста
            findNavController().navigate(R.id.action_codeFragment_to_userFragment)
        }
        binding.textSendAgain.setOnClickListener {
            repeatSentCode()
        }
    }

    private fun repeatSentCode() {
        codeViewModel.resendCode()
        codeViewModel.resendCodeResult.observe(viewLifecycleOwner){resendCodeResult->
            when(resendCodeResult){
                is Resource.Success -> {
                    Toast.makeText(requireContext(), "Код отправлен повторно", Toast.LENGTH_SHORT).show()
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), "Не удалось отправить код", Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {

                }
            }
        }

    }

    private fun data() {
        val codeInput1 = binding.editCode1.text.toString().trim()
        val codeInput2 = binding.editCode2.text.toString().trim()
        val codeInput3 = binding.editCode3.text.toString().trim()
        val codeInput4 = binding.editCode4.text.toString().trim()
        val code = "$codeInput1$codeInput2$codeInput3$codeInput4"
//        codeViewModel.confirmLogin(code)
//        observePhone()
        codeViewModel.confirmLogin2(code) { isInStock ->
            if (isInStock) {
                findNavController().navigate(R.id.action_codeFragment_to_userFragment)
            } else {
                binding.textErrorCode.setText("Код введен неверно, попробуйте еще раз")
                binding.textErrorCode.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.error
                    )
                )
            }
        }
//        confirmLogin(code)

    }

    private fun observePhone() {
        codeViewModel.token.observe(viewLifecycleOwner) { token ->
            when (token) {
                is Resource.Success -> {
                    findNavController().navigate(R.id.action_codeFragment_to_userFragment)
                }

                is Resource.Error -> {
                    binding.textErrorCode.setText("Код введен неверно, попробуйте еще раз")
                    binding.textErrorCode.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.error
                        )
                    )
                }

                is Resource.Loading -> {

                }
            }
        }
    }

    private fun setUpTextPhone() {
        val phone = "444 555 444"
        binding.textErrorCode.text = getString(R.string.text_code, phone)
    }

}