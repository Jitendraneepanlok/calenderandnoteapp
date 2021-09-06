 package com.timespace.notesapp.ui.meeting

import android.app.ProgressDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chivorn.datetimeoptionspicker.DateTimePickerView
import com.chivorn.datetimeoptionspicker.DateTimePickerView.OnTimeSelectListener
import com.chivorn.datetimeoptionspicker.OptionsPickerView
import com.chivorn.datetimeoptionspicker.OptionsPickerView.OnOptionsSelectListener
import com.chivorn.datetimeoptionspicker.listener.CustomListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.timespace.notesapp.R
import com.timespace.notesapp.base.BaseFragment
import com.timespace.notesapp.databinding.FragmentCreateMeetingBinding
import com.timespace.notesapp.firebasemodel.AlertModel
import com.timespace.notesapp.firebasemodel.MySpace
import com.timespace.notesapp.firebasemodel.Repeat
import com.timespace.notesapp.ui.home.fragment.home.HomeFragment
import com.timespace.notesapp.ui.home.viewmodel.NotesViewModel
import com.timespace.notesapp.ui.meeting.adapter.Alert_adapter
import com.timespace.notesapp.ui.meeting.adapter.Tags_adapter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import com.google.firebase.firestore.DocumentReference




class CreateMeetingFragment : BaseFragment()  {

    private lateinit var binding: FragmentCreateMeetingBinding
    private var dtpvOptions: OptionsPickerView<*>? = null
    private val alert1 = ArrayList<String>()
    private val alert2 = ArrayList<String>()
    private val alert3 = ArrayList<String>()
    private var alert_adapter: Alert_adapter? = null
    private var tags_adapter: Tags_adapter? = null
    private  var dtpvNoLinkOptions:OptionsPickerView<*>? = null
    private var dtpvCustomTime: DateTimePickerView? = null
    private  var dtpvCustomLunar:DateTimePickerView? = null
    private  var dtpvCustomEndTime:DateTimePickerView? = null
    private  var dtpvCustomEndDate:DateTimePickerView? = null
    private val options1Items: ArrayList<Repeat> = ArrayList<Repeat>()
    var alertList: ArrayList<AlertModel> = ArrayList()
    var tagsList: ArrayList<AlertModel> = ArrayList()

    val db = FirebaseFirestore.getInstance()
    val firebaseAuth = FirebaseAuth.getInstance()

    companion object {
        private var instance: CreateMeetingFragment? = null
        private var viewModel: NotesViewModel? = null
        @JvmStatic
        fun newInstance(viewModelm: NotesViewModel): CreateMeetingFragment? {
            this.viewModel=viewModelm
            if (instance == null) {
                instance = CreateMeetingFragment()
            }
            return instance
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.fragment_create_meeting, container, false)

        WorkStation()
        return binding.root
    }

    override fun WorkStation() {
        initalizeRecyclerView()
        AccessClickLIstener()
    }

    private fun initalizeRecyclerView() {
        alert_adapter = Alert_adapter(requireActivity(), alertList)
        val linearLayoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
        binding.rvAlert.layoutManager = linearLayoutManager
        binding.rvAlert.adapter = alert_adapter
        binding.rvAlert.isNestedScrollingEnabled = true

        tags_adapter = Tags_adapter(requireActivity(), tagsList)
        val linearLayoutManager2 = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
        binding.rvTags.layoutManager = linearLayoutManager2
        binding.rvTags.adapter = tags_adapter
        binding.rvTags.isNestedScrollingEnabled = true
    }

