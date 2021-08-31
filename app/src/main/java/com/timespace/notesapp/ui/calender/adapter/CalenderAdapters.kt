package com.timespace.notesapp.ui.calender.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.timespace.notesapp.R
import com.timespace.notesapp.databinding.CalenderEventListBinding
import com.timespace.notesapp.firebasemodel.DailyEvents
import com.timespace.notesapp.firebasemodel.Events
import com.timespace.notesapp.ui.home.fragment.home.adapter.EventAdapter
import kotlinx.coroutines.tasks.await
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CalenderAdapters(val context: Context,val action:AdaptorAction): RecyclerView.Adapter<CalenderAdapters.CalenderViewHolder>() {

    //private val action: AdaptorAction? = null
    var ViewList: ArrayList<LinearLayout> = ArrayList()

    inner class CalenderViewHolder(val binding: CalenderEventListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(position: Int) {
            val c = Calendar.getInstance()
            val month_day = SimpleDateFormat("dd")
            val currentMonth = SimpleDateFormat("MMMM")
            val currentYear = SimpleDateFormat("yyyy")
            val monthDay = month_day.format(c.getTime())
            val monthCurrent = currentMonth.format(c.getTime())
            val yearCurrent = currentYear.format(c.getTime())
            binding.tvEventTime.setText(getDay(position))
            binding.tvDay.setText(getWeek(position))
            if(monthDay.equals( getDay(position).toString()) && monthCurrent.equals( getWeek(position).toString())&& yearCurrent.equals( getYear(position).toString())){
                binding.tvEventTime.setTextColor(context.getColor(R.color.pink))
                binding.root.alpha=1f
            }else{
                binding.root.alpha=0.5f
                binding.tvEventTime.setTextColor(context.getColor(R.color.black))
            }
            binding.tvMonth.setText(getMonth(position))

            ViewList.add(position, binding.llCalander)
            action!!.OnClickListener(ViewList)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalenderViewHolder {
        val binding = DataBindingUtil.inflate<CalenderEventListBinding>(
            LayoutInflater.from(context),
            R.layout.calender_event_list,
            parent,
            false
        )
        return CalenderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CalenderViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int {
        return 365
    }

    interface AdaptorAction {
        fun OnClickListener(value: List<LinearLayout?>?)
    }

    fun getDay(minusDay: Int): String? {
        val dateFormat: DateFormat = SimpleDateFormat("dd")
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, minusDay)
        val newDate: Date = calendar.getTime()
        val date: String = dateFormat.format(newDate)
        return date
    }

    fun getMonth(minusDay: Int): String? {
        val dateFormat: DateFormat = SimpleDateFormat("EEEE")
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, minusDay)
        val newDate: Date = calendar.getTime()
        val date: String = dateFormat.format(newDate)
        return date
    }

    fun getWeek(minusDay: Int): String? {
        val dateFormat: DateFormat = SimpleDateFormat("MMMM")
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, minusDay)
        val newDate: Date = calendar.getTime()
        val date: String = dateFormat.format(newDate)
        return date
    }

    fun getYear(minusDay: Int): String? {
        val dateFormat: DateFormat = SimpleDateFormat("yyyy")
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, minusDay)
        val newDate: Date = calendar.getTime()
        val date: String = dateFormat.format(newDate)
        return date
    }


}