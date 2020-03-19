package `in`.indilabz.sss_shopkeeper.activity

import `in`.aabhasjindal.otptextview.OTPListener
import `in`.aabhasjindal.otptextview.OtpTextView
import `in`.indilabz.sss_shopkeeper.INDIMaster
import `in`.indilabz.sss_shopkeeper.R
import `in`.indilabz.sss_shopkeeper.model.Register
import `in`.indilabz.sss_shopkeeper.model.Shop
import `in`.indilabz.sss_shopkeeper.response.RegisterResponse
import `in`.indilabz.sss_shopkeeper.rest.RetrofitInstance
import `in`.indilabz.sss_shopkeeper.utils.INDIPreferences
import `in`.indilabz.sss_shopkeeper.utils.Toaster
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.Gson
import dmax.dialog.SpotsDialog

class OTPActivity : AppCompatActivity() {

    private lateinit var serverOtp : String
    private lateinit var otpView : OtpTextView
    private lateinit var registerModal : Register
    private lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)
        otpView = findViewById(R.id.otp_view)
        otpView.otpListener = listener
        dialog = SpotsDialog.Builder().setContext(this).build()
        serverOtp = intent.extras!!.getString("Otp")!!
        val value = intent.getStringExtra("GSON")!!
        registerModal = Gson().fromJson(value, Register::class.java)
        Toaster.longt(serverOtp)
    }

    private val listener = object : OTPListener {

        override fun onOTPComplete(otp: String) {

            if(otp == serverOtp){

                dialog.setTitle("Please wait...")
                dialog.setMessage("Creating new user!!...")
                dialog.setCanceledOnTouchOutside(false)
                dialog.show()

                RetrofitInstance.getRegisterRetrofit(
                    INDIMaster.api().register(
                        registerModal.phone,
                        registerModal.fullName,
                        registerModal.categoryText,
                        registerModal.email,
                        registerModal.currentAddress,
                        registerModal.perAddress,
                        registerModal.password,
                        registerModal.pincode,
                        registerModal.gender,
                        registerModal.ownerName
                    ),
                    register
                )

            }else{
                Toaster.longt("Invalid Otp")
            }
        }

        override fun onInteractionListener() {

        }
    }

    private val register = { _: Int, bool: Boolean, value: RegisterResponse ->

        if (bool) {

            Toast.makeText(this, "Registered successfully", Toast.LENGTH_LONG).show()

            val loginresult = value.result
            dialog.dismiss()
            if (value.success && loginresult != null) {
                val shopResponse = Shop(
                    loginresult.id,
                    loginresult.name,
                    loginresult.email,
                    loginresult.phone,
                    loginresult.password,
                    loginresult.category.toString(),
                    loginresult.currentAddress,
                    "",
                    loginresult.gender,
                    loginresult.pincode,
                    loginresult.ownerName
                )

                INDIPreferences.shop(shopResponse)
                INDIPreferences.session(true)

                val intent = Intent(this@OTPActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)

            }else{
                Toaster.longt("some error occurred")
            }
        } else {
            dialog.dismiss()
            Toast.makeText(this, value.error, Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (dialog.isShowing){
            dialog.dismiss()
        }
    }

}
