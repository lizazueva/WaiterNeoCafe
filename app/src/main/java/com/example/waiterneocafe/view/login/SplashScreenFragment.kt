package com.example.waiterneocafe.view.login

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.clientneowaiter.R
import com.example.clientneowaiter.databinding.FragmentSplashScreenBinding

class SplashScreenFragment : Fragment() {
    private lateinit var binding: FragmentSplashScreenBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashScreenBinding.inflate(inflater, container, false)

        Handler(Looper.myLooper()!!).postDelayed({
            findNavController().navigate(R.id.action_splashScreenFragment_to_loginFragment)
        }, 5000)
        return binding.root
    }

}