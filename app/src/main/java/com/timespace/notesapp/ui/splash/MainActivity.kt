package com.timespace.notesapp.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.databinding.DataBindingUtil
import com.timespace.notesapp.R
import com.timespace.notesapp.base.BaseActivity
import com.timespace.notesapp.constance.AppConstance
import com.timespace.notesapp.databinding.ActivityMainBinding
import com.timespace.notesapp.ui.home.HomeActivity
import com.timespace.notesapp.ui.welcomescreen.goodMorning.ApprovalActivity
import com.timespace.notesapp.ui.welcomescreen.languageselector.LanguageSelectorActivity
import com.timespace.notesapp.ui.welcomescreen.locationSelector.LocationSelectorActivity


class MainActivity : BaseActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil. setContentView(this,R.layout.activity_main)
        Handler().postDelayed(Runnable {
           if(sharedPre.ScreenType.equals(AppConstance.SCREEN_APPROVED)){
                val i = Intent(this, ApprovalActivity::class.java)
                startActivity(i)
                finish()
            }else if(sharedPre.ScreenType.equals(AppConstance.SCREEN_HOME)){
                val i = Intent(this, HomeActivity::class.java)
                startActivity(i)
                finish()
            }else {
                val i = Intent(this, LanguageSelectorActivity::class.java)
                startActivity(i)
                finish()
            }

        }, 1500)

    }
}