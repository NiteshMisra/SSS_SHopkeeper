package `in`.indilabz.review_application.rest

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ModelAPIError {

    @SerializedName("message")
    @Expose
    val error: String? = null

}
