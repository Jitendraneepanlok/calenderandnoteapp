package com.timespace.notesapp.ui.calender

import android.graphics.Typeface
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.common.net.HttpHeaders.RANGE
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.squareup.timessquare.CalendarPickerView.OnDateSelectedListener
import com.timespace.notesapp.R
import com.timespace.notesapp.base.BaseFragment
import com.timespace.notesapp.databinding.FragmentCalenderBinding
import com.timespace.notesapp.firebasemodel.Years
import com.timespace.notesapp.firebasemodel.YearsDocuments
import com.timespace.notesapp.ui.event.EventDetailsFragment
import com.timespace.notesapp.ui.home.viewmodel.NotesViewModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

import com.squareup.timessquare.CalendarPickerView


class CalenderFragment : BaseFragment() {

    private lateinit var binding: FragmentCalenderBinding

    var newValue = 0
    var y: Int = 0
    var m: Int = 0
    var d: Int = 0
    var pickerVals = arrayOf("2019", "2020", "2021", "2022", "2023")

    var nextYear: Calendar? = null
    var sdf: SimpleDateFormat? = null
    var currentDateandTime: String? = null
    var dt: Date? = null
    lateinit var data: Array<String>
    var years: ArrayList<String> = ArrayList()
    var yearList: List<Years> = ArrayList<Years>()
    val REQUEST_DATE_CODE = 777
    var imageView: ImageView? = null

    // firebase integrate
    var db = FirebaseFirestore.getInstance()
    var firebaseAuth = FirebaseAuth.getInstance()

    companion object {
        private var instance: CalenderFragment? = null
        private var viewModel: NotesViewModel? = null

        @JvmStatic
        fun newInstance(viewModelm: NotesViewModel): CalenderFragment? {
            this.viewModel = viewModelm
            if (instance == null) {
                instance = CalenderFragment()
            }
            return instance
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.fragment_calender, container, false)
        val font = Typeface.createFromAsset(requireActivity().assets, "poppins_regular.ttf")
        val fontTitle = Typeface.createFromAsset(requireActivity().assets, "poppins_medium.ttf")

        binding.calendarView.setTypeface(font)
        binding.calendarView.setTitleTypeface(fontTitle)
        val currentYear = viewModel!!.methods.ConverMillsTo(System.currentTimeMillis(), "yyyy")
        WorkStation()
        return binding.root
    }

    override fun WorkStation() {
        AccessClickLIstener()
    }

    private fun AccessClickLIstener() {

        binding.ivPageChange.setOnClickListener {
            startFragment(
                CalenderFragment1.newInstance(CalenderFragment1.viewModel!!),
                CalenderFragment1.toString(),
                true
            )
        }

        val cl = Calendar.getInstance()
        y = cl[Calendar.YEAR]
        m = cl[Calendar.MONTH]
        d = cl[Calendar.DATE]

        nextYear = Calendar.getInstance()
        nextYear!!.add(Calendar.YEAR, 10)

        sdf = SimpleDateFormat("yyyy/MM/dd")
        y = y - 1900
        currentDateandTime = sdf!!.format(Date(y, m, d))
        dt = null
        try {
            dt = sdf!!.parse(currentDateandTime)
//           myDate.setTime(dt);
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        binding.calendarView.init(dt, nextYear!!.time).inMode(CalendarPickerView.SelectionMode.MULTIPLE).withSelectedDate(dt)

        binding.calendarView.setOnDateSelectedListener(object : OnDateSelectedListener {
            override fun onDateSelected(date: Date) {
                val formate = "dd-MM-yyyy"
                val inputFormat = SimpleDateFormat(formate)
                val myDate = inputFormat.format(date)

                // get required dates
                val dayOfTheWeek = DateFormat.format("EEEE", date) as String // Thursday
                val day = DateFormat.format("dd", date) as String // 20
                val monthString = DateFormat.format("MMMM", date) as String // Jun

                // check in firebase whether event exist or not on selected date
                db.collection("event").whereEqualTo("start_date", myDate)
                    .get()
                    .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
                        val documentExists: Boolean
                        if (task.isSuccessful) {
                            documentExists = !task.result.isEmpty
                            for (document in task.result) {
                            }
                        } else {
                            documentExists = false
                        }
                        if (documentExists) {
                            val formate = "dd"
                            val inputFormat = SimpleDateFormat(formate)
                            val myDate = inputFormat.format(date)
                            startFragment(
                                EventDetailsFragment.newInstance(
                                    viewModel!!,
                                    myDate,
                                    dayOfTheWeek,
                                    day,
                                    monthString
                                ), EventDetailsFragment.toString(), true
                            )
                        } else {
                            showCustomAlert("Event Not Found", binding.root)
                        }
                    })
            }

            override fun onDateUnselected(date: Date) {}
        })

        val rootRef = FirebaseFirestore.getInstance()
        val applicationsRef = rootRef.collection("all_years")
        val applicationIdRef = applicationsRef.document("years")
        applicationIdRef.get().addOnCompleteListener { task: Task<DocumentSnapshot> ->
            if (task.isSuccessful) {
                val document = task.result
                if (document.exists()) {
                    //description = (List<Map<String, Object>>) document.get("description");
                    yearList = document.toObject(YearsDocuments::class.java)!!.years
                    for (i in yearList.indices) {
                        years.add(yearList[i].title)
                    }
                    data = arrayOf(arrayOf(years.size).toString())
                    data = years.toArray<String>(data)
                    binding.NumberPickerYear.setWrapSelectorWheel(true)
                    binding.NumberPickerYear.setMinValue(0)
                    binding.NumberPickerYear.setMaxValue(data.size - 1)
                    binding.NumberPickerYear.setDisplayedValues(data)
                    binding.NumberPickerYear.setWrapSelectorWheel(false)
                    binding.NumberPickerYear.setOnValueChangedListener { picker, oldVal, newVal ->
                        newValue = data[newVal].toInt()
                        y = newValue - 1900
                        nextYear = Calendar.getInstance()
                        // nextYear.add(Calendar.YEAR, 10)
                        nextYear!!.add(Calendar.YEAR, 10)
                        /*  val today = Date()
         binding.calendarView.init(today, nextYear!!.time)
             .withSelectedDate(today)*/
                        sdf = SimpleDateFormat("yyyy/MM/dd")
                        currentDateandTime = sdf!!.format(Date(y, m, d))
                        dt = null
                        try {
                            dt = sdf!!.parse(currentDateandTime)
                        } catch (e: ParseException) {
                            e.printStackTrace()
                        }
                        binding.calendarView.init(dt, nextYear!!.getTime()).withSelectedDate(dt)
                    }
                }
            }
        }
    }

}