package com.neobis.waiterneocafe.view.orders

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.neobis.clientneowaiter.R
import com.neobis.clientneowaiter.databinding.FragmentOrdersBinding
import com.neobis.waiterneocafe.adapters.SliderAdapterOrders
import com.neobis.waiterneocafe.model.notifications.NotificationsResponse
import com.neobis.waiterneocafe.model.user.ClientId
import com.neobis.waiterneocafe.utils.NotificationsWebSocket
import com.neobis.waiterneocafe.utils.Resource
import com.neobis.waiterneocafe.viewModel.NotificationsViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrdersFragment : Fragment() {

    private lateinit var binding: FragmentOrdersBinding
    private lateinit var webSocket: NotificationsWebSocket
    private val notificationsViewModel: NotificationsViewModel by viewModel()
    private var idClient = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpListeners()
        viewPager()
        setWebSocket()
    }

    private fun setWebSocket() {
        notificationsViewModel.getIdClient()
        notificationsViewModel.idClient.observe(viewLifecycleOwner){id->
            when(id){
                is Resource.Success -> {
                    id.data?.let{id ->
                        webSocket(id)
                        idClient = id.id
                    }
                }

                is Resource.Error -> {
                    id.message?.let {
                        Toast.makeText(
                            requireContext(),
                            "Не удалось получить данные",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                is Resource.Loading -> {
                }
            }
        }
    }
    private fun webSocket(id: ClientId) {
        webSocket = NotificationsWebSocket(object : NotificationsWebSocket.NotificationsWebSocketListener {
            override fun onMessage(message: String) {

                activity?.runOnUiThread {
                    try {

                        val newNotifications = parseWebSocketMessage(message)
                        if (newNotifications.isNotEmpty()){
                            binding.imageNotification.setImageResource(R.drawable.icn_bell_active)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onClose() {
                // Handle WebSocket close event
            }

            override fun onFailure(error: String) {
                // Handle WebSocket failure
            }
        }, id.id)

        webSocket.startWebSocket()

    }
    private fun parseWebSocketMessage(message: String): List<NotificationsResponse.Notifications> {
        try {
            val responseType = object : TypeToken<NotificationsResponse>() {}.type
            val response = Gson().fromJson<NotificationsResponse>(message, responseType)
            return response.notifications ?: emptyList()
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
            // Логирование содержания сообщения для дальнейшего анализа
            Log.e("NotificationsFragment", "Invalid JSON message: $message")
            return emptyList()
        }
    }


    private fun setUpListeners() {
        binding.imageNotification.setOnClickListener {
            findNavController().navigate(R.id.action_ordersFragment_to_notificationsFragment)
        }
        binding.imageProfile.setOnClickListener {
            findNavController().navigate(R.id.action_ordersFragment_to_profileFragment)
        }
    }

    private fun viewPager() {
        val tabLayout = binding.tabLayout
        val viewPager2 = binding.pager
        val tabArray = arrayOf(
            "Столы",
            "Заказы"
        )
        val adapter = SliderAdapterOrders(childFragmentManager, lifecycle)
        viewPager2.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = tabArray[position]
        }.attach()

        tabLayout.getTabAt(0)?.select()

        for (i in 0 until tabArray.size) {
            val tab = tabLayout.getTabAt(i)
            when (i) {
                    0 -> tab?.view?.background = ContextCompat.getDrawable(requireContext(), R.drawable.backgr_tab_select_orders)
                else -> tab?.view?.background = ContextCompat.getDrawable(requireContext(), R.drawable.backgr_tab_select_table)
            }
            if (i != 0) {
                tab?.view?.background = null
            }
        }

//
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    for (i in 0 until tabLayout.tabCount) {
                        val currentTab = tabLayout.getTabAt(i)
                        if (i == it.position) {
                            currentTab?.view?.background = ContextCompat.getDrawable(
                                requireContext(),
                                when (i) {
                                    0 -> R.drawable.backgr_tab_select_orders
                                    else -> R.drawable.backgr_tab_select_table
                                }
                            )
                        } else {
                            currentTab?.view?.background = ColorDrawable(Color.TRANSPARENT)
                        }
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.view?.background = ColorDrawable(Color.TRANSPARENT)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

}