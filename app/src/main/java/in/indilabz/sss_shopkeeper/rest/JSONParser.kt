package `in`.indilabz.yorneeds.utils

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by Dell on 07-Mar-18.
 */

object JSONParser {

    private var value: String? = null

    fun getArray(s: String): JSONArray? {

        var array: JSONArray? = null
        try {
            array = JSONArray(s)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return array
    }

    fun getString(s: String, key: String, i: Int): String? {

        var json: JSONObject? = null
        var array: JSONArray? = null
        try {
            array = JSONArray(s)
            json = array.getJSONObject(i)

            value = json!!.getString(key)

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return value
    }

    fun getSingleString(s: String, key: String): String? {

        var json: JSONObject? = null
        var array: JSONArray? = null
        try {
            array = JSONArray(s)
            json = array.getJSONObject(0)

            value = json!!.getString(key)

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return value
    }


    fun getWellFormedName(value: String, max: Int, min: Int): String {

        return if (value.trim { it <= ' ' }.length < max) {

            value
        } else {

            value.trim { it <= ' ' }.substring(0, min) + "..."
        }
    }

    fun getJSONObject(data: String, key: String): String? {

        try {

            val json = JSONObject(data)
            value = json.getString(key)

        } catch (e: JSONException) {

            e.printStackTrace()
        }

        return value
    }


    fun getObject(s: String, key: String): String? {

        var json: JSONObject? = null
        try {
            json = JSONObject(s)

            value = json.getString(key)

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return value
    }
}
