package com.timespace.notesapp.ui.login.otp

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.timespace.notesapp.R
import com.timespace.notesapp.base.BaseActivity
import com.timespace.notesapp.constance.AppConstance
import com.timespace.notesapp.databinding.ActivityOtpValidationBinding
import com.timespace.notesapp.repositories.methods.FirbaseAuthActions
import com.timespace.notesapp.ui.home.HomeActivity
import com.timespace.notesapp.ui.login.LoginViewModel
import java.util.*
import java.util.concurrent.TimeUnit

class OtpActivity : BaseActivity(), FirbaseAuthActions {

    private lateinit var binding: ActivityOtpValidationBinding
    private var mVerificationId: String? = null
    var mobile: String? = null

    var action:FirbaseAuthActions?=null
    private val viewModel: LoginViewModel by viewModels()
    private var mAuth: FirebaseAuth? = null

    private var timer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_otp_validation)
        action=this
        mAuth = FirebaseAuth.getInstance()
        binding.close.setOnClickListener {
            finish()
        }
        viewModel.MobileAuthCheck(this, "+91"+ sharedPre.userMobile.toString(), action!!)
        timer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val v = String.format("%02d", millisUntilFinished / 60000)
                val va = (millisUntilFinished % 60000 / 1000).toInt()
                binding.timer.text =  v + ":"+ String.format("%02d", va)
                binding.resendButton.visibility = View.INVISIBLE
            }

            override fun onFinish() {
                binding.resendButton.visibility = View.VISIBLE
            }
        }
        viewModel.MobileAuthCheck(this, "+91"+ sharedPre.userMobile.toString(), action!!)
        binding.btnValidate.setOnClickListener {
            val code: String = binding.etOtp.text.toString().trim()
            if (code.isEmpty() || code.length < 6) {
                binding.etOtp.error = "Enter valid code"
                binding.etOtp.requestFocus()
                return@setOnClickListener
            }else{
                viewModel.verifyVerificationCode(binding.etOtp.text.toString(), this, action!!)
            }
        }
    }

    override fun VerificationCodeSent() {
       timer!!.start()

    }

    override fun startActivity(uid: String, phoneNumber: String?) {
        timer!!.cancel()
        sharedPre.ScreenType=AppConstance.SCREEN_HOME
       startActivity(Intent(this,HomeActivity::class.java))
        finish()
    }

    override fun Error(message: String) {
        showCustomAlert(message.toString(),binding.root)
        timer!!.cancel()
        showCustomAlert(message,binding.root)
    }

    override fun TimeOut() {
        timer!!.cancel()
    }


}