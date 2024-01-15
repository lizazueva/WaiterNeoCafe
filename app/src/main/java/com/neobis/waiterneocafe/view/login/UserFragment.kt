package com.neobis.waiterneocafe.view.login

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
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.neobis.clientneowaiter.R
import com.neobis.clientneowaiter.databinding.FragmentUserBinding
import com.neobis.waiterneocafe.MainActivity
import com.neobis.waiterneocafe.utils.DateMask
import com.neobis.waiterneocafe.utils.Resource
import com.neobis.waiterneocafe.viewModel.UserViewModel
import com.google.android.material.textfield.TextInputLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Locale

class UserFragment : Fragment() {
    private lateinit var binding: FragmentUserBinding
    private  val userViewModel: UserViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataProfile()
        setupListeners()
    }

    private fun dataProfile() {
        userViewModel.getProfile()
        userViewModel.user.observe(viewLifecycleOwner){user ->
            when (user) {
                is Resource.Success -> {
                    binding.textInputName.setText(user.data?.first_name)
                    binding.textInputSurname.setText(user.data?.last_name)
                    val formattedDate =
                        user.data?.let { convertDateFormatGet(it.birth_date, "yyyy-MM-dd", "MM.dd.yyyy") }
                    binding.textInputDate.setText(formattedDate)
                }
                is Resource.Loading ->{

                }
                is Resource.Error ->{

                }
            }
        }
    }

    private fun convertDateFormatGet(dateInput: String, inputFormat: String, outputFormat: String): String {
        val inputFormatter = SimpleDateFormat(inputFormat, Locale.getDefault())
        val outputFormatter = SimpleDateFormat(outputFormat, Locale.getDefault())
        val parsedDate = inputFormatter.parse(dateInput)
        return outputFormatter.format(parsedDate)
    }
    private fun convertDateFormatSent(dateInput: String, inputFormat: String, outputFormat: String): String {
        val inputFormatter = SimpleDateFormat(inputFormat, Locale.getDefault())
        val outputFormatter = SimpleDateFormat(outputFormat, Locale.getDefault())
        val parsedDate = inputFormatter.parse(dateInput)
        return outputFormatter.format(parsedDate)
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
                //для теста
//                val intent = Intent(requireContext(), MainActivity::class.java)
//                startActivity(intent)


                updateProfile()
            }

        }
    }

    private fun updateProfile() {
        val nameInput = binding.textInputName.text.toString().trim()
        val surnameInput = binding.textInputSurname.text.toString().trim()
        val dateInput = binding.textInputDate.text.toString().trim()
        val formattedDate = convertDateFormatSent(dateInput, "MM.dd.yyyy", "yyyy-MM-dd")

        userViewModel.updateProfile(surnameInput, nameInput, formattedDate)
        userViewModel.updateResult.observe(viewLifecycleOwner){result->
            when (result) {
                is Resource.Success -> {
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    startActivity(intent)
                }
                is Resource.Loading ->{

                }
                is Resource.Error ->{
                    result.message?.let {
                        Toast.makeText(requireContext(),
                            "Не удалось изменить профиль",
                            Toast.LENGTH_SHORT).show()
                    }
                }
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