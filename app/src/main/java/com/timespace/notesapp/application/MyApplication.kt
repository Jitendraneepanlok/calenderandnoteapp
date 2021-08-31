package com.timespace.notesapp.application
/*Android Developer
* Ishant Sharma
* Java and Kotlin
*www.devishant.com
* */
import android.app.Application
import com.timespace.notesapp.application.ThemeHelper.applyTheme
import com.timespace.notesapp.database.prefrence.SharedPre
import dagger.hilt.android.HiltAndroidApp
import io.paperdb.Paper


@HiltAndroidApp()
class MyApplication : Application() {
    private lateinit var sharedPre: SharedPre

    override fun onCreate() {
        super.onCreate()
        Paper.init(this)
        sharedPre = SharedPre.getInstance(getApplicationContext())!!
        val themePref = sharedPre.GetCurrentTheme
        if(!themePref.isNullOrEmpty()){
            if(themePref.equals(ThemeHelper.LIGHT_MODE)){
                sharedPre.isDarkModeEnable=false
            }else if(themePref.equals(ThemeHelper.DARK_MODE)){
                sharedPre.isDarkModeEnable=true
            }
            applyTheme(themePref)
        }else{
            sharedPre.isDarkModeEnable=false
            applyTheme(ThemeHelper.LIGHT_MODE)

        }

    }
    fun isNightModeEnabled(): Boolean {
        return  sharedPre.isDarkModeEnable
    }
    fun setIsNightModeEnabled(isNightModeEnabled: Boolean) {
       sharedPre.isDarkModeEnable=isNightModeEnabled
    }
}