package com.timespace.notesapp.ui.welcomescreen.languageselector

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.timespace.notesapp.R
import com.timespace.notesapp.base.BaseActivity
import com.timespace.notesapp.databinding.ActivityLanguageSelectorBinding
import com.timespace.notesapp.ui.welcomescreen.mobileScreen.MobileActivity
import java.util.*

class LanguageSelectorActivity : BaseActivity() {

    private lateinit var binding: ActivityLanguageSelectorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadLocal()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_language_selector)

        val data = arrayOf(getString(R.string.english) , getString(R.string.kannad))
        binding.numberPickerValue.setMinValue(0)
        binding.numberPickerValue.setMaxValue(data.size-1)
        binding.numberPickerValue.setDisplayedValues(data)
        binding.numberPickerValue.setValue(2)
        // Set fading edge enabled
      //  binding.numberPickerValue.setFadingEdgeEnabled(true);
        // Set scroller enabled
        binding.numberPickerValue.setScrollerEnabled(true);
        // Set wrap selector wheel
        binding.numberPickerValue.setWrapSelectorWheel(true);
        // Set accessibility description enabled
        binding.numberPickerValue.setAccessibilityDescriptionEnabled(true);
        binding.numberPickerValue.setOnValueChangedListener { picker, oldVal, newVal ->
            sharedPre.language = data.get(newVal)
            loadLocal()
        }
        binding.next.setOnClickListener {
            startActivity(Intent(this,MobileActivity::class.java))
        }
    }

    private fun loadLocal() {
        setLocale(sharedPre.language)
    }

    private fun setLocale(lang: String?) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val configuration = Configuration()
        configuration.locale = locale
        resources.updateConfiguration(
            configuration,
            getBaseContext().getResources().getDisplayMetrics()
        )

    }
}




