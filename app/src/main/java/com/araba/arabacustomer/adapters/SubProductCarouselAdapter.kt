package com.araba.arabacustomer.adapters

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.araba.arabacustomer.R
import com.araba.arabacustomer.models.CategoryModel
import com.azoft.carousellayoutmanager.CarouselLayoutManager
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener
import com.azoft.carousellayoutmanager.CenterScrollListener

/**
 * Created by rajneeshkumar on 11/13/17.
 */
class SubProductCarouselAdapter(var activity: Activity, var mList: ArrayList<CategoryModel>) : RecyclerView.Adapter<SubProductCarouselAdapter.SubProductListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SubProductListViewHolder(parent)


    override fun onBindViewHolder(holder: SubProductListViewHolder, position: Int) {
        holder.bind(mList.get(position))
    }

    override fun getItemCount() = mList.size

    inner class SubProductListViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(activity).inflate(R.layout.adapter_sub_product_list_carousel, parent, false)) {

        var title: TextView? = null
        var desc: TextView? = null
        fun bind(mCategoryModel: CategoryModel) = with(itemView) {

            title = findViewById(R.id.title)
            desc = findViewById(R.id.desc)

            title?.text = mCategoryModel.name
            desc?.text = mCategoryModel.description
        }
    }
}
