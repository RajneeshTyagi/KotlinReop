package com.araba.arabacustomer.network

import com.araba.arabacustomer.models.*
import com.araba.arabacustomer.utilitys.UrlUtility
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by rajneeshkumar on 11/1/17.
 */
interface NetworkApiIInterface {

    @GET("country/isd")
    fun getCountryList(): Observable<CountryResponseModel>

    @POST("customer/sendotp")
    fun getOtp(@Body mobileNo: MobileSendModel): Observable<GetOtpResponseModel>

    @POST("customer/registration")
    fun sendOtp(@Body mobileOtp: MobileOtpSendModel): Observable<SendOtpResponseModel>

    @GET("categories/list")
    fun getCategoryList(@Query("rootCategoryId") rootCategoryId: String): Observable<CategoryResponseModel>

}