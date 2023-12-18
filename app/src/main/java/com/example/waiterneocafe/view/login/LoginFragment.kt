package com.example.waiterneocafe.view.login

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.clientneowaiter.R
import com.example.clientneowaiter.databinding.FragmentLoginBinding
import com.example.waiterneocafe.api.RetrofitInstance
import com.example.waiterneocafe.model.login.LoginRequest
import com.example.waiterneocafe.utils.Resource
import com.example.waiterneocafe.utils.Utils
import com.example.waiterneocafe.viewModel.LoginViewModel
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }



    private fun setupListeners() {
        binding.textInputName.addTextChangedListener(inputText)
        binding.textInputPassword.addTextChangedListener(inputText)


        binding.btnLogin.setOnClickListener {
//            для теста
//            findNavController().navigate(R.id.action_loginFragment_to_codeFragment)

            binding.btnLogin.setOnClickListener {

                //для теста
                val password = binding.textInputName.text.toString()
                val username = binding.textInputPassword.text.toString()

                lifecycleScope.launch {
                    try {
                        val response = RetrofitInstance.api.login2(LoginRequest(password, username))

                        if (response.isSuccessful) {
                            val access = response.body()?.access
                            access?.let { Utils.access_token = it }

                            findNavController().navigate(R.id.action_loginFragment_to_codeFragment)

                        } else {
                            val error = response.body()?.detail
                            withContext(Dispatchers.Main) {
                                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
                            }
                            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        // Обработка исключения
                    }
                }


//            data()
            }
        }
    }

        private fun data() {
            val password = binding.textInputName.text.toString()
            val username = binding.textInputPassword.text.toString()
            loginViewModel.login(password, username)
            observe()

        }

        private fun observe() {
            loginViewModel.preToken.observe(viewLifecycleOwner){ preToken->
                when(preToken) {
                    is Resource.Success ->{
                        val navController = findNavController()
                        if (navController.currentDestination?.id == R.id.loginFragment) {
                            findNavController().navigate(R.id.action_loginFragment_to_codeFragment)
                        }
                    }
                    is Resource.Error ->{
                        val errorMessage = "Неуспешная авторизация"
                        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Loading ->{

                    }
                }
            }
        }

    private val inputText = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {


            binding.btnLogin.isEnabled = validateInput()
        }

        override fun afterTextChanged(p0: Editable?) {}

    }



    private fun validateInput(): Boolean {
        val nameInput = binding.textInputName.text.toString().trim()
        val phoneInput = binding.textInputPassword.text.toString().trim()

        if (nameInput.isEmpty()) {
            setFieldColor(binding.textLogin, binding.textInputLayoutName, Color.RED, true)
            binding.textInputName.error = "Заполните это поле"

            return false
        }

        if (phoneInput.isEmpty()) {
            setFieldColor(binding.textPassword, binding.textInputLayoutPassword, Color.RED, true)
            binding.textInputPassword.error = "Заполните это поле"
            return false
        }

        binding.textInputPassword.error = null
        binding.textInputName.error = null
        setFieldColor(binding.textLogin, binding.textInputLayoutName, Color.BLACK, false)
        setFieldColor(binding.textPassword, binding.textInputLayoutPassword, Color.BLACK, false)


        return true
    }

    private fun setFieldColor(textView: TextView, textInputLayout: TextInputLayout, color: Int, hasError: Boolean) {

        val color = ContextCompat.getColor(requireContext(), R.color.grey_hint)

        if (hasError) {
            textInputLayout.setBackgroundResource(R.drawable.button_backgr_error)
            textInputLayout.setStartIconTintList(ColorStateList.valueOf(Color.RED))
        }else{
            textInputLayout.setBackgroundResource(R.drawable.button_backgr_grey)
            textInputLayout.setStartIconTintList(ColorStateList.valueOf(color))
        }


    }

}