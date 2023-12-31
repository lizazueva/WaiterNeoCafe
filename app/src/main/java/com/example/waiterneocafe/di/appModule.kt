package com.example.waiterneocafe.di

import com.example.waiterneocafe.api.Repository
import com.example.waiterneocafe.api.RetrofitInstance
import com.example.waiterneocafe.viewModel.CodeViewModel
import com.example.waiterneocafe.viewModel.LoginViewModel
import com.example.waiterneocafe.viewModel.MenuViewModel
import com.example.waiterneocafe.viewModel.NewOrderViewModel
import com.example.waiterneocafe.viewModel.NotificationsViewModel
import com.example.waiterneocafe.viewModel.OrdersViewModel
import com.example.waiterneocafe.viewModel.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { RetrofitInstance.api }
    factory { Repository(get()) }
}

val viewModules = module {
    viewModel { LoginViewModel (get())}
    viewModel { CodeViewModel (get())}
    viewModel { MenuViewModel (get())}
    viewModel { UserViewModel (get())}
    viewModel { NewOrderViewModel (get())}
    viewModel { OrdersViewModel (get())}
    viewModel { NotificationsViewModel(get())}



}

val homeScope = module {
    viewModel { MenuViewModel(get()) }
}