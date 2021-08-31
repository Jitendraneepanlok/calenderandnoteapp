package com.timespace.notesapp.repositories.methods
/*
* © Copyright Ishant Sharma
* Android Developer
* JAVA/KOTLIN
* +91-7732993378
* ishant.sharma1947@gmail.com
* */

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Build
import android.util.Base64
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.timespace.notesapp.R
import com.timespace.notesapp.database.datastore.DataStoreBase
import com.timespace.notesapp.database.prefrence.SharedPre
import kotlinx.coroutines.*
import java.io.UnsupportedEncodingException
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.inject.Inject


class MethodsRepo @Inject constructor(
    var context: Context,
    var dataStore: DataStoreBase,
    var sharedPre: SharedPre
) {
    private var progressDialog: Dialog? = null
    private lateinit var forceResendingTokenMAin: PhoneAuthProvider.ForceResendingToken
    private var verficationId = "0"
    private  var mAuth: FirebaseAuth= FirebaseAuth.getInstance()
    private lateinit var authAction: FirbaseAuthActions;

    fun MobAuth(activity: Activity, phoneNumber: String, authAction: FirbaseAuthActions) {
        this.authAction = authAction
        GlobalScope.launch(Dispatchers.IO) {
            withTimeout(60L) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    "+" + phoneNumber,
                    60L,
                    TimeUnit.SECONDS,
                    activity,
                    object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                        override fun onCodeSent(
                            s: String,
                            forceResendingToken: PhoneAuthProvider.ForceResendingToken
                        ) {
                            super.onCodeSent(s, forceResendingToken)
                            verficationId = s
                            forceResendingTokenMAin = forceResendingToken
                            authAction.VerificationCodeSent()


                        }

                        override fun onCodeAutoRetrievalTimeOut(s: String) {
                            super.onCodeAutoRetrievalTimeOut(s)
                            authAction.TimeOut()

                        }

                        override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                            signInWithPhoneAuthCredential(phoneAuthCredential, authAction)
                        }

                        override fun onVerificationFailed(s: FirebaseException) {
                            authAction.Error(s.message!!);
                        }
                    })
            }


        }

    }

    fun verifyVerificationCode(activity: Activity, code: String?, authAction: FirbaseAuthActions) {
        //creating the credential
        val credential = PhoneAuthProvider.getCredential(verficationId, code!!)

        //signing the user
        signInWithPhoneAuthCredential(activity, credential, authAction)
    }

    fun signInWithPhoneAuthCredential(
        activity: Activity,
        credential: PhoneAuthCredential,
        authAction: FirbaseAuthActions
    ) {
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    authAction.startActivity(task.result.user!!.uid, task.result.user!!.phoneNumber)
                } else {
                    task.exception
                }

            }
    }

    private fun signInWithPhoneAuthCredential(
        credential: PhoneAuthCredential,
        authAction: FirbaseAuthActions
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            val data = async {
                mAuth.signInWithCredential(credential)
            }
            if (data.await().isSuccessful) {
                sharedPre.setUserId(data.await().result!!.user!!.uid)
            } else if (data.await().isCanceled) {
                authAction.Error(context.getString(R.string.try_again))
            }
        }
    }

    fun fetchCharacterData(): String {
        return "Ishant"
    }

    fun isValidEmail(email: String?): Boolean {
        val EMAIL_PATTERN =
            "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        return Pattern.compile(EMAIL_PATTERN).matcher(email).matches()
    }

    fun isValidPhoneNumber(phone: String?): Boolean {
        val mobilePattern = "[0-9]{10}"
        return Pattern.matches(mobilePattern, phone)
    }

    fun isValidName(name: String?): Boolean {
        val pattern = Pattern.compile("^[a-zA-Z\\s]*$")
        val matcher: Matcher = pattern.matcher(name)
        return matcher.matches()
    }

    fun isValidPassword(password: String?): Boolean {
        val pattern = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20}$")
        val matcher: Matcher = pattern.matcher(password)
        return matcher.matches()
    }

    fun isNetworkConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return cm!!.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }

    fun showLoadingDialog(context: Context?): Dialog? {
        if (progressDialog == null) {
            progressDialog = Dialog(context!!)
            if (progressDialog!!.window != null) {
                val window = progressDialog!!.window
                window!!.setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
                )
                window.setGravity(Gravity.CENTER)
                progressDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
            progressDialog!!.setContentView(R.layout.dialog_progress)
            progressDialog!!.setCancelable(false)
            progressDialog!!.setCanceledOnTouchOutside(false)
        }
        return progressDialog
    }

    fun getFormattedDate(context: Context?, smsTimeInMilis: Long): String? {
        val formatter = SimpleDateFormat("yyyy-MM-dd , h:mm aa")
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = smsTimeInMilis
        return formatter.format(calendar.time)
    }

    fun getFormattedDate(context: Context?, smsTimeInMilis: Long, Format: String?): String? {
        val formatter = SimpleDateFormat(Format)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = smsTimeInMilis
        return formatter.format(calendar.time)
    }

    fun ConverMillsTo(mills: Long, youFormat: String?): String? {
        val date = Date(mills)
        val formatter: DateFormat = SimpleDateFormat(youFormat)
        return formatter.format(date)
    }

    fun getMillsFromDateandTime(dateOrTime: String?, format: String?): Long {
        val sdf = SimpleDateFormat(format)
        var date: Date? = null
        return try {
            date = sdf.parse(dateOrTime)
            date.time
        } catch (e: ParseException) {
            e.printStackTrace()
            0
        }
    }

    fun Base64Encode(text: String): String? {
        var encrpt = ByteArray(0)
        try {
            encrpt = text.toByteArray(charset("UTF-8"))
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
        return Base64.encodeToString(encrpt, Base64.DEFAULT)
    }

    fun Base64Decode(base64: String?): String? {
        val decrypt = Base64.decode(base64, Base64.DEFAULT)
        var text: String? = null
        try {
            text = String(decrypt, charset("UTF-8"))
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
        return text
    }

    fun setBackGround(context: Context?, view: View, @DrawableRes drawable: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(ContextCompat.getDrawable(context!!, drawable))
        } else {
            view.setBackgroundDrawable(ContextCompat.getDrawable(context!!, drawable))
        }
    }

}