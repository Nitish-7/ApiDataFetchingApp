package com.app.assignment.data.api.models

data class ApiResponse(
    val limit: Int,
    val products: List<Product>,
    val skip: Int,
    val total: Int
)