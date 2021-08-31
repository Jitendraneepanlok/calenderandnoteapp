package com.timespace.notesapp.ui.welcomescreen.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.databinding.DataBindingUtil
import com.timespace.notesapp.R
import com.timespace.notesapp.base.BaseActivity
import com.timespace.notesapp.constance.AppConstance
import com.timespace.notesapp.databinding.ActivityRegisterBinding
import com.timespace.notesapp.ui.welcomescreen.locationSelector.LocationSelectorActivity

class RegisterActivity : BaseActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var context: Context
    private var handler=Handler()
    private var runnable:Runnable?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=DataBindingUtil.setContentView(this,R.layout.activity_register)
        context=this
        binding.MobileNumber.setText(sharedPre.userMobile.toString())

        binding.next.setOnClickListener {
            if(binding.enterName.text.isNullOrEmpty()){
                showCustomAlert(getString(R.string.error_on_name),binding.root)
                binding.enterName.requestFocus()
            }else if( binding.MobileNumber.text.isNullOrEmpty()){
                showCustomAlert(getString(R.string.error_on_mobile_number),binding.root)
                binding.MobileNumber.requestFocus()
            }
            else{
                sharedPre.setName(binding.enterName.text.toString())
                startActivity(Intent(context, LocationSelectorActivity::class.java))
                sharedPre.ScreenType= AppConstance.SCREEN_LOCATION
            }

        }
        binding.BackButton.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        finish()
    }
}