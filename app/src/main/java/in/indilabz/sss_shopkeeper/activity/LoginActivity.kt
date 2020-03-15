package `in`.indilabz.sss_shopkeeper.activity

import `in`.indilabz.sss_shopkeeper.INDIMaster
import `in`.indilabz.sss_shopkeeper.R
import `in`.indilabz.sss_shopkeeper.databinding.ActivityLoginBinding
import `in`.indilabz.sss_shopkeeper.model.Shop
import `in`.indilabz.sss_shopkeeper.response.LoginResponse
import `in`.indilabz.sss_shopkeeper.utils.Toaster
import `in`.indilabz.sss_shopkeeper.rest.RetrofitInstance
import `in`.indilabz.sss_shopkeeper.utils.INDIPreferences
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import dmax.dialog.SpotsDialog

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        dialog = SpotsDialog.Builder().setContext(this).build()

        binding.forgotPassword.setOnClickListener {
            startActivity(Intent(this,ForgotPassword::class.java))
        }

        findViewById<Button>(R.id.submit).setOnClickListener {

            if(binding.email.editText!!.text.length>3){

                if(binding.password.editText!!.text.length>4){

                    dialog.show()

                    RetrofitInstance.getLoginRetrofit(
                        INDIMaster.api().login(
                            binding.email.editText!!.text.toString(),
                            binding.password.editText!!.text.toString()
                        ),login)
                }
                else{
                    Toaster.longt("Invalid password!")
                }
            }
            else{
                Toaster.longt("Invalid phone!")
            }

        }

        binding.helpdesk.setOnClickListener {
            startActivity(Intent(this, HelpDesk::class.java))
        }

        findViewById<Button>(R.id.register).setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private val login = {  bool: Boolean, value:LoginResponse ->

        dialog.dismiss()
        if(bool){

            try{

                val loginresult = value.loginResult
                if(value.success && loginresult != null){
                    val shopResponse = Shop(
                        loginresult.id,
                        loginresult.name,
                        loginresult.email,
                        loginresult.phone,
                        loginresult.password,
                        loginresult.category.toString(),
                        loginresult.currentAddress,
                        loginresult.shopImage)

                    INDIPreferences.shop(shopResponse)
                    INDIPreferences.session(true)
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                else{
                    INDIPreferences.session(false)
                    //Toaster.longt(value.message)
                }

            }catch (e: Exception){

                INDIPreferences.session(false)
                //Toaster.longt(e.message!!)
            }
        }
        else{

            if (value.message != null) {
                Toaster.longt(value.message)
            }
        }
    }
}
