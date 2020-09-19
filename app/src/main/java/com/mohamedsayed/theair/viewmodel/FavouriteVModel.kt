package com.mohamedsayed.theair.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mohamedsayed.theair.model.database.dao.FavouriteDao
import com.mohamedsayed.theair.model.network.ApiResponse
import com.mohamedsayed.theair.model.network.ApiService
import com.mohamedsayed.theair.model.objects.TvShow
import io.reactivex.CompletableObserver
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class FavouriteVModel(val favouriteDao: FavouriteDao) : ViewModel() {

    var getFavouriteTvShow: LiveData<TvShow>? = null
    fun getFavouriteTvShow(tv_id: Int) : LiveData<TvShow>?{
        getFavouriteTvShow = LiveDataReactiveStreams.fromPublisher(favouriteDao.getFavouriteItem(tv_id).toFlowable())
        return getFavouriteTvShow!!
    }

    var getFavouriteList: LiveData<List<TvShow>>? = null
    fun getFavouriteList() : LiveData<List<TvShow>>?{
        getFavouriteList = LiveDataReactiveStreams.fromPublisher(favouriteDao.getFavouriteList())
        return getFavouriteList!!
    }

    var insertFavourite = MutableLiveData<Boolean?>()
    fun insertFavourite(tvShow: TvShow) {
        favouriteDao.insertFavourite(tvShow).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onComplete() {
                    insertFavourite.value = true
                    insertFavourite.value = null
                }

                override fun onError(e: Throwable) {
                    insertFavourite.value = false
                    insertFavourite.value = null
                }
            })
    }

    var deleteFavourite = MutableLiveData<Boolean?>()
    fun deleteFavourite(tvShow: TvShow) {
        favouriteDao.deleteFavourite(tvShow).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Int?> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onSuccess(t: Int) {
                    deleteFavourite.value = true
                    deleteFavourite.value = null
                }

                override fun onError(e: Throwable) {
                    deleteFavourite.value = false
                    deleteFavourite.value = null
                }
            })
    }
}