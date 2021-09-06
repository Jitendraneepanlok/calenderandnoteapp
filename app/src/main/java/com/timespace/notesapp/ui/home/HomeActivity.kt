package com.timespace.notesapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayout
import com.timespace.notesapp.R
import com.timespace.notesapp.base.BaseActivity
import com.timespace.notesapp.databinding.ActivityHomeBinding
import com.timespace.notesapp.ui.calender.CalenderFragment
import com.timespace.notesapp.ui.calender.CalenderFragment1
import com.timespace.notesapp.ui.home.fragment.home.HomeFragment
import com.timespace.notesapp.ui.home.viewmodel.NotesViewModel
import com.timespace.notesapp.ui.meeting.CreateMeetingFragment
import com.timespace.notesapp.ui.notes.NotesActivity
import com.timespace.notesapp.ui.notes.createNotes.CreateNotesActivity
import com.timespace.notesapp.ui.setting.SettingFragment
import com.timespace.notesapp.ui.space.SpacesActivity

class HomeActivity : BaseActivity() {
    private lateinit var binding:ActivityHomeBinding
    private val viewModel: NotesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this, R.layout.activity_home)
        setView(binding.root)
        startFragment(HomeFragment.newInstance(viewModel), HomeFragment.toString(),true)
        ClickListners()
        AccessFirebase()

    }

    private fun AccessFirebase() {

    }

    private fun ClickListners() {
        binding.apps.setOnClickListener {
            if(binding.bottomView.isVisible){
                binding.bottomView.visibility=View.GONE
            }else{
                binding.bottomView.visibility=View.VISIBLE
            }
        }
        binding.add.setOnClickListener {
            if (getCurrentFragment() !is CreateMeetingFragment) {
                startFragment(
                    CreateMeetingFragment.newInstance(viewModel),
                    CreateMeetingFragment.toString(),
                    true
                )
            }
        }
       binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab!!.position){
                   0 ->{
                       if (getCurrentFragment() !is CalenderFragment1){
                           startFragment(CalenderFragment1.newInstance(viewModel),CalenderFragment1.toString(),true)
                           binding.bottomView.visibility=View.GONE
                       }

                    }

                    1->{
                        startActivity(Intent(this@HomeActivity, NotesActivity::class.java))
                    }
                    2->{
                        startActivity(Intent(this@HomeActivity, SpacesActivity::class.java))
                    }
                    3->{
                        startFragment(SettingFragment.newInstance(viewModel),SettingFragment.toString(),true)
                        binding.bottomView.visibility=View.GONE
                    }
                    else ->{

                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                when(tab!!.position){
                    0 ->{
                        startFragment(CalenderFragment1.newInstance(viewModel),CalenderFragment1.toString(),true)
                        binding.bottomView.visibility=View.GONE
                    }
                    2->{
                        startActivity(Intent(this@HomeActivity, SpacesActivity::class.java))
                    }
                    1->{
                        startActivity(Intent(this@HomeActivity, NotesActivity::class.java))
                    }
                    3->{
                        startFragment(SettingFragment.newInstance(viewModel),SettingFragment.toString(),true)
                        binding.bottomView.visibility=View.GONE
                    }
                    else ->{

                    }
                }
            }
        })

    }
}