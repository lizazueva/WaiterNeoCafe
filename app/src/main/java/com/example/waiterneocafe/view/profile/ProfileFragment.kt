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
import androidx.navigation.fragment.findNavController
import com.example.clientneowaiter.R
import com.example.clientneowaiter.databinding.AlertDialogExitBinding
import com.example.clientneowaiter.databinding.FragmentProfileBinding
import com.example.waiterneocafe.view.login.LoginActivity

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
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
        binding.imageLogout.setOnClickListener {
            showDialogExit()
        }
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