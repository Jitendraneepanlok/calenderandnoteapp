package com.timespace.notesapp.ui.home.fragment.space

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.timespace.notesapp.R
import com.timespace.notesapp.base.BaseFragment
import com.timespace.notesapp.databinding.FragmentHomeBinding
import com.timespace.notesapp.firebasemodel.DailyEvents
import com.timespace.notesapp.ui.home.viewmodel.NotesViewModel
import kotlin.collections.ArrayList

class MySpaceFragment : BaseFragment() {
    private lateinit var binding: FragmentHomeBinding
    private var eventList: ArrayList<DailyEvents> = ArrayList()

    companion object {
        private var instance: MySpaceFragment? = null
        private var viewModel: NotesViewModel? = null

        @JvmStatic
        fun newInstance(viewModelm: NotesViewModel): MySpaceFragment? {
            viewModel = viewModelm
            if (instance == null) {
                instance = MySpaceFragment()
            }
            return instance
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            LayoutInflater.from(requireContext()),
            R.layout.fragment_home,
            container,
            false
        )
        WorkStation()
        return binding.root
    }

    override fun WorkStation() {

    }
}