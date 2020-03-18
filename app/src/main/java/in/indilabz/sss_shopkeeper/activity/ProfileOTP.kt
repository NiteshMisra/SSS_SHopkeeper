package `in`.indilabz.sss_shopkeeper.activity

import `in`.aabhasjindal.otptextview.OTPListener
import `in`.aabhasjindal.otptextview.OtpTextView
import `in`.indilabz.sss_shopkeeper.INDIMaster
import `in`.indilabz.sss_shopkeeper.R
import `in`.indilabz.sss_shopkeeper.model.Shop
import `in`.indilabz.sss_shopkeeper.response.UpdateResponse
import `in`.indilabz.sss_shopkeeper.rest.RetrofitInstance
import `in`.indilabz.sss_shopkeeper.utils.INDIPreferences
import `in`.indilabz.sss_shopkeeper.utils.Toaster
import android.app.AlertDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dmax.dialog.SpotsDialog

class ProfileOTP : AppCompatActivity() {

    private lateinit var serverOtp : String
    private lateinit var otpView : OtpTextView
    private lateinit var dialog: AlertDialog
    private lateinit var mobileNo : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_otp)
        otpView = findViewById(R.id.profile_otp_view)
        otpView.otpListener = listener
        dialog = SpotsDialog.Builder().setContext(this).build()
        serverOtp = intent.extras!!.getString("Otp")!!
        mobileNo = intent.getStringExtra("Mobile")!!
        Toaster.longt(serverOtp)
    }

    private val listener = object : OTPListener {

        override fun onOTPComplete(otp: String) {

            if(otp == serverOtp){

                dialog.setTitle("Please wait...")
                dialog.setMessage("Updating Mobile No...")
                dialog.setCanceledOnTouchOutside(false)
                dialog.show()

                RetrofitInstance.updateRetrofit(
                    INDIMaster.api().updatePhone(
                        INDIPreferences.shop()!!.id.toString(),
                       mobileNo
                    )
                ) { _:Int, bool:Boolean, value: UpdateResponse ->

                    dialog.dismiss()

                    if(bool && value.success){
                        Toaster.longt("Profile updated successfully")
                        val shop : Shop = INDIPreferences.shop()!!
                        shop.phone = mobileNo
                        INDIPreferences.shop(shop)
                        finish()

                    } else{
                        Toaster.longt("Failed to update profile")
                    }
                }

            }else{
                Toaster.longt("Invalid Otp")
            }
        }

        override fun onInteractionListener() {

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (dialog.isShowing){
            dialog.dismiss()
        }
    }
}
