package com.josh.billrssroom.ui.favorites

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.josh.billrssroom.R
import com.josh.billrssroom.model.FeedItem
import kotlinx.android.synthetic.main.item_row_favorites.view.*
import java.util.*

class FavoritesAdapter(private val activity: Activity,
                       val favoriteClickCallback: FavoriteClickListener) :
    RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    private var favoritesList: List<FeedItem> = Collections.emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): FavoritesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return FavoritesViewHolder(layoutInflater.inflate(R.layout.item_row_favorites, parent, false))
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val items = favoritesList.get(position)
        // or? val billModel = favoritesList[position]
        // or? holder.bindTo(favoritesList[position])

        holder.bindTo(items)
    }

    fun setBillItemList(billFeedItemList: List<FeedItem>) {
        favoritesList = billFeedItemList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return favoritesList.size
    }

    inner class FavoritesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val titleView = itemView.text_title
        val dateView = itemView.text_pubdate
        val descriptionView = itemView.text_description
        val btnShare = itemView.btn_share
        val btnBrowser = itemView.btn_browser
        val btnTrash = itemView.btn_trash
        var favorite: FeedItem? = null

        fun bindTo(favorite: FeedItem){
            this.favorite = favorite
            titleView.text = favorite.title
            dateView.text = favorite.formattedDate
            descriptionView.text = favorite.formattedDescription

            btnTrash.setOnClickListener { favoriteClickCallback.onTrashClick(favorite, adapterPosition) }
            btnBrowser.setOnClickListener { favoriteClickCallback.onBrowserClick(favorite, adapterPosition) }
            btnShare.setOnClickListener { favoriteClickCallback.onShareClick(favorite, adapterPosition) }
        }
    }
}