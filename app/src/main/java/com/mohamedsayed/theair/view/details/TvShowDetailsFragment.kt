package com.mohamedsayed.theair.view.details

import CreatedBy
import Networks
import TvShowDetails
import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.mohamedsayed.theair.R
import com.mohamedsayed.theair.helpers.Q
import com.mohamedsayed.theair.model.network.ApiResponse
import com.mohamedsayed.theair.model.network.Status
import com.mohamedsayed.theair.model.objects.TvShow
import com.mohamedsayed.theair.view.MainActivity
import com.mohamedsayed.theair.view.latest.LatestAdapter
import com.mohamedsayed.theair.viewmodel.TvShowDetailsVModel
import kotlinx.android.synthetic.main.tv_show_details.*
import kotlinx.android.synthetic.main.tv_shows_item_list.*
import kotlinx.android.synthetic.main.layout_recycler.*
import kotlinx.android.synthetic.main.layout_recycler_2.*
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
        return inflater.inflate(R.layout.tv_show_details, container, false)
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
                    tvSdProgressBar.visibility = View.VISIBLE
                }
                Status.STOP ->{
                    Log.d(TAG,"Status.STOP")
                    tvSdProgressBar.visibility = View.GONE
                }
                Status.EMPTY ->{
                    Log.d(TAG,"Status.EMPTY")
                    tvSdProgressBar.visibility = View.GONE
                }
                Status.SUCCESS ->{
                    Log.d(TAG,"Status.SUCCESS")
                    tvSdProgressBar.visibility = View.GONE
                    tvSdScrollView.visibility = View.VISIBLE
                    updateUi(it.data!!)
                }
                Status.ERROR ->{
                    Log.d(TAG,"Status.ERROR")
                    tvSdProgressBar.visibility = View.GONE
                    tvShowDetailsVModel.getTvShowDetails(tvShowId).removeObserver(observer!!)
                }
            }
        }

        tvShowDetailsVModel.getTvShowDetails(tvShowId).observe(viewLifecycleOwner, observer!!)
    }

    fun updateUi(item: TvShowDetails){
        tvSdTxtTitle.text = item.name
        tvShowTxtRate.text = item.vote_average.toString()
        tvSdTxtEpsoide.text = item.number_of_episodes.toString()
        tvSdTxtOverview.text = item.overview
        tvSdTxtHomePage.text = item.homepage

        val genres = ArrayList<String>()
        item.genres.forEach {
            genres.add(it.name)
        }
        tvSdTxtGenre.text = genres.joinToString(", ")

        Glide.with(mContext!!).applyDefaultRequestOptions(
            RequestOptions()
                .error(R.drawable.ic_refresh))
            .load(String.format(Q.POSTER_URL_PATH,item.poster_path))
            .fitCenter()
            .addListener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable?>?, isFirstResource: Boolean): Boolean {
                    e?.printStackTrace()
                    tvShowPBarImage.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable?>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    tvShowPBarImage.visibility = View.GONE
                    return false
                }
            })
            .into(tvShowImPoster)

        Glide.with(mContext!!).applyDefaultRequestOptions(
            RequestOptions()
                .error(R.drawable.ic_refresh))
            .load(String.format(Q.POSTER_URL_PATH,item.backdrop_path))
            .fitCenter()
            .addListener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable?>?, isFirstResource: Boolean): Boolean {
                    e?.printStackTrace()
                    tvSdCovergressBar.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable?>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    tvSdCovergressBar.visibility = View.GONE
                    return false
                }
            })
            .into(tvSdImCover)

        setupNetworksList(item.networks)
        setupCastList(item.created_by)
    }

    private fun setupNetworksList(list: List<Networks>?){
        if(list == null || list.isEmpty()) tvNoData.visibility = View.VISIBLE
        else {
            recyclerView.layoutManager =
                LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false)
            recyclerView.adapter = NetworkAdapter(mContext!!, list)
            recyclerView.visibility = View.VISIBLE
        }
    }

    private fun setupCastList(list: List<CreatedBy>?){
        if(list == null || list.isEmpty()) tvNoData2.visibility = View.VISIBLE
        else {
            recyclerView2.layoutManager =
                LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false)
            recyclerView2.adapter = CastAdapter(mContext!!, list)
            recyclerView2.visibility = View.VISIBLE
        }
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
