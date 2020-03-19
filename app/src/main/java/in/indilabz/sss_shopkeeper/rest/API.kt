package `in`.indilabz.sss_shopkeeper.rest

import `in`.indilabz.sss_shopkeeper.response.CategoryResponse
import `in`.indilabz.sss_shopkeeper.response.LoginResponse
import `in`.indilabz.sss_shopkeeper.response.RegisterResponse
import `in`.indilabz.sss_shopkeeper.response.UpdateResponse
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by mjdem on 06-05-2018.
 */

interface API {

    @FormUrlEncoded
    @POST("api/v1/shop/")
    fun register(
        @Field("phone") contact_number: String,
        @Field("name") full_name: String,
        @Field("category") category: String,
        @Field("email") email: String,
        @Field("current_address") current_address: String,
        @Field("permanent_address") permanent_address: String,
        @Field("password") password: String,
        @Field("pincode") pincode: String,
        @Field("gender") gender : String,
        @Field("owner_name") ownerName : String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("api/v1/auth/shop")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("api/v1/otp/")
    fun sendOtp(
        @Field("phone") contact_number: String
    ): Call<UpdateResponse>

    @GET("api/v1/category/")
    fun getCategory(): Call<CategoryResponse>

    @FormUrlEncoded
    @POST("api/v1/discounts/availed")
    fun discountLog(
        @Field("id") id: String,
        @Field("student") student_id: String,
        @Field("shop") shop_id : String,
        @Field("amount") amount : String
    ): Call<UpdateResponse>

    @FormUrlEncoded
    @POST("api/v1/shop/update/{id}")
    fun updateName(
        @Path("id") id: String,
        @Field("name") name : String
    ): Call<UpdateResponse>

    @FormUrlEncoded
    @POST("api/v1/shop/update/{id}")
    fun updateOwnerName(
        @Path("id") id: String,
        @Field("owner_name") name : String
    ): Call<UpdateResponse>

    @FormUrlEncoded
    @POST("api/v1/shop/update/{id}")
    fun updateGender(
        @Path("id") id: String,
        @Field("gender") gender : String
    ): Call<UpdateResponse>

    @FormUrlEncoded
    @POST("api/v1/shop/update/{id}")
    fun updatePinCode(
        @Path("id") id: String,
        @Field("pincode") name : String
    ): Call<UpdateResponse>

    @FormUrlEncoded
    @POST("api/v1/shop/forgot")
    fun forgotPassword(
        @Field("phone") phone: String,
        @Field("password") password : String
    ): Call<UpdateResponse>

    @FormUrlEncoded
    @POST("api/v1/shop/update/{id}")
    fun updatePhone(
        @Path("id") id: String,
        @Field("phone") phone : String
    ): Call<UpdateResponse>

    @FormUrlEncoded
    @POST("api/v1/shop/update/{id}")
    fun updateCategory(
        @Path("id") id: String,
        @Field("category") category : Int
    ): Call<UpdateResponse>

    @FormUrlEncoded
    @POST("api/v1/shop/update/{id}")
    fun updateAddress(
        @Path("id") id: String,
        @Field("current_address") address: String
    ): Call<UpdateResponse>
}
