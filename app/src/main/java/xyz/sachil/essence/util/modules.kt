package xyz.sachil.essence.util

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.component.KoinApiExtension
import org.koin.dsl.module
import xyz.sachil.essence.ExceptionHandler
import xyz.sachil.essence.model.cache.CacheDatabase
import xyz.sachil.essence.model.net.NetClient
import xyz.sachil.essence.repository.TypeDataRepository
import xyz.sachil.essence.repository.TypeRepository
import xyz.sachil.essence.vm.SharedViewModel
import xyz.sachil.essence.vm.TypeViewModel

@OptIn(KoinApiExtension::class)
val appModules = module {
    viewModel { TypeViewModel() }
}

@OptIn(KoinApiExtension::class)
val frameworkModules = module {
    single { CacheDatabase.newInstance(get()) }
    single { NetClient.newInstance() }
    single { TypeRepository() }
    factory{ TypeDataRepository() }
    single { ExceptionHandler() }
}