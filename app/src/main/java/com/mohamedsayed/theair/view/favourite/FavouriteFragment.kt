package com.mohamedsayed.theair.view.favourite


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

class FavouriteFragment : Fragment() {

    var TAG = "LatestFragment"

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

        setupFavourite()
    }

    var observerFavourites : Observer<List<TvShow>?>? = null
    var observerUnFavourite: Observer<Boolean?> ? = null
    fun setupFavourite() {
        observerFavourites = Observer {
            it?.let {
                if(it.isEmpty()) tvNoData.visibility = View.VISIBLE
                else {
                    setupList(it)
                }
                favouriteVModel.getFavouriteList()!!.removeObserver(observerFavourites!!)
            }?:run{
                tvNoData.visibility = View.VISIBLE
            }
        }

        observerUnFavourite = Observer {
            it?.let {
                Log.d(TAG,"deleteFavourite : $it")
                Toast.makeText(mContext!!,getString(R.string.unfavourite_success), Toast.LENGTH_LONG).show()
                favouriteVModel.getFavouriteList()!!.observe(viewLifecycleOwner, observerFavourites!!)
                favouriteVModel.deleteFavourite.removeObserver(observerUnFavourite!!)
            }
        }

        favouriteVModel.getFavouriteList()!!.observe(viewLifecycleOwner, observerFavourites!!)
    }

    private fun setupList(list: List<TvShow>) {
        list.forEach {
            it.isFavourite = true
        }
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
                if(!b){
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
