package com.example.waiterneocafe.view.notifications

import android.graphics.Canvas
import android.os.Bundle
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
import com.example.clientneowaiter.R
import com.example.clientneowaiter.databinding.FragmentNotificationsBinding
import com.example.waiterneocafe.adapters.AdapterNotifications
import com.example.waiterneocafe.model.notifications.Notifications
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator

class NotificationsFragment : Fragment() {

    private lateinit var binding: FragmentNotificationsBinding
    private lateinit var adapterNotifications: AdapterNotifications
    lateinit var testNot: ArrayList<Notifications>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpAdapter()
        setUpSwipeCallback()
        setUpListeners()

    }
    private fun setUpListeners() {
        binding.imageBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.textClearAll.setOnClickListener {
            adapterNotifications.deleteAllItems()
            binding.textNoNotifications.isVisible = true
            binding.textClearAll.isVisible = false

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
                        Toast.makeText(requireContext(), "Уведомление удалено", Toast.LENGTH_LONG).show()
                        adapterNotifications.removeItem(position)
                    }
                }
            }
        }

        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerNotifications)

    }

    private fun setUpAdapter() {
        adapterNotifications = AdapterNotifications()
        binding.recyclerNotifications.adapter = adapterNotifications
        binding.recyclerNotifications.layoutManager = LinearLayoutManager(requireContext())
        testNot = arrayListOf (
            Notifications(1,"Заказ готов", "13:15","Кофейный напиток, 2 бургера, 3 тирамису, 2 тарталетки"),
            Notifications(2,"Вы закрыли счет", "13:16", "Кофейный напиток"),
            Notifications(3,"Ваш заказ оформлен", "13:15", "Кофейный напиток"),
            Notifications(4,"Заказ готов", "17:15", "Кофейный напиток")
        )
        adapterNotifications.differ.submitList(testNot)
    }
}