package com.mohamedsayed.theair.view.latest

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.mohamedsayed.theair.R
import com.mohamedsayed.theair.helpers.Q
import com.mohamedsayed.theair.model.objects.TvShow
import kotlinx.android.synthetic.main.tv_shows_item_list.view.*

class LatestAdapter(val context: Context,
                    val list: List<TvShow>,val onItemClick:(TvShow)->Unit) : RecyclerView.Adapter<LatestAdapter.LatestAdapterHolder>() {


    inner class LatestAdapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvShowTxtTitle = itemView.tvShowTxtTitle
        val tvShowTxtDate = itemView.tvShowTxtDate
        val tvShowTxtRate = itemView.tvShowTxtRate
        val tvShowImPoster = itemView.tvShowImPoster
        val tvShowPBarImage = itemView.tvShowPBarImage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LatestAdapterHolder {
        return LatestAdapterHolder(LayoutInflater.from(parent.context).inflate(R.layout.tv_shows_item_list, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: LatestAdapterHolder, position: Int) {


        val item = list[position]

        holder.tvShowTxtTitle.text = item.name
        holder.tvShowTxtDate.text = item.first_air_date
        holder.tvShowTxtRate.text = item.vote_average.toString()

        Glide.with(context).applyDefaultRequestOptions(
            RequestOptions()
            .error(R.drawable.ic_refresh))
            .load(String.format(Q.POSTER_URL_PATH,item.poster_path))
            .fitCenter()
            .addListener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable?>?, isFirstResource: Boolean): Boolean {
                    e?.printStackTrace()
                    holder.tvShowPBarImage.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable?>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    holder.tvShowPBarImage.visibility = View.GONE
                    return false
                }
            })
            .into(holder.tvShowImPoster)

        holder.itemView.setOnClickListener {
            onItemClick.invoke(item)
        }

    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}