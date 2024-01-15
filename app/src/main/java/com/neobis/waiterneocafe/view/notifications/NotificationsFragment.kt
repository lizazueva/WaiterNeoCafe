package com.neobis.waiterneocafe.view.notifications

import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.neobis.clientneowaiter.R
import com.neobis.clientneowaiter.databinding.FragmentNotificationsBinding
import com.neobis.waiterneocafe.adapters.AdapterNotifications
import com.neobis.waiterneocafe.model.notifications.NotificationsResponse
import com.neobis.waiterneocafe.model.user.ClientId
import com.neobis.waiterneocafe.utils.NotificationsWebSocket
import com.neobis.waiterneocafe.utils.Resource
import com.neobis.waiterneocafe.viewModel.NotificationsViewModel
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationsFragment : Fragment() {

    private lateinit var binding: FragmentNotificationsBinding
    private lateinit var adapterNotifications: AdapterNotifications
    private lateinit var webSocket: NotificationsWebSocket
    private val notificationsViewModel: NotificationsViewModel by viewModel()
    private var idClient = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getIdClient()
        setUpSwipeCallback()
        setUpListeners()

    }

    private fun getIdClient() {
        notificationsViewModel.getIdClient()
        notificationsViewModel.idClient.observe(viewLifecycleOwner){id->
            when(id){
                is Resource.Success -> {
                    id.data?.let{id ->
                        setWebSocket(id)
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

    private fun setWebSocket(id: ClientId) {
        webSocket = NotificationsWebSocket(object : NotificationsWebSocket.NotificationsWebSocketListener {
            override fun onMessage(message: String) {

                activity?.runOnUiThread {
                    try {

//                            val newNotifications = Gson().fromJson(message, object : TypeToken<List<Notifications>>() {}.type)
                        val newNotifications = parseWebSocketMessage(message)
                        setUpAdapter(newNotifications)
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
        binding.imageBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.textClearAll.setOnClickListener {
            deleteAllNotifications()
        }

    }

    private fun deleteAllNotifications() {
        notificationsViewModel.deleteAllNotification(idClient)
        notificationsViewModel.resultAllDelete.observe(viewLifecycleOwner) {resultDelete ->
            when (resultDelete) {
                is Resource.Success -> {
                    Toast.makeText(requireContext(), "Уведомления удалены", Toast.LENGTH_LONG).show()
                    binding.textNoNotifications.isVisible = true
                    binding.textClearAll.isVisible = false
                    adapterNotifications.deleteAll()
                }

                is Resource.Error -> {
                    resultDelete.message?.let {
                        Toast.makeText(
                            requireContext(),
                            "Не удалось удалить уведомления",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                is Resource.Loading -> {
                }
            }
        }

    }

    private fun setUpSwipeCallback() {
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean, ) {
                RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(requireContext(),R.color.delete))
                    .addActionIcon(R.drawable.img_trash)
                    .create()
                    .decorate()

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition

                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        val notificationId = adapterNotifications.differ.currentList[position].id
                        deleteNotification(notificationId, position)
                    }
                }
            }
        }

        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerNotifications)
    }

    private fun deleteNotification(notificationId: Int, position: Int) {
        notificationsViewModel.deleteNotification(notificationId)
        notificationsViewModel.resultDelete.observe(viewLifecycleOwner) {resultDelete ->
            when (resultDelete) {
                is Resource.Success -> {
                    Toast.makeText(requireContext(), "Уведомление удалено", Toast.LENGTH_LONG).show()
                    adapterNotifications.removeItem(position)
                }

                is Resource.Error -> {
                    resultDelete.message?.let {
                        Toast.makeText(
                            requireContext(),
                            "Не удалось удалить уведомление",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                is Resource.Loading -> {
                }
            }
        }

    }

    private fun setUpAdapter(newNotifications: List<NotificationsResponse.Notifications>) {
        adapterNotifications = AdapterNotifications()
        binding.recyclerNotifications.adapter = adapterNotifications
        binding.recyclerNotifications.layoutManager = LinearLayoutManager(requireContext())

        adapterNotifications.differ.submitList(newNotifications)

    }
    override fun onDestroyView() {
        super.onDestroyView()
        webSocket.closeWebSocket()
    }
}