    private fun AccessClickLIstener() {

        binding.ivCross.setOnClickListener {
            startFragment(HomeFragment.newInstance(viewModel!!), HomeFragment.toString(),true) }

        binding.switchButton.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                binding.frame3.visibility = View.GONE
            }
            else{
                binding.frame3.visibility = View.VISIBLE
            }
        }

        initLunarPicker()
        initCustomTimePicker()
        initLunarEndPicker()
        initOptionPicker()
        getOptionData()
        initCustomTimeEndPicker()
        initNoLinkOptionsPicker()

        binding.tvStartDate.setOnClickListener(View.OnClickListener { dtpvCustomLunar!!.show() })

        binding.tvEndDate.setOnClickListener(View.OnClickListener { dtpvCustomEndDate!!.show() })

        binding.frame4.setOnClickListener(View.OnClickListener { dtpvOptions!!.show() })

        binding.tvStartTime.setOnClickListener(View.OnClickListener { dtpvCustomTime!!.show() })

        binding.tvEndTime.setOnClickListener(View.OnClickListener { dtpvCustomEndTime!!.show() })

        binding.linear2.setOnClickListener(View.OnClickListener { dtpvNoLinkOptions!!.show() })

        binding.linear3.setOnClickListener(View.OnClickListener {
            binding.etTags.visibility = View.VISIBLE
            binding.ivAddTag.visibility = View.VISIBLE
        })

        binding.linear4.setOnClickListener(View.OnClickListener { binding.flEdit.visibility = View.VISIBLE })

        binding.ivAddDetail.setOnClickListener(View.OnClickListener {
            val text: String = binding.etDetail.getText().toString()
            binding.linear4.visibility = View.GONE
            binding.flEdit.visibility = View.GONE
            binding.llDetail.visibility = View.VISIBLE
            binding.tvDetail.text = text
        })

        binding.ivAddTag.setOnClickListener(View.OnClickListener {
            val alertModel = AlertModel()
            alertModel.setAlertTime(binding.etTags.getText().toString())
            binding.etTags.setText("")
            val tagData = ArrayList<AlertModel>()
            tagData.add(alertModel)
            addTagData(tagData)
        })

        binding.ivDone.setOnClickListener(View.OnClickListener {
            val progressDialog = ProgressDialog(activity)
            progressDialog.setMessage("please wait...")
            progressDialog.setCancelable(false);
            progressDialog.show()

            /* // I have added data for testing.
            val db = FirebaseFirestore.getInstance()
             val db1 = db.collection("Jitendra Test").document("Jitendra Meetings")
             val user: MutableMap<String, Any> = HashMap()
             user.put("Meeting","Dummy")
             user.put("Date","Today")
             user.put("Tag","@Test")
             db1.set(user) */


            val title: String
            val startDate: String
            val endDate: String
            val start_time: String
            val end_time: String
            val detail: String

            title = binding.tvTitle.getText().toString()
            startDate = binding.tvStartDate.getText().toString()
            start_time = binding.tvStartTime.getText().toString()
            endDate = binding.tvEndDate.getText().toString()
            end_time = binding.tvEndTime.getText().toString()
            detail = binding.tvDetail.getText().toString()
            when {
                title == "" -> {
                    showCustomAlert(R.string.title_msg.toString(),binding.root)
                }
                startDate == R.string.start_date.toString() -> {
                    showCustomAlert(R.string.title_msg.toString(),binding.root)
                }
                endDate == R.string.end_date.toString() -> {
                    showCustomAlert(R.string.title_msg.toString(),binding.root)
                }
                start_time == R.string.start_time.toString() -> {
                    showCustomAlert(R.string.title_msg.toString(),binding.root)
                }
                end_time == R.string.end_time.toString() -> {
                    showCustomAlert(R.string.title_msg.toString(),binding.root)
                }
                detail == R.string.search_text.toString() -> {
                    showCustomAlert(R.string.title_msg.toString(),binding.root)
                }
                else -> {
                    val mySpace = MySpace()
                    mySpace.setTitle(binding.tvTitle.getText().toString())
                    mySpace.setAll_day(false)
                    mySpace.setStart_date(binding.tvStartDate.getText().toString())
                    mySpace.setStart_time(binding.tvStartTime.getText().toString())
                    mySpace.setEnd_date(binding.tvEndDate.getText().toString())
                    mySpace.setEnd_time(binding.tvEndTime.getText().toString())
                    mySpace.setRepeat(binding.tvRepeat.getText().toString())
                    mySpace.setDetails(binding.tvDetail.getText().toString())
                    mySpace.setAlerts(alertList)
                    mySpace.setTags(tagsList)

                db.collection("users")
                        .document(firebaseAuth.currentUser!!.uid)
                        .collection("new_meeting")
                        .document()
                        .set(mySpace)
                        .addOnSuccessListener(OnSuccessListener<Void?> {
                            //binding.tvTitle.text = ""
                            showCustomAlert("success",binding.root)
                            progressDialog.dismiss()
                        })
                        .addOnFailureListener(OnFailureListener { e ->
                            showCustomAlert(e.message.toString(),binding.root)
                            progressDialog.dismiss()
                        })
                }
            }
        })
    }

    private fun addTagData(levelList: ArrayList<AlertModel>) {
        //  this.alertList.clear();
        if (levelList != null) {
            tagsList.addAll(levelList)
        }
        tags_adapter?.notifyDataSetChanged()
    }

    private fun initOptionPicker() {
        dtpvOptions = OptionsPickerView.Builder(requireContext(),
            OnOptionsSelectListener { options1, options2, options3, v ->
                val tx: String? = options1Items.get(options1).getPickerViewText()
                binding.tvRepeat.text = tx
            })
            .setTitleText("")
            .setContentTextSize(20)
            .setDividerColor(Color.GRAY)
            .setSelectOptions(0, 1)
            .setBgColor(Color.WHITE)
            .setTitleBgColor(Color.GRAY)
            .setTitleColor(Color.BLACK)
            .setCancelColor(Color.BLACK)
            .setSubmitColor(Color.BLACK)
            .setTextColorCenter(Color.BLACK)
            .isCenterLabel(false)
            .setBackgroundId(0x66000000)
            .build()
        (dtpvOptions as OptionsPickerView<*>?)!!.setPicker(options1Items as List<Nothing>?)
    }

    private fun getOptionData() {
        getNoLinkData()
        options1Items.add(Repeat("No Repeat"))
        options1Items.add(Repeat("Every Day"))
        options1Items.add(Repeat("Every Week"))
        options1Items.add(Repeat("Every Month"))
        options1Items.add(Repeat("Every Year"))
        options1Items.add(Repeat("Custom"))
    }

    private fun getNoLinkData() {
        alert1.add("Before")
        alert2.add("Minutes")
        alert2.add("Hours")
        alert2.add("Days")
        alert2.add("Week")
        alert3.add("00")
        alert3.add("05")
        alert3.add("10")
        alert3.add("15")
        alert3.add("20")
        alert3.add("25")
        alert3.add("30")
        alert3.add("35")
        alert3.add("40")
        alert3.add("45")
        alert3.add("50")
        alert3.add("55")
        alert3.add("60")
    }

    private fun initNoLinkOptionsPicker() {
        dtpvNoLinkOptions = OptionsPickerView.Builder(requireActivity(),
            OnOptionsSelectListener { options1, options2, options3, v ->
                val alertModel = AlertModel()

                alertModel.setAlertTime(
                    alert1.get(options1).toString() + " " + alert2.get(options2) + " " + alert3.get(
                        options3
                    )
                )
                val alertData: ArrayList<AlertModel> = ArrayList<AlertModel>()
                alertData.add(alertModel)
                addData(alertData)
                val str = """
            1:${alert1.get(options1)}
            2:${alert2.get(options2)}
            3:${alert3.get(options3)}
            """.trimIndent()
            }).build()
        (dtpvNoLinkOptions as OptionsPickerView<*>?)!!.setNPicker(alert1 as List<Nothing>?,
            alert2 as List<Nothing>?, alert3 as List<Nothing>?
        )
    }

    private fun addData(levelList: ArrayList<AlertModel>) {

        //  this.alertList.clear();
        if (levelList != null) {
            alertList.addAll(levelList)
        }
        alert_adapter?.notifyDataSetChanged()
    }

    private fun initLunarPicker() {
        val selectedDate = Calendar.getInstance()
        val startDate = Calendar.getInstance()
        startDate[2014, 1] = 23
        val endDate = Calendar.getInstance()
        endDate[2030, 12] = 31
        dtpvCustomLunar = DateTimePickerView.Builder(requireActivity(),
            OnTimeSelectListener { date, v -> binding.tvStartDate.setText(getDate(date)) })
            .setDate(selectedDate)
            .setRangDate(startDate, endDate)
            .setLayoutRes(R.layout.datetimeoptionspicker_custom_lunar, object : CustomListener {
                override fun customLayout(v: View) {
                    val tvSubmit = v.findViewById<View>(R.id.tv_finish) as TextView
                    val tvCancel = v.findViewById<View>(R.id.tv_cancel) as TextView
                    tvSubmit.setOnClickListener {
                        dtpvCustomLunar!!.returnData()
                        dtpvCustomLunar!!.dismiss()
                    }
                    tvCancel.setOnClickListener { dtpvCustomLunar!!.dismiss() }
                }

                private fun setTimePickerChildWeight(v: View, yearWeight: Float, weight: Float) {
                    val timepicker = v.findViewById<View>(R.id.timepicker) as ViewGroup
                    val year = timepicker.getChildAt(0)
                    val lp = year.layoutParams as LinearLayout.LayoutParams
                    lp.weight = yearWeight
                    year.layoutParams = lp
                    for (i in 1 until timepicker.childCount) {
                        val childAt = timepicker.getChildAt(i)
                        val childLp = childAt.layoutParams as LinearLayout.LayoutParams
                        childLp.weight = weight
                        childAt.layoutParams = childLp
                    }
                }
            })
            .setType(booleanArrayOf(true, true, true, false, false, false))
            .isCenterLabel(false)
            .setDividerColor(Color.RED)
            .build()
    }

    private fun initCustomTimePicker() {
        val selectedDate = Calendar.getInstance()
        val startDate = Calendar.getInstance()
        startDate[2014, 1] = 23
        val endDate = Calendar.getInstance()
        endDate[2027, 2] = 28
        dtpvCustomTime = DateTimePickerView.Builder(requireActivity(),
            OnTimeSelectListener { date, v ->
                binding.tvStartTime.setText(getTime(date))
            })
            .setDate(selectedDate)
            .setRangDate(startDate, endDate)
            .isCyclic(false)
            .isCenterLabel(true)
            .setLayoutRes(
                R.layout.datetimeoptionspicker_custom_time
            ) { v -> // dtpvCustomTime.returnData();
                val tvSubmit = v.findViewById<View>(R.id.tv_finish) as TextView
                val tvCancel = v.findViewById<View>(R.id.tv_cancel) as TextView
                tvSubmit.setOnClickListener {
                    dtpvCustomTime!!.returnData()
                    dtpvCustomTime!!.dismiss()
                }
                tvCancel.setOnClickListener { dtpvCustomTime!!.dismiss() }
            }
            .setContentSize(18)
            .setType(booleanArrayOf(false, false, false, true, true, true))
            .setLabel("y", "m", "d", "h", "min", "s")
            .setLineSpacingMultiplier(1.2f)
            .setTextXOffset(0, 0, 0, 40, 0, -40)
            .isCenterLabel(true)
            .setDividerColor(-0xdb5263)
            .build()
    }

    private fun initLunarEndPicker() {
        val selectedDate = Calendar.getInstance()
        val startDate = Calendar.getInstance()
        startDate[2014, 1] = 23
        val endDate = Calendar.getInstance()
        endDate[2030, 12] = 31
        dtpvCustomEndDate = DateTimePickerView.Builder(requireActivity(),
            OnTimeSelectListener { date, v ->
                binding.tvEndDate.setText(getDate(date))
            })
            .setDate(selectedDate)
            .setRangDate(startDate, endDate)
            .setLayoutRes(R.layout.datetimeoptionspicker_custom_lunar, object : CustomListener {
                override fun customLayout(v: View) {
                    val tvSubmit = v.findViewById<View>(R.id.tv_finish) as TextView
                    val tvCancel = v.findViewById<View>(R.id.tv_cancel) as TextView
                    tvSubmit.setOnClickListener {
                        dtpvCustomEndDate!!.returnData()
                        dtpvCustomEndDate!!.dismiss()
                    }
                    tvCancel.setOnClickListener { dtpvCustomEndDate!!.dismiss() }

                }

                private fun setTimePickerChildWeight(v: View, yearWeight: Float, weight: Float) {
                    val timepicker = v.findViewById<View>(R.id.timepicker) as ViewGroup
                    val year = timepicker.getChildAt(0)
                    val lp = year.layoutParams as LinearLayout.LayoutParams
                    lp.weight = yearWeight
                    year.layoutParams = lp
                    for (i in 1 until timepicker.childCount) {
                        val childAt = timepicker.getChildAt(i)
                        val childLp = childAt.layoutParams as LinearLayout.LayoutParams
                        childLp.weight = weight
                        childAt.layoutParams = childLp
                    }
                }
            })
            .setType(booleanArrayOf(true, true, true, false, false, false))
            .isCenterLabel(false)
            .setDividerColor(Color.RED)
            .build()
    }

    private fun initCustomTimeEndPicker() {
        val selectedDate = Calendar.getInstance()
        val startDate = Calendar.getInstance()
        startDate[2014, 1] = 23
        val endDate = Calendar.getInstance()
        endDate[2027, 2] = 28
        dtpvCustomEndTime = DateTimePickerView.Builder(requireActivity(),
            OnTimeSelectListener { date, v ->
                binding.tvEndTime.setText(getTime(date))
            })
            .setDate(selectedDate)
            .setRangDate(startDate, endDate)
            .setLayoutRes(
                R.layout.datetimeoptionspicker_custom_time
            ) { v ->
                val tvSubmit = v.findViewById<View>(R.id.tv_finish) as TextView
                val tvCancel = v.findViewById<View>(R.id.tv_cancel) as TextView
                tvSubmit.setOnClickListener {
                    dtpvCustomEndTime!!.returnData()
                    dtpvCustomEndTime!!.dismiss()
                }
                tvCancel.setOnClickListener { dtpvCustomEndTime!!.dismiss() }
            }
            .setContentSize(18)
            .setType(booleanArrayOf(false, false, false, true, true, true))
            .setLabel("y", "m", "d", "h", "min", "s")
            .setLineSpacingMultiplier(1.2f)
            .setTextXOffset(0, 0, 0, 40, 0, -40)
            .isCenterLabel(false)
            .setDividerColor(-0xdb5263)
            .build()
    }

    private fun getTime(date: Date): String? {
        val format = SimpleDateFormat("HH:mm:ss")
        return format.format(date)
    }

    private fun getDate(date: Date): String? {
        val format = SimpleDateFormat("yyyy-MMMM-dd")
        return format.format(date)
    }


}