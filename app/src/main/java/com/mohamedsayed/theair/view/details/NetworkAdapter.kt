package com.mohamedsayed.theair.view.details

import CreatedBy
import Networks
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
import kotlinx.android.synthetic.main.networks_item_list.view.*

class NetworkAdapter(val context: Context,
                     val list: List<Networks>) : RecyclerView.Adapter<NetworkAdapter.NetworkAdapterHolder>() {


    inner class NetworkAdapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView = itemView.textView
        val imageView = itemView.imageView
        val pBarImage = itemView.pBarImage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NetworkAdapterHolder {
        return NetworkAdapterHolder(LayoutInflater.from(parent.context).inflate(R.layout.networks_item_list, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: NetworkAdapterHolder, position: Int) {


        val item = list[position]

        holder.textView.text = item.name

        Glide.with(context).applyDefaultRequestOptions(
            RequestOptions()
            .error(R.drawable.ic_refresh))
            .load(String.format(Q.POSTER_URL_PATH,item.logo_path))
            .fitCenter()
            .addListener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable?>?, isFirstResource: Boolean): Boolean {
                    e?.printStackTrace()
                    holder.pBarImage.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable?>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    holder.pBarImage.visibility = View.GONE
                    return false
                }
            })
            .into(holder.imageView)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}