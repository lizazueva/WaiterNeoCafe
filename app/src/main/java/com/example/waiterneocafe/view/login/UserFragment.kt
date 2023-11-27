package com.example.waiterneocafe.view.login

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.clientneowaiter.R
import com.example.clientneowaiter.databinding.FragmentUserBinding
import com.example.waiterneocafe.MainActivity
import com.example.waiterneocafe.utils.DateMask
import com.google.android.material.textfield.TextInputLayout

class UserFragment : Fragment() {
    private lateinit var binding: FragmentUserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    fun setupListeners() {
        binding.textInputName.addTextChangedListener(inputText)
        binding.textInputSurname.addTextChangedListener(inputText)
        val date = binding.textInputDate
        val maskDate = DateMask(date)
        binding.textInputDate.addTextChangedListener(inputText)
        binding.textInputDate.addTextChangedListener(maskDate)



        binding.btnEnter.setOnClickListener {
            if (validateInput()) {

                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
            }

        }
    }

    private val inputText = object : TextWatcher{
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            validateInput()
        }

        override fun afterTextChanged(p0: Editable?) {
        }

    }

    private fun validateInput(): Boolean {

        var isValid = true

        val nameInput = binding.textInputName.text.toString().trim()
        val surnameInput = binding.textInputSurname.text.toString().trim()
        val dateInput = binding.textInputDate.text.toString().trim()

        val isDateMatches = dateInput.matches(Regex("\\d{2}\\.\\d{2}\\.\\d{4}"))

        if (nameInput.isEmpty()) {
            setFieldColor(binding.textName, binding.textInputLayoutName, true, "Введите свое имя")
            isValid = false
        } else {
            setFieldColor(binding.textName, binding.textInputLayoutName, false, "")
        }

        if (surnameInput.isEmpty()) {
            setFieldColor(binding.textSurname, binding.textInputLayoutSurname, true, "Введите свою фамилию")
            isValid = false
        } else {
            setFieldColor(binding.textSurname, binding.textInputLayoutSurname, false, "")
        }

        if (dateInput.isEmpty()) {
            setFieldColor(binding.textDate, binding.textInputLayoutDate, true, "Введите дату рождения")
            isValid = false
        } else if (!isDateMatches) {
            setFieldColor(binding.textDate, binding.textInputLayoutDate, true, "Введите корректный формат даты")
            isValid = false
        } else {
            setFieldColor(binding.textDate, binding.textInputLayoutDate, false, "")
        }


        return isValid
    }

    private fun setFieldColor(textView: TextView, textInputLayout: TextInputLayout, hasError: Boolean, errorText: String) {

        val color = ContextCompat.getColor(requireContext(), R.color.grey_hint)

        if (hasError) {
            textInputLayout.setBackgroundResource(R.drawable.button_backgr_error)
            textInputLayout.setStartIconTintList(ColorStateList.valueOf(Color.RED))
            textView.setTextColor(Color.RED)
            textView.text = errorText

        }else{
            textInputLayout.setBackgroundResource(R.drawable.button_backgr_grey)
            textInputLayout.setStartIconTintList(ColorStateList.valueOf(color))
            textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey_hint))
            textView.text = when (textView.id) {
                R.id.text_name -> "Имя"
                R.id.text_surname -> "Фамилия"
                R.id.text_date -> "Дата рождения"
                else -> ""
            }
        }


    }

}