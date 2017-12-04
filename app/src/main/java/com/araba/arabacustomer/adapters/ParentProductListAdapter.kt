package com.araba.arabacustomer.adapters

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.araba.arabacustomer.R
import com.araba.arabacustomer.models.CategoryModel
import com.azoft.carousellayoutmanager.CarouselLayoutManager
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener
import com.azoft.carousellayoutmanager.CenterScrollListener


/**
 * Created by rajneeshkumar on 11/8/17.
 */
class ParentProductListAdapter(var activity: Activity, var mList: ArrayList<CategoryModel>) : RecyclerView.Adapter<ParentProductListAdapter.ParentProductListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentProductListViewHolder {
        if (viewType == 0) {
            return ParentProductListViewHolder(parent, R.layout.adapter_parent_product_list)
        } else {
            return ParentProductListViewHolder(parent, R.layout.adapter_parent_product_list_ltr)
        }
    }

    override fun getItemViewType(position: Int): Int {
//        return super.getItemViewType(position)
        if (position % 2 == 0) {
            return 0
        } else {
            return 1
        }
    }

    override fun onBindViewHolder(holder: ParentProductListViewHolder, position: Int) {
        holder.bind(mList.get(position))
    }

    override fun getItemCount() = mList.size

    inner class ParentProductListViewHolder(parent: ViewGroup, layout: Int) : RecyclerView.ViewHolder(LayoutInflater.from(activity).inflate(layout, parent, false)) {
        var title: TextView? = null
        var desc: TextView? = null
        var indicate: ImageView? = null
        var headerLayout: LinearLayout? = null
        var subList: RecyclerView? = null

        fun bind(mCategoryModel: CategoryModel) = with(itemView) {
            title = findViewById(R.id.title)
            desc = findViewById(R.id.desc)
            indicate = findViewById(R.id.indicate)
            headerLayout = findViewById(R.id.headerLayout)
            subList = findViewById(R.id.subList)

            var layoutManager = CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL)
            layoutManager.setPostLayoutListener(CarouselZoomPostLayoutListener())
            subList?.layoutManager = layoutManager
            subList?.setHasFixedSize(false)
            subList?.setAdapter(SubProductCarouselAdapter(activity, mCategoryModel.sub_category))
            subList?.addOnScrollListener(CenterScrollListener())

            title?.text = mCategoryModel.name
            desc?.text = mCategoryModel.description


            if (mCategoryModel.isSelected) {
                subList?.visibility = View.VISIBLE
                indicate?.setBackgroundResource(R.drawable.up)
            } else {
                subList?.visibility = View.GONE
                indicate?.setBackgroundResource(R.drawable.down)
            }

            headerLayout?.setOnClickListener {
                if (mCategoryModel.sub_category != null && mCategoryModel.sub_category.size > 0) {
                    if (mCategoryModel.isSelected!!) {
                        mList?.get(adapterPosition).isSelected = false
                        notifyItemChanged(adapterPosition)
                    } else {
                        mList?.get(adapterPosition).isSelected = true
                        notifyItemChanged(adapterPosition)
                    }
                } else {
                    Toast.makeText(activity, activity.resources.getString(R.string.there_is_not_any_item), Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}