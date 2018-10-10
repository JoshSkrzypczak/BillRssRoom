package com.josh.billrssroom.ui.feed

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.josh.billrssroom.R
import com.josh.billrssroom.model.FeedItem
import kotlinx.android.synthetic.main.item_row_rss.view.*
import java.util.*

class RssAdapter(private val activity: Activity, val rowClickCallback: BillItemClickListener) :
    RecyclerView.Adapter<RssAdapter.RssViewHolder>() {

    private var items: List<FeedItem> = Collections.emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RssViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RssViewHolder(layoutInflater.inflate(R.layout.item_row_rss, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RssViewHolder, position: Int) {
        val item = items[position]

        holder.bindTo(item)
    }

    fun setRssList(items: List<FeedItem>) {
        this.items = items
        notifyDataSetChanged();
    }

    inner class RssViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val titleView = itemView.text_title
        val dateView = itemView.text_date
        val descriptionView = itemView.text_description
        val btnShare = itemView.btn_share
        val btnBrowser = itemView.btn_browser
        val btnSave = itemView.btn_save
        var model: FeedItem? = null


        fun bindTo(model: FeedItem) {
            this.model = model
            titleView.text = model.title
            dateView.text = model.formattedDate
            descriptionView.text = model.formattedDescription

            btnBrowser.setOnClickListener { rowClickCallback.onBrowserBtnClick(model) }
            btnShare.setOnClickListener { rowClickCallback.onShareBtnClick(model, adapterPosition) }
            btnSave.setOnClickListener { v -> rowClickCallback.onSaveBtnClick(v, model, adapterPosition) }
        }
    }
}