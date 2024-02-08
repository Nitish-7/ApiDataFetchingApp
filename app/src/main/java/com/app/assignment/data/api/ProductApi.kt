package com.app.assignment.data.api

import com.app.assignment.data.api.models.ApiResponse
import com.app.assignment.data.api.models.Product
import retrofit2.Response
import retrofit2.http.GET

interface ProductApi {
    @GET("/products")
    suspend fun getProduct():Response<ApiResponse>
}