package com.araba.arabacustomer.models

import java.io.Serializable

/**
 * Created by rajneeshkumar on 11/1/17.
 */
data class CountryModel(var country_name: String, var isd: String) : Serializable

data class OtpModel(var OTP: String, var device_token: String) : Serializable
data class CategoryModel(var id: String, var parent_id: String, var name: String, var is_active: String,
                         var position: String, var level: String, var color: String,
                         var description: String, var product_count: String, var sub_category: ArrayList<CategoryModel>, var isSelected: Boolean = true) : Serializable

data class CategoryListParentModel(var categoryList: ArrayList<CategoryModel>) : Serializable
