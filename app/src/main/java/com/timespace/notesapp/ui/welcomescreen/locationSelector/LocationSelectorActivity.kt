package com.timespace.notesapp.ui.welcomescreen.locationSelector

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.chivorn.datetimeoptionspicker.OptionsPickerView
import com.google.firebase.firestore.QuerySnapshot
import com.timespace.notesapp.R
import com.timespace.notesapp.base.BaseActivity
import com.timespace.notesapp.constance.AppConstance
import com.timespace.notesapp.databinding.ActivityLocationSelectorBinding
import com.timespace.notesapp.firebasemodel.NewUser
import com.timespace.notesapp.ui.welcomescreen.goodMorning.ApprovalActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.*
import kotlin.collections.ArrayList

class LocationSelectorActivity : BaseActivity() {
    private lateinit var binding: ActivityLocationSelectorBinding
    private val districtOptions: OptionsPickerView<*>? = null
    private var townsOptions: OptionsPickerView<*>? = null
    var cities: ArrayList<String> = ArrayList()
    var towns: ArrayList<String> = ArrayList()
    lateinit var dataDistrict: Array<String>
    lateinit var dataTown: Array<String>
    var flagD: Int = 0
    var flagT: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_location_selector)
        CallDataBaseOnline()
        CallAdapters()
        CallClickListener()
        binding.BackButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun CallDataBaseOnline() {

    }

    private fun CallClickListener() {
        binding.districtSelectBtn.setOnClickListener {
            if (flagD == 0) {
                flagD = 1
                flagT = 0
                binding.districtPicker.visibility = View.VISIBLE
                binding.districtSelectBtn.visibility = View.GONE
                binding.districtView.visibility = View.GONE
                binding.townSelectBtn.visibility = View.VISIBLE
                binding.townView.visibility = View.VISIBLE
                binding.TownPicker.visibility = View.GONE
            }
        }

        binding.btnEnter.setOnClickListener(View.OnClickListener {
            if (binding.districtSelectBtn.getText().toString() == "District") {
                showCustomAlert("Please Select District", binding.root)
            } else if (binding.townSelectBtn.getText().toString() == "Town") {
                showCustomAlert("Please Select Town", binding.root)
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    val user = NewUser(
                        sharedPre.userMobile,
                        sharedPre.name,
                        sharedPre.district,
                        sharedPre.town,
                        "0"
                    )
                    var data =
                        firestore.collection("new_users").document(sharedPre.userMobile!!).get()
                            .await().toObject(
                                NewUser::class.java
                            )
                    if (data == null) {
                        var data =
                            firestore.collection("new_users").document(sharedPre.userMobile!!)
                                .set(user).addOnCompleteListener {
                                    if (it != null && it.isSuccessful) {
                                        sharedPre.ScreenType = AppConstance.SCREEN_APPROVED
                                        startActivity(
                                            Intent(
                                                this@LocationSelectorActivity,
                                                ApprovalActivity::class.java
                                            )
                                        )
                                        finish()
                                    } else {
                                        showCustomAlert("Please Try Again!!", binding.root)
                                    }
                                }

                    } else {
                        if (data.status.equals("0")) {
                            sharedPre.ScreenType = AppConstance.SCREEN_APPROVED
                            startActivity(
                                Intent(
                                    this@LocationSelectorActivity,
                                    ApprovalActivity::class.java
                                )
                            )
                            finish()
                        } else {
                            sharedPre.ScreenType = AppConstance.SCREEN_APPROVED
                            startActivity(
                                Intent(
                                    this@LocationSelectorActivity,
                                    ApprovalActivity::class.java
                                )
                            )
                            finish()
                        }
                    }
                }


            }


        })

        binding.townSelectBtn.setOnClickListener {

            if (binding.districtSelectBtn.text.toString().equals("District")){
                showCustomAlert("please select district first",binding.root)
            }
            else{
                if (flagT == 0) {
                    flagT = 1
                    flagD = 0
                    binding.TownPicker.visibility = View.VISIBLE
                    binding.townSelectBtn.visibility = View.GONE
                    binding.townView.visibility = View.GONE
                    binding.districtSelectBtn.visibility = View.VISIBLE
                    binding.districtView.visibility = View.VISIBLE
                    binding.districtPicker.visibility = View.GONE
                }

                firestore.collection("towns")
                    .whereEqualTo(
                        "district",
                        binding.districtSelectBtn.getText().toString()
                    )
                    .get().addOnCompleteListener {
                        if (it != null) {
                            towns = ArrayList()
                            val snapshots: QuerySnapshot = it.result
                            for (doc in snapshots) {
                                if (doc["name"] != null) {
                                    towns.add(doc.getString("name")!!)
                                }
                            }
                            dataTown = arrayOf<String>(towns.size.toString())
                            dataTown = towns.toArray<String>(dataTown)

                            if (dataTown.isNotEmpty()){
                                binding.TownPicker.setMinValue(0)
                                binding.TownPicker.setMaxValue(dataTown.size - 1)
                                binding.TownPicker.setDisplayedValues(dataTown)
                                binding.TownPicker.setWrapSelectorWheel(true)
                                binding.townSelectBtn.setText(towns.get(0).toString())
                                binding.TownPicker.setAccessibilityDescriptionEnabled(true)
                                binding.TownPicker.setOnValueChangedListener { picker, oldVal, newVal ->
                                    //   binding.districtPicker.visibility = View.GONE
                                    binding.townSelectBtn.setText(
                                        towns.get(newVal).toString()
                                    )
                                    sharedPre.town = towns.get(newVal)
                                }
                            }


                        }
                    }
            }

        }
    }

    private fun CallAdapters() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                var data = firestore.collection("districts").get().await()
                val snapshots: QuerySnapshot = data
                for (doc in snapshots) {
                    if (doc["name"] != null) {
                        cities.add(doc.getString("name")!!)
                    }
                }
                dataDistrict = arrayOf<String>(cities.size.toString())
                dataDistrict = cities.toArray<String>(dataDistrict)

                binding.districtPicker.setMinValue(0)
                binding.districtPicker.setMaxValue(dataDistrict.size - 1)
                binding.districtPicker.setDisplayedValues(dataDistrict)
                binding.districtPicker.setWrapSelectorWheel(true)
                binding.districtSelectBtn.setText(getString(R.string.district))
                binding.districtPicker.setAccessibilityDescriptionEnabled(true)
                binding.districtPicker.setOnValueChangedListener { picker, oldVal, newVal ->

                    binding.districtSelectBtn.setText(
                        cities.get(newVal).toString()
                    )
                   // binding.TownPicker.visibility = View.GONE
                    sharedPre.district = cities.get(newVal)


                }

            } catch (e: Exception) {
                showCustomAlert("No Internet connection", binding.root)
            }

        }


    }

    override fun onBackPressed() {
        finish()
    }
}