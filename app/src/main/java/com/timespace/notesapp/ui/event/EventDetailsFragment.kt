package com.timespace.notesapp.ui.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.timespace.notesapp.R
import com.timespace.notesapp.adapter.EventItemAdapter
import com.timespace.notesapp.base.BaseFragment
import com.timespace.notesapp.databinding.FragmentEventDetailBinding
import com.timespace.notesapp.firebasemodel.Description
import com.timespace.notesapp.firebasemodel.Events
import com.timespace.notesapp.ui.calender.CalenderFragment
import com.timespace.notesapp.ui.home.fragment.home.HomeFragment
import com.timespace.notesapp.ui.home.viewmodel.NotesViewModel
import com.timespace.notesapp.ui.meeting.CreateMeetingFragment
import kotlin.collections.ArrayList

class EventDetailsFragment: BaseFragment() {

    private lateinit var binding: FragmentEventDetailBinding

    var rv_description: RecyclerView? = null

    private var firebaseFirestore = FirebaseFirestore.getInstance()
    private var firebaseAuth: FirebaseAuth=FirebaseAuth.getInstance()


    var selectedDate = ""
    var eventDocId:String? = ""
//    var day:String? = ""
//    var date:String? = ""
//    var month:String? = ""

    private var eventItemAdapter: EventItemAdapter? = null
    var descriptionList: ArrayList<Description> = ArrayList<Description>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_event_detail,
            container,
            false
        )

        WorkStation()
        return binding.root
    }

    override fun WorkStation() {
        AccessClickLIstener()
    }

    private fun AccessClickLIstener() {
        binding.ivCross.setOnClickListener {
            startFragment(
                CalenderFragment.newInstance(viewModel!!),
                CalenderFragment.toString(),true)
        }
        binding.tvDayDate.setText(dayofweek)
        binding.tvMonth.setText(month)
        binding.tvDate.setText(date)


        // get data from firebase
        firebaseFirestore!!.collection("event").whereEqualTo("start_date", date)
            .get()
            .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
                val documentExists: Boolean
                if (task.isSuccessful) {
                    documentExists = !task.result.isEmpty
                    for (document in task.result) {
                        eventDocId = document.id
                        //new introduce
                        firebaseFirestore!!.collection("event").document(eventDocId!!)
                            .get()
                            .addOnCompleteListener(OnCompleteListener<DocumentSnapshot> { task ->
                                val events: Events? = task.result.toObject(Events::class.java)
                                //   String one = events.getDescriptionEvent().getDescription4();
                                binding.tvDailyPanchanga.text = events!!.subject_title
                                binding.tvTithiTag.text = events.descriptions!!.description1
                                binding.tvTithi.text = events.descriptions!!.description2
                                binding.tvNakshatraTag.text = events.descriptions!!.description3
                                binding.tvNakshatra.text = events.descriptions!!.description4
                                binding.tvKaranTag.text = events.descriptions!!.description5
                                binding.tvKaran.text = events.descriptions!!.description6
                                binding.tvPakshaTag.text = events.descriptions!!.description7
                                binding.tvPaksha.text = events.descriptions!!.description8
                                binding.tvTag.text = events.descriptions!!.description9
                                binding.tvDesc.text = events.descriptions!!.description10
                            })
                    }
                }
            })

    }

    companion object {
        private var instance: EventDetailsFragment? = null
        private var viewModel: NotesViewModel? = null
        private var date:String?=null
        private var dayofweek:String?=null
        private var day:String?=null
        private var month:String?=null

        @JvmStatic
        fun newInstance(viewModelm: NotesViewModel, datem:String, dayofweekm:String, daym:String, monthm:String): EventDetailsFragment? {
            this.viewModel = viewModelm
            this.date = datem
            this.dayofweek = dayofweekm
            this.day = daym
            this.month = monthm
            if (instance == null) {
                instance = EventDetailsFragment()
            }
            return instance
        }

    }
}