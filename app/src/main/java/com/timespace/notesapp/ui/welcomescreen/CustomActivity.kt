package com.timespace.notesapp.ui.welcomescreen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.timespace.notesapp.R
import com.timespace.notesapp.databinding.ActivityCustomBinding
import java.util.*

class CustomActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCustomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_custom)
        val data = arrayOf(getString(R.string.english), getString(R.string.kannad))
        binding.NumberPicker.setMinValue(1)
        binding.NumberPicker.setMaxValue(data.size)
        binding.NumberPicker.setDisplayedValues(data)
        binding.NumberPicker.setValue(7)
     //   binding.NumberPicker.wrapSelectorWheel= true

      // Set fading edge enabled
       binding.NumberPicker.setFadingEdgeEnabled(true);

       // Set scroller enabled
        binding.NumberPicker.setScrollerEnabled(true);

      // Set wrap selector wheel
        binding.NumberPicker.setWrapSelectorWheel(true);

     // Set accessibility description enabled
       binding.NumberPicker.setAccessibilityDescriptionEnabled(true);
        binding.NumberPicker.setOnValueChangedListener { picker, oldVal, newVal ->

        }


        /* val values = arrayOf(getString(R.string.english), getString(R.string.kannad))
         binding.NumberPicker.minValue = 0
         binding.NumberPicker.maxValue = values.size - 1
         binding.NumberPicker.displayedValues = values
         binding.NumberPicker.wrapSelectorWheel = true
         binding.NumberPicker.setOnValueChangedListener { picker, oldVal, newVal ->

         }

         */


    }


}

