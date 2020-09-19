package com.mohamedsayed.theair.model.database


import androidx.room.Database
import androidx.room.RoomDatabase
import com.mohamedsayed.theair.model.database.dao.FavouriteDao
import com.mohamedsayed.theair.model.objects.TvShow

@Database(entities = [TvShow::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favouriteDao(): FavouriteDao
}
