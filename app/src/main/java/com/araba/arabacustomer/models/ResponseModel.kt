package com.araba.arabacustomer.models

import java.io.Serializable

/**
 * Created by rajneeshkumar on 11/1/17.
 */

data class ComonResponseModel(var status: Int, var message: String) : Serializable

data class CountryResponseModel(var status: Int, var message: String, var data: CountryModel) : Serializable
data class GetOtpResponseModel(var status: Int, var message: String, var data: OtpModel) : Serializable
data class SendOtpResponseModel(var status: Int, var message: String, var data: OtpModel) : Serializable
data class CategoryResponseModel(var status: Int, var message: String, var data: CategoryListParentModel) : Serializable
