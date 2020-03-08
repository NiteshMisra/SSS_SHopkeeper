package `in`.indilabz.sss_shopkeeper.activity

import `in`.indilabz.sss_shopkeeper.R
import `in`.indilabz.sss_shopkeeper.utils.INDIPreferences
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({

            if(INDIPreferences.session()){

                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()

            } else{

                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                finish()
            }

        },3000)

    }
}
