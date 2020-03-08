package `in`.indilabz.student_union.rest

import `in`.indilabz.yorneeds.utils.JSONParser


object APIHelper {

    fun error(string: String): String {

        val datum =  JSONParser.getObject(string, "error")

        datum?.let {
            return it
        }
        return ""
    }

    fun result(string: String): String{

        val datum =  JSONParser.getJSONObject(string, "result")

        datum?.let {
            return it
        }
        return ""
    }

    fun loginStatus(string: String): Boolean {

        val datum =  JSONParser.getObject(string, "login_status")

        return datum!!.toBoolean()
    }
}