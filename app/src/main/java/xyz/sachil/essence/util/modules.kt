package xyz.sachil.essence.util

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.component.KoinApiExtension
import org.koin.dsl.module
import xyz.sachil.essence.ExceptionHandler
import xyz.sachil.essence.model.cache.CacheDatabase
import xyz.sachil.essence.model.net.NetClient
import xyz.sachil.essence.repository.DataRepository
import xyz.sachil.essence.repository.DetailRepository
import xyz.sachil.essence.repository.TypeRepository
import xyz.sachil.essence.repository.WeeklyPopularRepository
import xyz.sachil.essence.vm.TypeViewModel
import xyz.sachil.essence.vm.WeeklyPopularViewModel

@OptIn(KoinApiExtension::class)
val modules = module {
    single { CacheDatabase.newInstance(get()) }
    single { NetClient.newInstance() }
    single { TypeRepository() }
    single { DataRepository() }
    single { WeeklyPopularRepository() }
    single { DetailRepository() }
    factory { ExceptionHandler() }
}