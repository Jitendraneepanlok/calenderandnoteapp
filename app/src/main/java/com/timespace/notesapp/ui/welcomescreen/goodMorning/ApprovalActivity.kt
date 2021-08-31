package com.timespace.notesapp.ui.welcomescreen.goodMorning

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.timespace.notesapp.R
import com.timespace.notesapp.base.BaseActivity
import com.timespace.notesapp.databinding.ActivityGoodMorningBinding
import com.timespace.notesapp.firebasemodel.NewUser
import com.timespace.notesapp.ui.login.otp.OtpActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*

class ApprovalActivity : BaseActivity() {
    private lateinit var binding:ActivityGoodMorningBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_good_morning)
        setIconTintColor(binding.ivIcon)
        setViewTextColor(binding.pendingApproval)
        val sdf = SimpleDateFormat("EEEE")
        val d = Date()
        val dayOfTheWeek = sdf.format(d)
        val c = Calendar.getInstance()
        val timeOfDay = c[Calendar.HOUR_OF_DAY]
        val month_date = SimpleDateFormat("MMMM")
        val month_day = SimpleDateFormat("dd")
        val month_name = month_date.format(c.getTime())
        val monthDay = month_day.format(c.getTime())
        binding.date.setText("It's $dayOfTheWeek"+"\n"+"$monthDay , $month_name")
        if (timeOfDay >= 0 && timeOfDay < 12) {
            binding.wishMe.setText(getString(R.string.goodmorning))
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            binding.wishMe.setText(getString(R.string.good_afternoon))
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            binding.wishMe.setText(getString(R.string.good_evening))
        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            binding.wishMe.setText(getString(R.string.good_night))
        }
        binding.name.setText(sharedPre.name)
        CoroutineScope(Dispatchers.IO).launch {
            var data = firestore.collection("new_users").document(sharedPre.userMobile!!).get().await().toObject(
                NewUser::class.java)
            if (data == null) {
                finish()
            } else {
                if (data.status.equals("0")) {
                    CoroutineScope(Dispatchers.Main).launch{
                        binding.genrateOtp.visibility = View.GONE
                        binding.hurrahTxt.visibility = View.GONE
                        binding.validatMessage.visibility = View.GONE
                        binding.pendingApproval.visibility = View.VISIBLE
                    }

                } else {
                    CoroutineScope(Dispatchers.Main).launch{
                        binding.genrateOtp.visibility = View.VISIBLE
                        binding.validatMessage.visibility = View.VISIBLE
                        binding.pendingApproval.visibility = View.GONE
                        binding.hurrahTxt.visibility = View.VISIBLE
                    }

                }
            }
        }
        binding.genrateOtp.setOnClickListener {
            val intent = Intent(this@ApprovalActivity, OtpActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}