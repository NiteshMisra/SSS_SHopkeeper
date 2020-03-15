package `in`.indilabz.sss_shopkeeper.model

import com.google.gson.annotations.SerializedName

data class Discount(
    @SerializedName("unique_id")
    val unique_id: String,

    @SerializedName("student_id")
    val student_id: String,

    @SerializedName("shop_id")
    val shop_id : String,

    @SerializedName("discount")
    val discount : String,

    @SerializedName("allowed_discount")
    val allowed_discount : String
)