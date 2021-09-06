package com.timespace.notesapp.ui.home.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.timespace.notesapp.R
import com.timespace.notesapp.base.BaseFragment
import com.timespace.notesapp.databinding.FragmentHomeBinding
import com.timespace.notesapp.firebasemodel.DailyEvents
import com.timespace.notesapp.firebasemodel.Events
import com.timespace.notesapp.ui.home.fragment.home.adapter.EventAdapter
import com.timespace.notesapp.ui.home.viewmodel.NotesViewModel
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : BaseFragment(), EventAdapter.AdaptorAction {
    private lateinit var binding: FragmentHomeBinding
    private var eventList: ArrayList<DailyEvents> = ArrayList()

    companion object {
        private var instance: HomeFragment? = null
        private var viewModel: NotesViewModel? = null

        @JvmStatic
        fun newInstance(viewModelm: NotesViewModel): HomeFragment? {
            viewModel = viewModelm
            if (instance == null) {
                instance = HomeFragment()
            }
            return instance
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(requireContext()),
            R.layout.fragment_home,
            container,
            false
        )
        WorkStation()
        return binding.root
    }

    override fun WorkStation() {
        //setViewTextColor(binding.wishMe)
        setIconTintColor(binding.ivIcon)
        val c = Calendar.getInstance()
        val sdf = SimpleDateFormat("EEEE")
        val d = Date()
        val dayOfTheWeek = sdf.format(d)
        val timeOfDay = c[Calendar.HOUR_OF_DAY]
        val month_date = SimpleDateFormat("MMMM")
        val month_day = SimpleDateFormat("dd")
        val month_name = month_date.format(c.getTime())
        val monthDay = month_day.format(c.getTime())
        binding.date.setText("It's $dayOfTheWeek" + "\n" + "$monthDay , $month_name")

        if (timeOfDay >= 0 && timeOfDay < 12) {
            binding.wishMe.setText(getString(R.string.goodmorning))
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            binding.wishMe.setText(getString(R.string.good_afternoon))
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            binding.wishMe.setText(getString(R.string.good_evening))
        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            binding.wishMe.setText(getString(R.string.good_night))
        }
        binding.name.setText(viewModel!!.sharedPre.name)



        lifecycleScope.launchWhenCreated {

            val data = firestore.collection("event").whereEqualTo("Start Date"/*"start_date"*/,
            viewModel!!.methods.ConverMillsTo(System.currentTimeMillis(), "dd-MM-yyyy")).get().await()

            eventList = ArrayList()

            if (data != null) {
                for (item in data.documents) {
                    val events = firestore.collection("event").document(item.id).get().await().toObject(Events::class.java)
                    if (events != null) {
                        val dailyEvents = DailyEvents(events.start_time, events.subject_title)
                        eventList.add(dailyEvents);
                        val eventAdapter = EventAdapter(requireContext(), eventList, this@HomeFragment);
                        binding.eventRecycler.adapter = eventAdapter
                    } else {
                        showCustomAlert("No Events Found", binding.root)
                    }
                }
            }
        }


    }

    override fun OnClickListener(value: List<TextView>?) {
        setViewTextColor(value?.get(0)!!)
    }
}