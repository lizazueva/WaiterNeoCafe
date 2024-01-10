package com.example.waiterneocafe.view.profile

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.clientneowaiter.R
import com.example.clientneowaiter.databinding.AlertDialogExitBinding
import com.example.clientneowaiter.databinding.FragmentProfileBinding
import com.example.waiterneocafe.model.user.Shedule
import com.example.waiterneocafe.utils.Resource
import com.example.waiterneocafe.view.login.LoginActivity
import com.example.waiterneocafe.viewModel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Locale

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private  val userViewModel: UserViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataProfile()
        dataShedule()
        setUpListeners()

    }

    private fun dataShedule() {
        userViewModel.getShedule()
        userViewModel.shedule.observe(viewLifecycleOwner){shedule->
            when (shedule) {
                is Resource.Success -> {
                    shedule.data?.let { workdays ->
                            updateScheduleUI(workdays)
                        }
                    }
                is Resource.Loading ->{

                }
                is Resource.Error ->{

                }
            }

        }
    }

    private fun updateScheduleUI(schedule: Shedule) {
        schedule.workdays.forEach { workday ->
            when (workday.workday) {
                1 -> {
                    dataImageAndTextShedule("Понедельник: смена" ,binding.textMonday, binding.imageMonday, workday.start_time, workday.end_time )
                }
                2 -> {
                    dataImageAndTextShedule("Вторник: смена" ,binding.textTuesday, binding.imageTuesday, workday.start_time, workday.end_time )
                }
                3 ->{
                    dataImageAndTextShedule("Среда: смена" ,binding.textWednesday, binding.imageWednesday, workday.start_time, workday.end_time )
                }
                4 ->{
                    dataImageAndTextShedule("Четверг: смена" ,binding.textThursday, binding.imageThursday, workday.start_time, workday.end_time )
                }
                5 ->{
                    dataImageAndTextShedule("Пятница: смена" ,binding.textFriday, binding.imageFriday, workday.start_time, workday.end_time )
                }
                6 ->{
                    dataImageAndTextShedule("Суббота: смена" ,binding.textSaturday, binding.imageSaturday, workday.start_time, workday.end_time )
                }
                7 ->{
                    dataImageAndTextShedule("Воскресенье: смена" ,binding.textSunday, binding.imageSunday, workday.start_time, workday.end_time )
                }
            }
        }
    }

    private fun dataImageAndTextShedule(
        text: String,
        textView: TextView,
        imageView: ImageView,
        startTime: String,
        endTime: String
    ){
        val inputFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val startTimeDate = inputFormat.parse(startTime)
        val endTimeDate = inputFormat.parse(endTime)
        val formattedStartTime = outputFormat.format(startTimeDate)
        val formattedEndTime = outputFormat.format(endTimeDate)
        textView.text = "$text c $formattedStartTime по $formattedEndTime"
        if(formattedStartTime >= "17:00"){
            imageView.setImageResource(R.drawable.img_moon)
        }else{
            imageView.setImageResource(R.drawable.img_sun)
        }

    }

    private fun setUpListeners() {
        binding.imageBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.imageLogout.setOnClickListener {
            showDialogExit()
        }
    }

    private fun dataProfile() {
        userViewModel.getProfile()
        userViewModel.user.observe(viewLifecycleOwner){user ->
            when (user) {
                is Resource.Success -> {
                    binding.textInputName.setText(user.data?.first_name)
                    binding.textInputSurname.setText(user.data?.last_name)
                    binding.textInputPhone.setText(user.data?.phone_number)

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

    private fun showDialogExit() {
        val dialogBinding = AlertDialogExitBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext())

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(dialogBinding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

        dialogBinding.buttonNo.setOnClickListener {
            dialog.dismiss()
        }
        dialogBinding.buttonYes.setOnClickListener {
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }
    }
}