package com.timespace.notesapp.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.timespace.notesapp.R
import com.timespace.notesapp.base.BaseFragment
import com.timespace.notesapp.databinding.FragmentSettingBinding
import com.timespace.notesapp.ui.home.viewmodel.NotesViewModel

class SettingFragment : BaseFragment(){

    private lateinit var binding: FragmentSettingBinding

    companion object {
        private var instance: SettingFragment? = null
        private var viewModel: NotesViewModel? = null

        @JvmStatic
        fun newInstance(viewModelm: NotesViewModel): SettingFragment? {
            viewModel = viewModelm
            if (instance == null) {
                instance = SettingFragment()
            }
            return instance
        }

    }

    override fun WorkStation() {
        binding.tvProfile.setOnClickListener {
            startFragment(ProfileFragment.newInstance(viewModel!!),ProfileFragment.toString(),true)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            LayoutInflater.from(requireContext()),
            R.layout.fragment_setting,
            container,
            false
        )
        WorkStation()
        return binding.root
    }

}