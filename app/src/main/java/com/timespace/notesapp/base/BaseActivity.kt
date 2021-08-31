package com.timespace.notesapp.base

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.tbruyelle.rxpermissions2.RxPermissions
import com.timespace.notesapp.R
import com.timespace.notesapp.application.ThemeHelper
import com.timespace.notesapp.database.datastore.DataStoreBase
import com.timespace.notesapp.database.prefrence.SharedPre
import com.timespace.notesapp.repositories.methods.MethodsRepo
import com.timespace.notesapp.ui.home.fragment.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {
    private var addToBackStack = false
  //  private var manager: FragmentManager? = null
    private var transaction: FragmentTransaction? = null
    private var fragment: Fragment? = null
    private var rxPermissions: RxPermissions? = null
    private var doubleBackToExitPressedOnce = false
    private var auth: FirebaseAuth? = null
    private lateinit var snackBar: Snackbar
    private lateinit var actiVityView: View
    @Inject
    lateinit var manager: FragmentManager
    @Inject
    lateinit var methods: MethodsRepo
    @Inject
    lateinit var datastore: DataStoreBase
    @Inject
    lateinit var getName:String
    @Inject
    lateinit var firestore: FirebaseFirestore
    @Inject
    lateinit var databaseFirebase: FirebaseDatabase
    @Inject
    lateinit var firebaseAuthentication: FirebaseAuth
    @Inject
    lateinit var sharedPre: SharedPre


    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(ThemeHelper.adjustFontScale(newBase!!,SharedPre.getInstance(newBase)!!.GetFont!!))
    }


    open fun setView(view: View){
        this.actiVityView=view
    }
    fun loading(value:Boolean){
        if(value){
           methods.showLoadingDialog(this)!!.show()
        }else{
            methods.showLoadingDialog(this)!!.dismiss()
        }
    }

    open fun GetApplicationContext(): Context? {
        return applicationContext
    }

    open fun getRxPermissions(): RxPermissions? {
        if (rxPermissions == null) {
            rxPermissions = RxPermissions(this)
        }
        return rxPermissions
    }


    open fun startFragment(fragment: Fragment?, backStackTag: String?, addToBackStack: Boolean) {
      /*  if(manager==null){
            manager = supportFragmentManager
        }*/
        transaction = manager!!.beginTransaction()
        this.addToBackStack = addToBackStack
        transaction!!.addToBackStack(backStackTag)
        transaction!!.replace(R.id.container, fragment!!)
        if (!isFinishing && !isDestroyed) {
            transaction!!.commit()
        }
    }

    public fun startFragment(fragment: Fragment?, addToBackStack: Boolean, backStackTag: String?) {
        this.addToBackStack = addToBackStack
       /* if(manager==null){
            manager = supportFragmentManager
        }*/
        val fragmentPopped = manager!!.popBackStackImmediate(backStackTag, 0)
        if (!fragmentPopped) {
            transaction = manager!!.beginTransaction()
            if (addToBackStack) {
                transaction!!.addToBackStack(backStackTag)
            } else {
                transaction!!.addToBackStack(null)
            }
            transaction!!.replace(R.id.container, fragment!!)
            transaction!!.commit()
        }
    }

    open fun startFragment(
        fragment: Fragment?,
        addToBackStack: Boolean,
        backStackTag: String?,
        wantAnimation: Boolean
    ) {
        /*if(manager==null){
            manager = supportFragmentManager
        }*/
        this.addToBackStack = addToBackStack
        val fragmentPopped = manager!!.popBackStackImmediate(backStackTag, 0)
        if (!fragmentPopped) {
            transaction = manager!!.beginTransaction()
            if (wantAnimation) {
                transaction!!.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, 0, 0)
            }
            if (addToBackStack) {
                transaction!!.addToBackStack(backStackTag)
            } else {
                transaction!!.addToBackStack(null)
            }
            transaction!!.replace(R.id.container, fragment!!)
            transaction!!.commit()
        }
    }

    open fun showCustomAlert(
        msg: String?,
        v: View?,
        button: String?,
        isRetryOptionAvailable: Boolean,
        listener: RetrySnackBarClickListener
    ) {
        if (isRetryOptionAvailable) {
            snackBar = Snackbar.make(v!!, msg!!, Snackbar.LENGTH_LONG)
                .setAction(button) { listener.onClickRetry() }
        } else {
            snackBar = Snackbar.make(v!!, msg!!, Snackbar.LENGTH_LONG)
        }
        snackBar.setActionTextColor(Color.BLUE)
        val snackBarView: View = snackBar.getView()
        val textView = snackBarView.findViewById<TextView>(R.id.snackbar_text)
        textView.setTextColor(Color.WHITE)
        snackBar.show()
    }

    open fun showCustomAlert(msg: String?, v: View?) {
        snackBar = Snackbar.make(v!!, msg!!, Snackbar.LENGTH_LONG)
        snackBar.setActionTextColor(Color.BLUE)
        val snackBarView: View = snackBar.getView()
        val textView = snackBarView.findViewById<TextView>(R.id.snackbar_text)
        textView.setTextColor(Color.WHITE)
        snackBar.show()
    }

    override fun onBackPressed() {
        fragment = getCurrentFragment()
        if(manager==null){
            manager = supportFragmentManager
        }
        if (addToBackStack) {
            if (fragment is HomeFragment) {
                if (doubleBackToExitPressedOnce) {

                    finish()
                    return
                }
                doubleBackToExitPressedOnce = true
                showCustomAlert("Press back again",actiVityView);
                lifecycleScope.launch(Dispatchers.Default) {
                    delay(2000)
                    doubleBackToExitPressedOnce = false
                }
            } else {
                if (manager != null && manager!!.backStackEntryCount > 0) {
                    manager!!.popBackStackImmediate()
                } else {
                    super.onBackPressed()
                }
            }
        } else {
            super.onBackPressed()
        }
    }

    open fun getCurrentFragment(): Fragment? {
        fragment = manager.findFragmentById(R.id.container)
        return fragment
    }

    open fun hideSoftKeyboard(activity: Activity) {
        val imm = activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
    fun setBgData(view: View) {
        val theme = sharedPre.themeData
        when (theme!!) {
            "1" -> {
                view.setBackgroundColor(resources.getColor(R.color.theme1))
                return
            }
            "2" -> {
                view.setBackgroundColor(resources.getColor(R.color.theme2))
                return
            }
            "3" -> {
                view.setBackgroundColor(resources.getColor(R.color.theme3))
                return
            }
            "4" -> {
                view.setBackgroundColor(resources.getColor(R.color.theme4))
                return
            }
        }
    }

    fun setIconTintColor(icon: ImageView) {
        val theme = sharedPre.themeData
        when (theme!!) {
            "1" -> {
                icon.setColorFilter(resources.getColor(R.color.pink))
                return
            }
            "2" -> {
                icon.setColorFilter(resources.getColor(R.color.yellow))
                return
            }
            "3" -> {
                icon.setColorFilter(resources.getColor(R.color.green))
                return
            }
            "4" -> {
                icon.setColorFilter(resources.getColor(R.color.blue))
                return
            }
        }
    }


    fun setViewTextColor(textView: TextView) {
        val theme = sharedPre.themeData
        when (theme!!) {
            "1" -> {
                textView.setTextColor(resources.getColor(R.color.pink))
                return
            }
            "2" -> {
                textView.setTextColor(resources.getColor(R.color.yellow))
                return
            }
            "3" -> {
                textView.setTextColor(resources.getColor(R.color.green_theam))
                return
            }
            "4" -> {
                textView.setTextColor(resources.getColor(R.color.blue))
                return
            }
        }
    }

}