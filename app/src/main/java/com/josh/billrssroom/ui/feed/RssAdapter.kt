package com.josh.billrssroom.ui.feed

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.josh.billrssroom.R
import com.josh.billrssroom.model.BillModel
import kotlinx.android.synthetic.main.item_row_rss.view.*
import java.util.*

class RssAdapter(val rowClickCallback: BillItemClickListener) :
    RecyclerView.Adapter<RssAdapter.RssViewHolder>() {

    private var items: List<BillModel> = Collections.emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RssViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RssViewHolder(layoutInflater.inflate(R.layout.item_row_rss, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RssViewHolder, position: Int) {
        val item = items.get(position)

        holder.bindTo(item)
    }

    fun setRssList(items: List<BillModel>){
        this.items = items
    }

    inner class RssViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val titleView = itemView.bill_title
        val dateView = itemView.bill_pub_date
        val descriptionView = itemView.bill_description
        val btnShare = itemView.btn_share
        val btnBrowser = itemView.btn_browser
        val btnSave = itemView.btn_save
        var model: BillModel? = null

        fun bindTo(model: BillModel) {
            this.model = model

            titleView.text = model.title
            dateView.text = model.formattedDate
            descriptionView.text = model.formattedDescription

            btnBrowser.setOnClickListener { rowClickCallback.onBrowserClicked(model) }
            btnShare.setOnClickListener { rowClickCallback.onShareClicked(model) }
            btnSave.setOnClickListener { rowClickCallback.onSaveClicked(model, adapterPosition) }
        }
    }
}