package com.timespace.notesapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.timespace.notesapp.R
import com.timespace.notesapp.databinding.ItemEventBinding
import com.timespace.notesapp.firebasemodel.Description

class EventItemAdapter  (var context: Context) : RecyclerView.Adapter<EventItemAdapter.EventsViewHolder>() {

    private var data = ArrayList<Description>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        var binding: ItemEventBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_event, parent, false)
        return EventsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        holder.binding.tvTithi.text=data.get(position).desc
        holder.binding.tvTithiTag.text=data.get(position).desc1
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(data: ArrayList<Description>) {
        this.data = data
        notifyDataSetChanged()
    }

    class EventsViewHolder(var binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root)

}