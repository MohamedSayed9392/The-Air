package com.mohamedsayed.theair.model.database.dao


import androidx.room.*
import com.mohamedsayed.theair.model.objects.TvShow
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface FavouriteDao {

    @Query("SELECT * FROM favourite")
    fun getFavouriteList(): Flowable<List<TvShow>>

    @Query("SELECT * FROM favourite WHERE id = :id LIMIT 1")
    fun getFavouriteItem(id: Int): Single<TvShow>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavourite(tvShow: TvShow): Completable

    @Delete
    fun deleteFavourite(tvShow: TvShow): Single<Int>
}