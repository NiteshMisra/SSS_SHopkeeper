package `in`.indilabz.sss_shopkeeper.rest

import `in`.indilabz.review_application.rest.ModelAPIError
import `in`.indilabz.sss_shopkeeper.INDIMaster
import `in`.indilabz.sss_shopkeeper.activity.SplashActivity
import `in`.indilabz.sss_shopkeeper.response.CategoryResponse
import `in`.indilabz.sss_shopkeeper.response.LoginResponse
import `in`.indilabz.sss_shopkeeper.response.RegisterResponse
import `in`.indilabz.sss_shopkeeper.response.UpdateResponse
import `in`.indilabz.sss_shopkeeper.utils.Constants
import `in`.indilabz.sss_shopkeeper.utils.INDIPreferences
import android.content.Intent
import android.os.AsyncTask
import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitInstance : Constants {

    private class RegisterRetrofitAPI (private val retrofitListener: (Int, Boolean, RegisterResponse) -> Unit?, calls: Call<RegisterResponse>?) : AsyncTask<String, String, String>() {

        init {
            registerCall = calls
        }

        override fun doInBackground(vararg params: String?): String? {

            registerCall!!.clone().enqueue(object : Callback<RegisterResponse> {

                override fun onResponse(call: Call<RegisterResponse>?, response: Response<RegisterResponse>?) {

                    if (response!!.isSuccessful) {

                        try {

                            //Log.d("TAG_RETROFIT_RESULT", response.body()!!)

                            val registerResponse = response.body()!!
                            if (registerResponse.success){
                                retrofitListener.invoke(response.code(), true,registerResponse)
                            }else{
                                retrofitListener.invoke(response.code(), false, registerResponse)
                            }
                        } catch (e: Exception) {

                            Log.d("TAG_RETROFIT_ERROR", e.toString())

                            retrofitListener.invoke(response.code(), false, response.body()!!)
                        }

                    } else {

                        try {
                            Log.d("TAG_REAL_ERROR", response.errorBody()!!.string())
                        } catch (e: Exception) {

                            Log.d("TAG_REAL_ERROR_EX", e.message!!)
                        }

                        try {

                            //Toaster.longToast(response.errorBody()!!.string())

                            val apiError = INDIMaster.gson.fromJson(response.errorBody()!!.string(), ModelAPIError::class.java)

                            if (apiError.error == "AUTH_ERROR") {

                                if (INDIPreferences.preferenceEditor().clear().commit()) {

                                    INDIMaster.applicationContext().startActivity(Intent(INDIMaster.applicationContext(), SplashActivity::class.java))
                                    INDIPreferences.session(false)
                                    INDIPreferences.backpress(false)
                                }

                            } else {

                                retrofitListener.invoke(response.code(), false, RegisterResponse(false,apiError.error,null))
                            }
                        } catch (e: Exception) {

                            /// Log.d("TAG_EXCEPTION_ERROR", e.toString())

                            try{
                                retrofitListener.invoke(response.code(), false, RegisterResponse(false,e.message,null))
                            }
                            catch (e : Exception){
                                e.printStackTrace()
                            }
                        }

                    }

                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {

                    //Log.d("TAG_RETROFIT_THROW", t.message)
                    try{
                        retrofitListener.invoke(404, false, RegisterResponse(false,"Please check your internet connection",null))
                    }catch (e : Exception){
                        e.printStackTrace()
                    }
                    //Toaster.LongToast("Unable to connect to internet");
                }
            })

            return null
        }


        override fun onPostExecute(result: String?) {

        }

        override fun onPreExecute() {

        }

    }

    private class CategoryRetrofitAPI (private val retrofitListener: (Int, Boolean, CategoryResponse) -> Unit?, calls: Call<CategoryResponse>?) : AsyncTask<String, String, String>() {

        init {
            categoryCall = calls
        }

        override fun doInBackground(vararg params: String?): String? {

            categoryCall!!.clone().enqueue(object : Callback<CategoryResponse> {

                override fun onResponse(call: Call<CategoryResponse>?, response: Response<CategoryResponse>?) {

                    if (response!!.isSuccessful) {

                        try {

                            //Log.d("TAG_RETROFIT_RESULT", response.body()!!)

                            val categoryResponse = response.body()!!
                            if (categoryResponse.success){
                                retrofitListener.invoke(response.code(), true,categoryResponse)
                            }else{
                                retrofitListener.invoke(response.code(), false, categoryResponse)
                            }
                        } catch (e: Exception) {

                            Log.d("TAG_RETROFIT_ERROR", e.toString())

                            retrofitListener.invoke(response.code(), false, response.body()!!)
                        }

                    } else {

                        try {
                            Log.d("TAG_REAL_ERROR", response.errorBody()!!.string())
                        } catch (e: Exception) {

                            Log.d("TAG_REAL_ERROR_EX", e.message!!)
                        }

                        try {

                            //Toaster.longToast(response.errorBody()!!.string())

                            val apiError = INDIMaster.gson.fromJson(response.errorBody()!!.string(), ModelAPIError::class.java)

                            if (apiError.error == "AUTH_ERROR") {

                                if (INDIPreferences.preferenceEditor().clear().commit()) {

                                    INDIMaster.applicationContext().startActivity(Intent(INDIMaster.applicationContext(), SplashActivity::class.java))
                                    INDIPreferences.session(false)
                                    INDIPreferences.backpress(false)
                                }

                            } else {

                                retrofitListener.invoke(response.code(), false,
                                    CategoryResponse(false,apiError.error,null)
                                )
                            }
                        } catch (e: Exception) {

                            /// Log.d("TAG_EXCEPTION_ERROR", e.toString())

                            try{
                                retrofitListener.invoke(response.code(), false, CategoryResponse(false,e.message,null))
                            }
                            catch (e : Exception){
                                e.printStackTrace()
                            }
                        }

                    }

                }

                override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {

                    //Log.d("TAG_RETROFIT_THROW", t.message)
                    try{
                        retrofitListener.invoke(404, false, CategoryResponse(false,"Please check your internet connection",null))
                    }catch (e : Exception){
                        e.printStackTrace()
                    }
                    //Toaster.LongToast("Unable to connect to internet");
                }
            })

            return null
        }


        override fun onPostExecute(result: String?) {

        }

        override fun onPreExecute() {

        }

    }

    private class LoginRetrofitAPI (private val retrofitListener: (Boolean, LoginResponse) -> Unit?, calls: Call<LoginResponse>?) : AsyncTask<String, String, String>() {

        init {
            loginCall = calls
        }

        override fun doInBackground(vararg params: String?): String? {

            loginCall!!.clone().enqueue(object : Callback<LoginResponse> {

                override fun onResponse(call: Call<LoginResponse>?, response: Response<LoginResponse>?) {

                    if (response!!.isSuccessful) {

                        try {

                            val loginResponse = response.body()!!

                            if (loginResponse.success){
                                retrofitListener.invoke(true, loginResponse)
                            }else{

                                retrofitListener.invoke(false, loginResponse)
                            }

                        } catch (e: Exception) {

                            retrofitListener.invoke(false, response.body()!!)
                        }

                    } else {

                        try{
                            retrofitListener.invoke(false, INDIMaster.gson.fromJson(response.errorBody()!!.string(), LoginResponse::class.java))
                        }
                        catch (e : Exception){

                            retrofitListener.invoke(false, LoginResponse(false,"Error while getting data!", "",null))
                            e.printStackTrace()
                        }

                    }

                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {

                    //Log.d("TAG_RETROFIT_THROW", t.message)
                    try{

                        val loginResponse = LoginResponse(false,t.message,"",null)
                        retrofitListener.invoke(false, loginResponse)

                    }catch (e : Exception){
                        e.printStackTrace()
                    }
                    //Toaster.LongToast("Unable to connect to internet");
                }
            })

            return null
        }


        override fun onPostExecute(result: String?) {

        }

        override fun onPreExecute() {

        }

    }

    private class OtpRetrofitAPI (private val retrofitListener: (Int, Boolean, UpdateResponse) -> Unit?, calls: Call<UpdateResponse>?) : AsyncTask<String, String, String>() {

        init {
            updateCall = calls
        }

        override fun doInBackground(vararg params: String?): String? {

            updateCall!!.clone().enqueue(object : Callback<UpdateResponse> {

                override fun onResponse(call: Call<UpdateResponse>?, response: Response<UpdateResponse>?) {

                    if (response!!.isSuccessful) {

                        try {

                            //Log.d("TAG_RETROFIT_RESULT", response.body()!!)

                            val loginResponse = response.body()!!

                            if (loginResponse.success){
                                retrofitListener.invoke(response.code(), true, loginResponse)
                            }else{
                                retrofitListener.invoke(response.code(), false, loginResponse)
                            }
                        } catch (e: Exception) {

                            Log.d("TAG_RETROFIT_ERROR", e.toString())
                            retrofitListener.invoke(response.code(), false, response.body()!!)
                        }

                    } else {

                        Log.e("login",response.body()!!.toString())

                        try {
                            Log.d("TAG_REAL_ERROR", response.errorBody()!!.string())
                        } catch (e: Exception) {

                            Log.d("TAG_REAL_ERROR_EX", e.message!!)
                        }

                        try {

                            //Toaster.longToast(response.errorBody()!!.string())

                            val apiError = INDIMaster.gson.fromJson(response.errorBody()!!.string(), ModelAPIError::class.java)

                            if (apiError.error == "AUTH_ERROR") {

                                if (INDIPreferences.preferenceEditor().clear().commit()) {

                                    INDIMaster.applicationContext().startActivity(Intent(INDIMaster.applicationContext(), SplashActivity::class.java))
                                    INDIPreferences.session(false)
                                    INDIPreferences.backpress(false)
                                }

                            } else {

                                retrofitListener.invoke(response.code(), false, response.body()!!)
                            }
                        } catch (e: Exception) {

                            /// Log.d("TAG_EXCEPTION_ERROR", e.toString())

                            try{
                                retrofitListener.invoke(response.code(), false, response.body()!!)
                            }
                            catch (e : Exception){
                                e.printStackTrace()
                            }
                        }

                    }

                }

                override fun onFailure(call: Call<UpdateResponse>, t: Throwable) {

                    //Log.d("TAG_RETROFIT_THROW", t.message)
                    try{
                        val updateResponse = UpdateResponse(false,"",t.message,null)
                        retrofitListener.invoke(404, false, updateResponse)

                    }catch (e : Exception){
                        e.printStackTrace()
                    }
                    //Toaster.LongToast("Unable to connect to internet");
                }
            })

            return null
        }


        override fun onPostExecute(result: String?) {

        }

        override fun onPreExecute() {

        }

    }

    private class AvailedRetrofitAPI (private val retrofitListener: (Int, Boolean, UpdateResponse) -> Unit?, calls: Call<UpdateResponse>?) : AsyncTask<String, String, String>() {

        init {
            updateCall = calls
        }

        override fun doInBackground(vararg params: String?): String? {

            updateCall!!.clone().enqueue(object : Callback<UpdateResponse> {

                override fun onResponse(call: Call<UpdateResponse>?, response: Response<UpdateResponse>?) {

                    if (response!!.isSuccessful) {

                        try {

                            //Log.d("TAG_RETROFIT_RESULT", response.body()!!)

                            val loginResponse = response.body()!!

                            if (loginResponse.success){
                                retrofitListener.invoke(response.code(), true, loginResponse)
                            }else{
                                retrofitListener.invoke(response.code(), false, loginResponse)
                            }
                        } catch (e: Exception) {

                            Log.d("TAG_RETROFIT_ERROR", e.toString())
                            retrofitListener.invoke(response.code(), false, response.body()!!)
                        }

                    } else {

                        Log.e("login",response.body()!!.toString())

                        try {
                            Log.d("TAG_REAL_ERROR", response.errorBody()!!.string())
                        } catch (e: Exception) {

                            Log.d("TAG_REAL_ERROR_EX", e.message!!)
                        }

                        try {

                            //Toaster.longToast(response.errorBody()!!.string())

                            val apiError = INDIMaster.gson.fromJson(response.errorBody()!!.string(), ModelAPIError::class.java)

                            if (apiError.error == "AUTH_ERROR") {

                                if (INDIPreferences.preferenceEditor().clear().commit()) {

                                    INDIMaster.applicationContext().startActivity(Intent(INDIMaster.applicationContext(), SplashActivity::class.java))
                                    INDIPreferences.session(false)
                                    INDIPreferences.backpress(false)
                                }

                            } else {

                                retrofitListener.invoke(response.code(), false, response.body()!!)
                            }
                        } catch (e: Exception) {

                            /// Log.d("TAG_EXCEPTION_ERROR", e.toString())

                            try{
                                retrofitListener.invoke(response.code(), false, response.body()!!)
                            }
                            catch (e : Exception){
                                e.printStackTrace()
                            }
                        }

                    }

                }

                override fun onFailure(call: Call<UpdateResponse>, t: Throwable) {

                    //Log.d("TAG_RETROFIT_THROW", t.message)
                    try{
                        val updateResponse = UpdateResponse(false,t.message,"",null)
                        retrofitListener.invoke(404, false, updateResponse)

                    }catch (e : Exception){
                        e.printStackTrace()
                    }
                    //Toaster.LongToast("Unable to connect to internet");
                }
            })

            return null
        }


        override fun onPostExecute(result: String?) {

        }

        override fun onPreExecute() {

        }

    }

    private class UpdateRetrofitAPI (private val retrofitListener: (Int, Boolean, UpdateResponse) -> Unit?, calls: Call<UpdateResponse>?) : AsyncTask<String, String, String>() {

        init {
            updateCall = calls
        }

        override fun doInBackground(vararg params: String?): String? {

            updateCall!!.clone().enqueue(object : Callback<UpdateResponse> {

                override fun onResponse(call: Call<UpdateResponse>?, response: Response<UpdateResponse>?) {

                    if (response!!.isSuccessful) {

                        try {

                            //Log.d("TAG_RETROFIT_RESULT", response.body()!!)

                            val updateResponse = response.body()!!
                            if (updateResponse.success){
                                retrofitListener.invoke(response.code(), true, updateResponse)
                            }else{
                                retrofitListener.invoke(response.code(), false, updateResponse)
                            }
                        } catch (e: Exception) {

                            Log.d("TAG_RETROFIT_ERROR", e.toString())

                            retrofitListener.invoke(response.code(), false, response.body()!!)
                        }

                    } else {

                        try {
                            Log.d("TAG_REAL_ERROR", response.errorBody()!!.string())
                        } catch (e: Exception) {

                            Log.d("TAG_REAL_ERROR_EX", e.message!!)
                        }

                        try {

                            //Toaster.longToast(response.errorBody()!!.string())

                            val apiError = INDIMaster.gson.fromJson(response.errorBody()!!.string(), ModelAPIError::class.java)

                            if (apiError.error == "AUTH_ERROR") {

                                if (INDIPreferences.preferenceEditor().clear().commit()) {

                                    INDIMaster.applicationContext().startActivity(Intent(INDIMaster.applicationContext(), SplashActivity::class.java))
                                    INDIPreferences.session(false)
                                    INDIPreferences.backpress(false)
                                }

                            } else {

                                retrofitListener.invoke(response.code(), false,UpdateResponse(false,apiError.error!!,"",null))
                            }
                        } catch (e: Exception) {

                            /// Log.d("TAG_EXCEPTION_ERROR", e.toString())

                            try{
                                retrofitListener.invoke(response.code(), false,UpdateResponse(false,"Error while fetching data!","",null))
                            }
                            catch (e : Exception){
                                e.printStackTrace()
                            }
                        }

                    }

                }

                override fun onFailure(call: Call<UpdateResponse>, t: Throwable) {

                    //Log.d("TAG_RETROFIT_THROW", t.message)
                    try{
                        retrofitListener.invoke(404, false,UpdateResponse(false,"Please check your internet connection","",null))
                    }catch (e : Exception){
                        e.printStackTrace()
                    }
                    //Toaster.LongToast("Unable to connect to internet");
                }
            })

            return null
        }


        override fun onPostExecute(result: String?) {

        }

        override fun onPreExecute() {

        }

    }


    companion object {

        private var retrofit: Retrofit? = null
        private var client: OkHttpClient? = null
        private var updateCall : Call<UpdateResponse>? = null
        private var registerCall: Call<RegisterResponse>? = null
        private var categoryCall: Call<CategoryResponse>? = null
        private var loginCall: Call<LoginResponse>? = null

        fun instance(): Retrofit {

            client = OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.MINUTES)
                    .readTimeout(10, TimeUnit.MINUTES)
                    .writeTimeout(10, TimeUnit.MINUTES)
                    .addInterceptor { chain ->
                        val newRequest = chain.request().newBuilder()
                                .build()
                        chain.proceed(newRequest)
                    }.build()


            retrofit = Retrofit.Builder()
                    .client(client!!)
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            return retrofit as Retrofit
        }

        fun session(): Retrofit {

            client = OkHttpClient.Builder()
                    .connectTimeout(5, TimeUnit.MINUTES)
                    .readTimeout(5, TimeUnit.MINUTES)
                    .writeTimeout(5, TimeUnit.MINUTES)
                    .retryOnConnectionFailure(true)
                    .addInterceptor { chain ->
                        val newRequest = chain.request().newBuilder()
                                .addHeader("AUTH-TOKEN", INDIPreferences.token()!!)
                                .build()
                        chain.proceed(newRequest)
                    }.build()


            retrofit = Retrofit.Builder()
                    .client(client!!)
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            return retrofit as Retrofit
        }

        fun getAvailedRetrofit(call: Call<UpdateResponse>?, retrofitListener: (Int, Boolean, UpdateResponse) -> Unit?) {
            AvailedRetrofitAPI(retrofitListener, call).execute()
        }

        fun getOTPRetrofit(call: Call<UpdateResponse>?, retrofitListener: (Int, Boolean, UpdateResponse) -> Unit?) {
            OtpRetrofitAPI(retrofitListener, call).execute()
        }

        fun updateRetrofit(call: Call<UpdateResponse>?, retrofitListener: (Int, Boolean, UpdateResponse) -> Unit?) {
            UpdateRetrofitAPI(retrofitListener, call).execute()
        }

        fun getLoginRetrofit(call: Call<LoginResponse>?, retrofitListener: (Boolean, LoginResponse) -> Unit?) {
            LoginRetrofitAPI(retrofitListener, call).execute()
        }

        fun getRegisterRetrofit(call: Call<RegisterResponse>?, retrofitListener: (Int, Boolean, RegisterResponse) -> Unit?) {
            RegisterRetrofitAPI(retrofitListener, call).execute()
        }

        fun getCategoryRetrofit(call: Call<CategoryResponse>?, retrofitListener: (Int, Boolean, CategoryResponse) -> Unit?) {
            CategoryRetrofitAPI(retrofitListener, call).execute()
        }
    }
}
