package `in`.indilabz.sss_shopkeeper


import `in`.indilabz.sss_shopkeeper.rest.API
import `in`.indilabz.sss_shopkeeper.rest.RetrofitInstance
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.request.RequestOptions
import com.google.gson.GsonBuilder
import net.gotev.uploadservice.UploadServiceConfig
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.util.*

class INDIMaster : MultiDexApplication(){

    var context: Context? = null


    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        instance = this@INDIMaster
        //CrashReporter.initialize(this)
        //ACRA.init(this);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        context = this.baseContext
        createNotificationChannel()

        UploadServiceConfig.initialize(
            context = this,
            defaultNotificationChannel = notificationChannelID,
            debug = BuildConfig.DEBUG
        )

    }

    companion object {

        @get:Synchronized
        var instance: INDIMaster? = null
            private set

        fun applicationContext() : INDIMaster {
            return instance!!.applicationContext as INDIMaster
        }

        const val notificationChannelID = "TestChannel"

        private fun createNotificationChannel(){
            if(Build.VERSION.SDK_INT >= 26){
                val channel = NotificationChannel(notificationChannelID,
                    "ShopKeeper App",
                    NotificationManager.IMPORTANCE_LOW
                )
                val manager = applicationContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                manager.createNotificationChannel(channel)
            }
        }

        private val api = RetrofitInstance.instance().create(API::class.java)

        private val session = RetrofitInstance.session().create(API::class.java)

        private var linearLayoutManager: LinearLayoutManager? = null
        private var gridLayoutManager: GridLayoutManager? = null

        private val gsonBuilder = GsonBuilder()
        val gson = gsonBuilder.create()
        private val requestOptions: RequestOptions? = null

        private val otpRandom = Random(System.currentTimeMillis())
        lateinit var otp: String
        //var loading:DialogLoading = DialogLoading()

        fun generateOTP() {

            val generatedToken = StringBuilder()

            try {
                val number = SecureRandom.getInstance("SHA1PRNG")
                // Generate 20 integers 0..20
                for (i in 0..5) {
                    generatedToken.append(number.nextInt(9))
                }

                otp = generatedToken.toString()

            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()

                otp = "886617"
            }
        }

        fun api(): API {
            return api
        }

        fun session(): API {
            return session
        }

        // linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        val verticalLayoutManager: LinearLayoutManager
            get() {

                linearLayoutManager = LinearLayoutManager(
                    applicationContext()
                )

                return linearLayoutManager!!
            }

        val horizontalLayoutManager: LinearLayoutManager
            get() {

                linearLayoutManager = LinearLayoutManager(
                    applicationContext()
                )
                linearLayoutManager!!.orientation = LinearLayoutManager.HORIZONTAL

                return linearLayoutManager!!
            }

        fun getGridLayoutManager(span: Int): GridLayoutManager {

            gridLayoutManager = GridLayoutManager(
                applicationContext(), span)

            return gridLayoutManager!!
        }
    }
}