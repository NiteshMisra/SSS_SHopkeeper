package `in`.indilabz.sss_shopkeeper.utils

import `in`.indilabz.sss_shopkeeper.INDIMaster
import `in`.indilabz.sss_shopkeeper.model.Shop
import android.app.Activity
import android.content.SharedPreferences
import com.google.gson.Gson


class INDIPreferences : Constants {
    companion object {

        private val preferences: SharedPreferences
            get() = INDIMaster.applicationContext()!!.getSharedPreferences(Constants.APP_NAME, Activity.MODE_PRIVATE)

        fun preferenceEditor(): SharedPreferences.Editor {
            return INDIMaster.applicationContext()!!.getSharedPreferences(Constants.APP_NAME, Activity.MODE_PRIVATE).edit()
        }

        //// Add following lines to store and retrieve String

        fun session(value: Boolean) {
            val editor = preferences.edit()
            editor.putBoolean("APP_SESSION", value)
            editor.commit()
        }

        fun session(): Boolean {
            val mSharedPreferences = preferences
            return mSharedPreferences.getBoolean("APP_SESSION", false)
        }

        fun otp(value: String) {
            val editor = preferences.edit()
            editor.putString("USER_OTP", value)
            editor.commit()
        }

        fun otp(): String? {
            val mSharedPreferences = preferences
            return mSharedPreferences.getString("USER_OTP", "")
        }

        fun token(): String? {
            val mSharedPreferences = preferences
            return mSharedPreferences.getString("USER_TOKEN", "bd53e7b8-2c5f-11ea-babf-44a84246acd5")
        }

        fun token(value: String) {
            val editor = preferences.edit()
            editor.putString("USER_TOKEN", value)
            editor.commit()
        }

        fun phone(): String? {
            val mSharedPreferences = preferences
            return mSharedPreferences.getString("phone", "")
        }

        fun phone(value: String) {
            val editor = preferences.edit()
            editor.putString("phone", value)
            editor.commit()
        }

        fun backpress(value: Boolean) {
            val editor = preferences.edit()
            editor.putBoolean("BACK_PRESS", value)
            editor.commit()
        }

        fun backpress(): Boolean {
            val mSharedPreferences = preferences
            return mSharedPreferences.getBoolean("BACK_PRESS", false)
        }
        
       ////////////////////////////// USER

        fun shop(questions: Shop) {

            val editor = preferences.edit()
            val gson = Gson()
            val jsonDatum = gson.toJson(questions)
            editor.putString("user", jsonDatum)
            editor.commit()
        }

        fun shop(): Shop? {

            val settings = preferences

            if (settings.contains("user")) {
                val jsonDatum = settings.getString("user", "{}")
                val gson = Gson()
                val datum = gson.fromJson(jsonDatum,
                    Shop::class.java)

               return datum
            } else
                return null
        }


        ////////////////////////////// FRAGMENT NAME
        fun fragmentName(value: String) {
            val editor = preferences.edit()
            editor.putString("FRAGMENT_NAME", value)
            editor.commit()
        }

        fun fragmentName(): String? {
            val mSharedPreferences = preferences
            return mSharedPreferences.getString("FRAGMENT_NAME", "")
        }
        
        fun email(value: String?) {
            val editor = preferences.edit()
            editor.putString("EMAIL", value)
            editor.commit()
        }

        fun email(): String? {
            val mSharedPreferences = preferences
            return mSharedPreferences.getString("EMAIL", "")
        }


        fun totalTestTime(value: String) {
            val editor =  preferences.edit()
            editor.putString("test_time", value)
            editor.commit()
        }

        fun totalTestTime(): String? {
            val mSharedPreferences =  preferences
            return mSharedPreferences.getString("test_time", "")
        }

        fun exam(value: String) {
            val editor =  preferences.edit()
            editor.putString("exam", value)
            editor.commit()
        }

        fun exam(): String? {
            val mSharedPreferences =  preferences
            return mSharedPreferences.getString("exam", "")
        }

        fun logout(): Boolean{

            return preferences.edit().clear().commit()
        }

        fun questionId(value: String) {
            val editor =  preferences.edit()
            editor.putString("question", value)
            editor.commit()
        }

        fun questionId(): String? {
            val mSharedPreferences =  preferences
            return mSharedPreferences.getString("question", "")
        }
    }
}