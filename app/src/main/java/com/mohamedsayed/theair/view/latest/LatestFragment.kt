package com.nahdetmisr.adwaa.view.dashboard.fragments

import TvShowResults
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mohamedsayed.theair.R
import com.mohamedsayed.theair.model.network.ApiResponse
import com.mohamedsayed.theair.model.network.Status
import com.mohamedsayed.theair.model.objects.TvShow
import com.mohamedsayed.theair.view.MainActivity
import com.mohamedsayed.theair.view.details.TvShowDetailsFragment
import com.mohamedsayed.theair.view.latest.LatestAdapter
import com.mohamedsayed.theair.viewmodel.LatestViewModel
import kotlinx.android.synthetic.main.layout_recycler.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LatestFragment : Fragment() {

    var TAG = "LatestFragment"

    val latestViewModel : LatestViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_recycler, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fabRefresh.setOnClickListener { getLatestTvShows() }

        getLatestTvShows()
    }

    var observer : Observer <ApiResponse<TvShowResults>>? = null
    private fun getLatestTvShows(){
        observer = Observer <ApiResponse<TvShowResults>>{
            when(it.status){
                Status.LOADING ->{
                    Log.d(TAG,"Status.LOADING")
                    progressBar.visibility = View.VISIBLE
                }
                Status.STOP ->{
                    Log.d(TAG,"Status.STOP")
                    progressBar.visibility = View.GONE
                }
                Status.EMPTY ->{
                    Log.d(TAG,"Status.EMPTY")
                    progressBar.visibility = View.GONE
                    tvNoData.visibility = View.VISIBLE
                }
                Status.SUCCESS ->{
                    Log.d(TAG,"Status.SUCCESS")
                    progressBar.visibility = View.GONE
                    setupList(it.data!!.results)
                }
                Status.ERROR ->{
                    Log.d(TAG,"Status.ERROR")
                    progressBar.visibility = View.GONE
                    fabRefresh.visibility = View.VISIBLE

                    latestViewModel.getLatestTvShows().removeObserver(observer!!)
                }
            }
        }

        latestViewModel.getLatestTvShows().observe(viewLifecycleOwner, observer!!)
    }

    private fun setupList(list: List<TvShow>){
        recyclerView.layoutManager = GridLayoutManager(mContext,2)
        recyclerView.adapter = LatestAdapter(mContext!!,list) { item ->
            val args = Bundle()
            args.putInt(TvShowDetailsFragment.TV_SHOW_ID, item.id)
            findNavController().navigate(R.id.action_latestFragment_to_tvShowDetailsFragment, args)
        }
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
