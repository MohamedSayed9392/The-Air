package com.nahdetmisr.adwaa.refator.koin

import com.mohamedsayed.theair.viewmodel.LatestViewModel
import com.mohamedsayed.theair.viewmodel.TvShowDetailsVModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModel = module {
    viewModel {
        LatestViewModel(get())
    }
    viewModel {
        TvShowDetailsVModel(get())
    }
}