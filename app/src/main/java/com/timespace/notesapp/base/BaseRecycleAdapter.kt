package com.timespace.notesapp.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.timespace.notesapp.base.BaseRecycleAdapter.BaseViewHolder
import java.util.*

abstract class BaseRecycleAdapter : RecyclerView.Adapter<BaseViewHolder>() {
    protected var isForDesign = false
    var layoutInflater: LayoutInflater? = null
    var context: Context? = null
    var recyclerView: RecyclerView? = null
    fun inflateLayout(layoutId: Int): View {
        return layoutInflater!!.inflate(layoutId, null)
    }

    override fun getItemViewType(position: Int): Int {
        return getViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        recyclerView = parent as RecyclerView
        context = parent.getContext()
        layoutInflater = LayoutInflater.from(parent.getContext())
         if (viewType == 0) {
             return viewHolder!!
        } else
            return  getViewHolder(parent, viewType)!!
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder!!.setData(position)
    }

    override fun getItemCount(): Int {
        return if (isForDesign) 5 else dataCount
    }

    abstract val viewHolder: BaseViewHolder?
    fun getViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder? {
        return null
    }

    fun getViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    abstract val dataCount: Int

    abstract inner class BaseViewHolder(itemView: View?) : RecyclerView.ViewHolder(
        itemView!!
    ){
        abstract fun setData(position: Int)
        fun isValidString(data: String?): Boolean {
            return data != null && !data.trim { it <= ' ' }.isEmpty()
        }

        fun isValidObject(`object`: Any?): Boolean {
            return `object` != null
        }

        fun getValidDecimalFormat(value: String): String {
            if (!isValidString(value)) {
                return "0.00"
            }
            val netValue = value.toDouble()
            return getValidDecimalFormat(netValue)
        }

        fun getValidDecimalFormat(value: Double): String {
            return String.format(Locale.ENGLISH, "%.2f", value)
        }

        fun updateViewVisibitity(myView: View?, visibility: Int) {
            if (myView == null) return
            if (myView.visibility != visibility) {
                myView.visibility = visibility
            }
        }
    }

    companion object {
        const val VIEW_TYPE_LOAD_MORE = 404
        const val VIEW_TYPE_DATA = 1
    }
}