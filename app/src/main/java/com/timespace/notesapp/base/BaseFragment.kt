package com.timespace.notesapp.base

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.timespace.notesapp.R
import com.timespace.notesapp.database.prefrence.SharedPre

abstract class BaseFragment : Fragment(){

    private lateinit var snackBar: Snackbar
    private var addToBackStack = false
    private var manager: FragmentManager? = null
    private var transaction: FragmentTransaction? = null
    private var fragment: Fragment? = null
    abstract fun WorkStation();
    var firestore=FirebaseFirestore.getInstance()
    var auth =FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true

    }
    fun getSharedPre():SharedPre{
        return SharedPre.getInstance(requireContext())!!
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
    open fun onBackPressed(){
       requireActivity().onBackPressed()
    }
    open fun startFragment(fragment: Fragment?, backStackTag: String?, addToBackStack: Boolean) {
        if(manager==null){
            manager = requireActivity().supportFragmentManager
        }
        transaction = manager!!.beginTransaction()
        this.addToBackStack = addToBackStack
        transaction!!.addToBackStack(backStackTag)
        transaction!!.replace(R.id.container, fragment!!)
        transaction!!.commit()

    }

    open fun startFragment(fragment: Fragment?, addToBackStack: Boolean, backStackTag: String?) {
        this.addToBackStack = addToBackStack
        if(manager==null){
            manager = requireActivity().supportFragmentManager
        }
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


    fun setBgData(view: View) {
        val theme = getSharedPre().themeData
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
        val theme = getSharedPre().themeData
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
        val theme = getSharedPre().themeData
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