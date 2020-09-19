package com.mohamedsayed.theair.view.latest


import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mohamedsayed.theair.R
import com.mohamedsayed.theair.model.network.ApiResponse
import com.mohamedsayed.theair.model.network.Status
import com.mohamedsayed.theair.model.objects.GuestSession
import com.mohamedsayed.theair.model.objects.TvShow
import com.mohamedsayed.theair.model.objects.TvShowResults
import com.mohamedsayed.theair.view.MainActivity
import com.mohamedsayed.theair.view.details.TvShowDetailsFragment
import com.mohamedsayed.theair.view.latest.LatestAdapter
import com.mohamedsayed.theair.viewmodel.FavouriteVModel
import com.mohamedsayed.theair.viewmodel.LatestViewModel
import com.mohamedsayed.theair.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.layout_recycler.*
import kotlinx.android.synthetic.main.tv_show_details.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class LatestFragment : Fragment() {

    var TAG = "LatestFragment"

    val latestViewModel: LatestViewModel by viewModel()
    val mainViewModel: MainViewModel by inject()
    val favouriteVModel: FavouriteVModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_recycler, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getGuestSessionId()
    }

    var observerGuestSession: Observer<ApiResponse<GuestSession>>? = null
    private fun getGuestSessionId() {
        observerGuestSession = Observer<ApiResponse<GuestSession>> {
            when (it.status) {
                Status.LOADING -> {
                    Log.d(TAG, "Status.LOADING")
                    progressBar.visibility = View.VISIBLE
                    fabRefresh.visibility = View.GONE
                }
                Status.EMPTY -> {
                    Log.d(TAG, "Status.EMPTY")
                    progressBar.visibility = View.GONE
                }
                Status.SUCCESS -> {
                    Log.d(TAG, "Status.SUCCESS")
                    progressBar.visibility = View.GONE
                    getLatestTvShows()
                }
                Status.ERROR -> {
                    Log.d(TAG, "Status.ERROR")
                    progressBar.visibility = View.GONE
                    fabRefresh.visibility = View.VISIBLE
                    fabRefresh.setOnClickListener { getGuestSessionId() }
                    mainViewModel.getGuestSessionId!!.removeObserver(observerGuestSession!!)
                }
            }
        }

        mainViewModel.getGuestSessionId().observe(viewLifecycleOwner, observerGuestSession!!)
    }

    var observer: Observer<ApiResponse<TvShowResults>>? = null
    private fun getLatestTvShows() {
        observer = Observer<ApiResponse<TvShowResults>> {
            when (it.status) {
                Status.LOADING -> {
                    Log.d(TAG, "Status.LOADING")
                    progressBar.visibility = View.VISIBLE
                    fabRefresh.visibility = View.GONE
                }
                Status.EMPTY -> {
                    Log.d(TAG, "Status.EMPTY")
                    progressBar.visibility = View.GONE
                    tvNoData.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    Log.d(TAG, "Status.SUCCESS")
                    progressBar.visibility = View.GONE
                    setupFavourite(it.data!!.results)
                }
                Status.ERROR -> {
                    Log.d(TAG, "Status.ERROR")
                    progressBar.visibility = View.GONE
                    fabRefresh.visibility = View.VISIBLE
                    fabRefresh.setOnClickListener { getLatestTvShows() }
                    latestViewModel.getLatestTvShows().removeObserver(observer!!)
                }
            }
        }

        latestViewModel.getLatestTvShows().observe(viewLifecycleOwner, observer!!)
    }

    var observerFavourite : Observer<Boolean?>? = null
    var observerUnFavourite: Observer<Boolean?> ? = null
    fun setupFavourite(list: List<TvShow>) {
        favouriteVModel.getFavouriteList()!!.observe(viewLifecycleOwner, {
            it?.let {
                if(it.isEmpty())  setupList(list)
                else {
                    it.forEach {
                        for (i in list.indices) {
                            if (list[i].id == it.id) {
                                list[i].isFavourite = true
                                break
                            }
                        }
                        setupList(list)
                    }
                }
            } ?: run {
                setupList(list)
            }
        })

        observerFavourite = Observer {
            it?.let {
                Log.d(TAG,"insertFavourite : $it")
                Toast.makeText(mContext!!,getString(R.string.favourite_success), Toast.LENGTH_LONG).show()
                favouriteVModel.insertFavourite.removeObserver(observerFavourite!!)
            }
        }

        observerUnFavourite = Observer {
            it?.let {
                Log.d(TAG,"deleteFavourite : $it")
                Toast.makeText(mContext!!,getString(R.string.unfavourite_success), Toast.LENGTH_LONG).show()
                favouriteVModel.deleteFavourite.removeObserver(observerUnFavourite!!)
            }
        }

    }

    private fun setupList(list: List<TvShow>) {
        recyclerView.layoutManager = GridLayoutManager(mContext, 2)
        recyclerView.adapter = LatestAdapter(mContext!!, list,
            { item ->
                val args = Bundle()
                args.putParcelable(TvShowDetailsFragment.TV_SHOW, item)
                findNavController().navigate(
                    R.id.action_latestFragment_to_tvShowDetailsFragment,
                    args
                )
            }, { item, b ->
                if(b){
                    favouriteVModel.insertFavourite(item!!)
                    favouriteVModel.insertFavourite.observe(viewLifecycleOwner, observerFavourite!!)
                }else{
                    favouriteVModel.deleteFavourite(item!!)
                    favouriteVModel.deleteFavourite.observe(viewLifecycleOwner, observerUnFavourite!!)
                }
            })
        recyclerView.visibility = View.VISIBLE
    }

    var mContext: MainActivity? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context as MainActivity
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        mContext = activity as MainActivity
    }
}
