package com.mohamedsayed.theair.koin

import com.mohamedsayed.theair.model.network.RetrofitService
import org.koin.core.module.Module
import org.koin.dsl.module

val appModule:Module = module {
    single { RetrofitService.apiService }
}