package `in`.indilabz.sss_shopkeeper.activity

import `in`.indilabz.sss_shopkeeper.INDIMaster
import `in`.indilabz.sss_shopkeeper.R
import `in`.indilabz.sss_shopkeeper.databinding.ActivityLoginBinding
import `in`.indilabz.sss_shopkeeper.model.Shop
import `in`.indilabz.sss_shopkeeper.response.LoginResponse
import `in`.indilabz.sss_shopkeeper.utils.Toaster
import `in`.indilabz.sss_shopkeeper.rest.RetrofitInstance
import `in`.indilabz.sss_shopkeeper.utils.INDIPreferences
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)


        findViewById<Button>(R.id.submit).setOnClickListener {

            if(binding.email.editText!!.text.length>3){

                if(binding.password.editText!!.text.length>4){

                    RetrofitInstance.getLoginRetrofit(
                        INDIMaster.api().login(
                            binding.email.editText!!.text.toString(),
                            binding.password.editText!!.text.toString(),
                            "app",
                            "shop"
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

        if(bool){

            try{

                if(value.loginStatus){

                    val shopResponse = Shop(value.userId,value.userName,value.name,value.userImage,value.uniqueId,value.category,value.shopPId,value.discount,null,value.currentAddress,value.loginStatus)
                    if((shopResponse.shop_p_id.toInt())>0){

                        INDIPreferences.shop(shopResponse)
                        INDIPreferences.session(true)
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                    else{
                        INDIPreferences.session(false)
                        Toaster.longt("Invalid login!2")
                    }
                }
                else{
                    INDIPreferences.session(false)
                    Toaster.longt(value.error)
                }

            }catch (e: Exception){

                INDIPreferences.session(false)
                Toaster.longt(e.message!!)
            }
        }
        else{

            if (value.error!= null){
                Toaster.longt(value.error)
            }
        }
    }
}
