package com.timespace.notesapp.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.timespace.notesapp.R
import com.timespace.notesapp.base.BaseFragment
import com.timespace.notesapp.constance.AppConstance
import com.timespace.notesapp.databinding.FragmentProfileBinding
import com.timespace.notesapp.ui.calender.CalenderFragment1
import com.timespace.notesapp.ui.home.viewmodel.NotesViewModel

class ProfileFragment : BaseFragment() {

    private lateinit var binding: FragmentProfileBinding

    companion object {
        private var instance: ProfileFragment? = null
        private var viewModel: NotesViewModel? = null

        @JvmStatic
        fun newInstance(viewModelm: NotesViewModel): ProfileFragment? {
            viewModel = viewModelm
            if (instance == null) {
                instance = ProfileFragment()
            }
            return instance
        }

    }

    override fun WorkStation() {
        binding.ivCross.setOnClickListener {
            startFragment(SettingFragment.newInstance(viewModel!!), SettingFragment.toString(),true)
        }
        binding.btnYellow.setOnClickListener {
            getSharedPre().setThemeData("2")
            AppConstance.THEME_DATA = "2"
            //setBgData(binding.relative1)
            setViewTextColor(binding.tvSelectSpace)
        }
        binding.btnPink.setOnClickListener {
            getSharedPre().setThemeData("1")
            AppConstance.THEME_DATA = "1"
            //setBgData(binding.relative1)
            setViewTextColor(binding.tvSelectSpace)
        }
        binding.btnGreen.setOnClickListener {
            getSharedPre().setThemeData("3")
            AppConstance.THEME_DATA = "3"
            setBgData(binding.relative1)
            setViewTextColor(binding.tvSelectSpace)
        }
        binding.btnBlue.setOnClickListener {
            getSharedPre().setThemeData("4")
            AppConstance.THEME_DATA = "4"
            //setBgData(binding.relative1)
            setViewTextColor(binding.tvSelectSpace)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            LayoutInflater.from(requireContext()),
            R.layout.fragment_profile,
            container,
            false
        )
        WorkStation()
        return binding.root
    }

}