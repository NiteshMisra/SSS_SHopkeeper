package `in`.indilabz.sss_shopkeeper.rest

import `in`.indilabz.sss_shopkeeper.response.LoginResponse
import `in`.indilabz.sss_shopkeeper.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by mjdem on 06-05-2018.
 */

interface API {

    @FormUrlEncoded
    @POST("api/api/shop")
    fun register(
        @Field("contact_number") contact_number: String,
        @Field("full-name") full_name: String,
        @Field("category") category: String,
        @Field("email") email: String,
        @Field("current_address") current_address: String,
        @Field("password") password: String,
        @Field("discount") discount: Int
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("admin")
    fun login(
        @Field("username") contact_number: String,
        @Field("password") password: String,
        @Field("from") from: String,
        @Field("user_type") user_type: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("api/api/discountupdate")
    fun updateAmount(
        @Field("shop_id") shop_id: String,
        @Field("discount") discount: String
    ): Call<String>

    @FormUrlEncoded
    @POST("api/api/discountlog")
    fun discountLog(
        @Field("student_id") student_id: String,
        @Field("shop_id") shop_id: String,
        @Field("amount") amount: String,
        @Field("discount") discount: Int,
        @Field("total_discount") total_discount: Int
    ): Call<String>

    @GET("api/api/gettotaldiscount/{id}")
    fun totalDiscount(
        @Path("id") shop_id: String
    ): Call<String>

    @GET("api/api/shop/{id}")
    fun profile(
        @Path("id") contact_number: String
    ): Call<String>

    @FormUrlEncoded
    @POST("api/api/shopupdate")
    fun update(
        @Field("id") id: String,
        @Field("name") fullName: String,
        @Field("contact_number") phone: String,
        @Field("category") course: String,
        @Field("current_address") address: String
    ): Call<String>
}
