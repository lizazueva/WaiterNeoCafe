package com.neobis.waiterneocafe.di

import com.neobis.waiterneocafe.api.Repository
import com.neobis.waiterneocafe.api.RetrofitInstance
import com.neobis.waiterneocafe.viewModel.CodeViewModel
import com.neobis.waiterneocafe.viewModel.LoginViewModel
import com.neobis.waiterneocafe.viewModel.MenuViewModel
import com.neobis.waiterneocafe.viewModel.NewOrderViewModel
import com.neobis.waiterneocafe.viewModel.NotificationsViewModel
import com.neobis.waiterneocafe.viewModel.OrdersViewModel
import com.neobis.waiterneocafe.viewModel.UserViewModel
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