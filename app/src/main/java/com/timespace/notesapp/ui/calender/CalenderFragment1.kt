package com.timespace.notesapp.ui.calender

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.timespace.notesapp.R
import com.timespace.notesapp.base.BaseFragment
import com.timespace.notesapp.databinding.FragmentCalender1Binding
import com.timespace.notesapp.firebasemodel.DailyEvents
import com.timespace.notesapp.firebasemodel.Events
import com.timespace.notesapp.ui.calender.adapter.CalenderAdapters
import com.timespace.notesapp.ui.calender.adapter.CalenderEventAdapter
import com.timespace.notesapp.ui.home.viewmodel.NotesViewModel
import com.timespace.notesapp.ui.setting.SettingFragment
import kotlinx.coroutines.tasks.await


class CalenderFragment1 : BaseFragment() , CalenderAdapters.AdaptorAction{


    private lateinit var binding: FragmentCalender1Binding
    private var eventList: ArrayList<DailyEvents> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_calender1,
            container,
            false
        )

        WorkStation()
        return binding.root
    }
    override fun WorkStation() {
        val adapter = CalenderAdapters(requireActivity(),this@CalenderFragment1)
        binding.rvDate.adapter = adapter

        binding.ivPageChange.setOnClickListener {
            startFragment(CalenderFragment.newInstance(viewModel!!), CalenderFragment.toString(),true)
        }
    }

    companion object {
        private var instance: CalenderFragment1? = null
        var viewModel: NotesViewModel? = null
        @JvmStatic
        fun newInstance(viewModelm: NotesViewModel): CalenderFragment1? {
            this.viewModel=viewModelm
            if (instance == null) {
                instance = CalenderFragment1()
            }
            return instance
        }

    }

    override fun OnClickListener(value: List<LinearLayout?>?) {
        if (value != null) {
            value.get(0)!!.setOnClickListener {
                lifecycleScope.launchWhenCreated {
                    val data = firestore.collection("event")
                        .whereEqualTo(
                        "start_date",
                        viewModel!!.methods.ConverMillsTo(System.currentTimeMillis(), "dd-MM-yyyy")
                    )
                        .get().await()
                    eventList=ArrayList()
                    if (data != null) {
                        for ( item in  data.documents) {
                            val events=  firestore.collection("event").document(item.id).get().await().toObject(
                                Events::class.java)
                            if(events!=null){
                                val dailyEvents =  DailyEvents(events.start_time,events.subject_title)
                                eventList.add(dailyEvents);
                                val eventAdapter = CalenderEventAdapter (requireContext(), eventList)
                                binding.rvDate.adapter=eventAdapter
                            }else{
                                showCustomAlert("No Events Found",binding.root)
                            }
                        }
                    }
                }
            }
        }
    }

}