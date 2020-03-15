package `in`.indilabz.sss_shopkeeper.activity

import `in`.indilabz.sss_shopkeeper.INDIMaster
import `in`.indilabz.sss_shopkeeper.R
import `in`.indilabz.sss_shopkeeper.databinding.ForgotPasswordBinding
import `in`.indilabz.sss_shopkeeper.response.UpdateResponse
import `in`.indilabz.sss_shopkeeper.rest.RetrofitInstance
import `in`.indilabz.sss_shopkeeper.utils.Toaster
import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import dmax.dialog.SpotsDialog

class ForgotPassword : AppCompatActivity() {

    private lateinit var binding : ForgotPasswordBinding
    private lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.forgot_password)

        dialog = SpotsDialog.Builder().setContext(this).build()
        binding.changePassword.setOnClickListener {
            if(binding.mobile.text.toString().length != 10 ){
                Toaster.longt("Enter valid Mobile No.")
                return@setOnClickListener
            }
            if (binding.newPassword.text.toString().length <= 4){
                Toaster.longt("Password must be greater than 4 digit")
                return@setOnClickListener
            }
            changePassword()
        }

    }

    private fun changePassword() {

        dialog.setTitle("Please wait...")
        dialog.setMessage("Searching user...")
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()

        RetrofitInstance.updateRetrofit(
            INDIMaster.api().forgotPassword(
                binding.mobile.text.toString(),
                binding.newPassword.text.toString()
            )
        ) { _: Int, bool: Boolean, value: UpdateResponse ->

            dialog.dismiss()
            if (bool) {
                Toaster.longt("Password updated successfully")
                finish()
            } else {
                Toaster.longt("Mobile no. is not registered")
            }

        }

    }
}
