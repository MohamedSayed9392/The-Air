package com.mohamedsayed.theair.koin

import androidx.room.Room
import com.mohamedsayed.theair.model.database.AppDatabase
import com.mohamedsayed.theair.model.network.RetrofitService
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

val dbModule:Module = module {
    single { Room.databaseBuilder(androidContext(), AppDatabase::class.java, "tmdb")
        .fallbackToDestructiveMigration().build() }
    single { get<AppDatabase>().favouriteDao() }
}