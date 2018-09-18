package com.josh.billrssroom.ui.favorites

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.josh.billrssroom.R
import com.josh.billrssroom.model.BillModel
import kotlinx.android.synthetic.main.item_row_favorites.view.*
import java.util.*

class MyFavoritesAdapter(private val activity: Activity,
                         val favoriteClickCallback: FavoriteClickListener) :
    RecyclerView.Adapter<MyFavoritesAdapter.AnotherViewHolder>() {

    private var favoritesList: List<BillModel> = Collections.emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): AnotherViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return AnotherViewHolder(layoutInflater.inflate(R.layout.item_row_favorites, parent, false))
    }

    override fun onBindViewHolder(holder: AnotherViewHolder, position: Int) {
        val items = favoritesList.get(position)
        // or? val billModel = favoritesList[position]
        // or? holder.bindTo(favoritesList[position])

        holder.bindTo(items)
    }

    fun setBillItemList(billItemsList: List<BillModel>) {
        favoritesList = billItemsList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return favoritesList.size
    }

    inner class AnotherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val titleView = itemView.text_title
        val dateView = itemView.text_pubdate
        val descriptionView = itemView.text_description
        val btnShare = itemView.btn_share
        val btnBrowser = itemView.btn_browser
        val btnTrash = itemView.btn_trash
        var favorite: BillModel? = null

        fun bindTo(favorite: BillModel){
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