package com.timespace.notesapp.ui.welcomescreen.mobileScreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.databinding.DataBindingUtil
import com.timespace.notesapp.R
import com.timespace.notesapp.base.BaseActivity
import com.timespace.notesapp.databinding.ActivityMobileBinding
import com.timespace.notesapp.firebasemodel.NewUser
import com.timespace.notesapp.ui.welcomescreen.goodMorning.ApprovalActivity
import com.timespace.notesapp.ui.welcomescreen.register.RegisterActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MobileActivity : BaseActivity() {
    private lateinit var binding: ActivityMobileBinding
    private lateinit var context: Context
    private var handler= Handler()
    private var runnable:Runnable?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mobile)
        context = this
        binding.BackButton.setOnClickListener {
            onBackPressed()
        }
        binding.enterNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrEmpty()) {
                    if(runnable!=null){
                        handler.removeCallbacks(runnable!!)
                    }
                    runnable= Runnable {
                        sharedPre.setUserMobile(s.toString())
                        if (s.length == 10) {
                            binding.next.visibility = View.VISIBLE
                            binding.next.setOnClickListener {
                                CoroutineScope(Dispatchers.IO).launch {
                                    var data = firestore.collection("new_users").document(sharedPre.userMobile!!).get().await().toObject(
                                        NewUser::class.java)
                                    if(data!=null){
                                        startActivity(Intent(context, ApprovalActivity::class.java))
                                    }else{
                                        startActivity(Intent(context, RegisterActivity::class.java))
                                    }
                                }

                            }
                        } else {
                            binding.next.setOnClickListener(null)
                            binding.next.visibility = View.GONE
                            showCustomAlert(getString(R.string.error_on_mobile_number),binding.root)
                        }
                    }
                    handler.postDelayed(runnable!!,1000)


                }
            }

        })
    }

    override fun onBackPressed() {
        finish()
    }
}