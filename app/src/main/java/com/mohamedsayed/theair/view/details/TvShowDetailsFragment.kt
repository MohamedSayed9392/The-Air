package com.mohamedsayed.theair.view.details

import TvShowDetails
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.mohamedsayed.theair.R
import com.mohamedsayed.theair.model.network.ApiResponse
import com.mohamedsayed.theair.model.network.Status
import com.mohamedsayed.theair.view.MainActivity
import com.mohamedsayed.theair.viewmodel.TvShowDetailsVModel
import kotlinx.android.synthetic.main.tv_shows_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TvShowDetailsFragment : Fragment() {

    var TAG = "TvShowDetailsFragment"

    val tvShowDetailsVModel : TvShowDetailsVModel by viewModel()

    var tvShowId:Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tvShowId = requireArguments().getInt(TV_SHOW_ID)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.tv_shows_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getTvShowDetails()
    }

    var observer : Observer <ApiResponse<TvShowDetails>>? = null
    private fun getTvShowDetails(){
        observer = Observer <ApiResponse<TvShowDetails>>{
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
                }
                Status.ERROR ->{
                    Log.d(TAG,"Status.ERROR")
                    progressBar.visibility = View.GONE
                    fabRefresh.visibility = View.VISIBLE

                    tvShowDetailsVModel.getTvShowDetails(tvShowId).removeObserver(observer!!)
                }
            }
        }

        tvShowDetailsVModel.getTvShowDetails(tvShowId).observe(viewLifecycleOwner, observer!!)
    }

    companion object {
        const val TV_SHOW_ID = "TV_SHOW_ID"
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
