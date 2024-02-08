package com.app.assignment.data.api.repository

import androidx.lifecycle.MutableLiveData
import com.app.assignment.data.api.ProductApi
import com.app.assignment.data.api.models.ApiResponse
import com.app.assignment.utils.ApiStateHandler
import com.app.assignment.utils.ParserAndHelper
import kotlinx.coroutines.flow.MutableStateFlow
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class ProductRepository @Inject constructor(private val api: ProductApi) {
    private val _apiResponse = MutableStateFlow<ApiStateHandler<ApiResponse>?>(null)
    val apiResponse = _apiResponse

    private val _lastRefreshTime = MutableStateFlow<String>("")
    val lastRefreshTime = _lastRefreshTime

    suspend fun getProducts() {
        _apiResponse.value = ApiStateHandler.Loading()
        val response = safeCall { api.getProduct() }
        _apiResponse.value = response
    }

    suspend fun <T> safeCall(
        res: suspend () -> Response<T>
    ): ApiStateHandler<T> {
        return try {
            val result = res()
            if (result.isSuccessful && result.body() != null) {
                lastRefreshTime.value = ParserAndHelper.getFormatedTime()
                ApiStateHandler.Success(result.body())
            } else if (result.errorBody() != null) {
                val jsonObject = JSONObject(result.errorBody()!!.charStream().readText())
                ApiStateHandler.Failure(jsonObject.getString("message"))
            } else {
                ApiStateHandler.Failure("Something went wrong!")
            }
        } catch (e: Exception) {
            ApiStateHandler.Failure("Something went wrong!")
        } as ApiStateHandler<T>
    }

}