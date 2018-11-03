package com.josh.billrssroom.screens.favorites

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.josh.billrssroom.R
import com.josh.billrssroom.model.FeedItem
import kotlinx.android.synthetic.main.include_item_row_fav.view.*
import java.util.*

class FavoritesAdapter(private val activity: Activity,
                       val favoriteClickCallback: FavoriteClickListener) :
    RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    private var favoritesList: List<FeedItem> = Collections.emptyList()
    private var expandedPosition = -1


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): FavoritesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return FavoritesViewHolder(layoutInflater.inflate(R.layout.item_row_favorites, parent, false))
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val items = favoritesList[position]

        holder.bindTo(items)

//        if (position == expandedPosition){
//            holder.groupButtons.visibility = View.VISIBLE
//        } else{
//            holder.groupButtons.visibility = View.GONE
//        }
    }

    fun setFavoritesList(favoriteItemsList: List<FeedItem>) {
        favoritesList = favoriteItemsList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return favoritesList.size
    }

    inner class FavoritesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val titleView = itemView.text_title
        val dateView = itemView.text_date
        val descriptionView = itemView.text_description
        val btnShare = itemView.btn_share
        val btnBrowser = itemView.btn_browser
        val btnTrash = itemView.btn_trash
        val groupButtons = itemView.group
        var favorite: FeedItem? = null

        fun bindTo(favorite: FeedItem){
            this.favorite = favorite
            titleView.text = favorite.title
            dateView.text = favorite.formattedDate
            descriptionView.text = favorite.formattedDescription

            btnTrash.setOnClickListener { favoriteClickCallback.onTrashBtnClick(favorite, adapterPosition) }
            btnBrowser.setOnClickListener { favoriteClickCallback.onBrowserBtnClick(favorite, adapterPosition) }
            btnShare.setOnClickListener { favoriteClickCallback.onShareBtnClick(favorite, adapterPosition) }

//            itemView.setOnClickListener {
//                if (expandedPosition >= 0){
//                    val prev = expandedPosition
//                    notifyItemChanged(prev)
//                }
//                expandedPosition = adapterPosition
//                notifyItemChanged(expandedPosition)
//            }
        }
    }
}