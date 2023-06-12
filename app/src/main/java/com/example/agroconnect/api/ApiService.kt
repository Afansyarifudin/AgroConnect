package com.example.agroconnect.api

import com.example.agroconnect.*
import com.example.agroconnect.datamodel.*
import retrofit2.Response
import retrofit2.http.*


interface ApiService {

    @Headers("Content-Type: application/json; charset=utf-8", "Accept: application/json; charset=utf-8")
    @GET("products")
    suspend fun getProductsML(): Response<List<List<ProductLearningResponse>>>

    @Headers()
    @GET("getScore")
    suspend fun getProductsMLQuery(
        @Query("supplier_data") supplierData: List<String>
    ) : Response<ProductMachineResponse>

    @Headers("Content-Type: application/json; charset=utf-8", "Accept: application/json; charset=utf-8")
    @GET("products/search")
    suspend fun searchProducts(@Query("name") query: String): Response<ProductResponse>

    @Headers("Content-Type: application/json; charset=utf-8", "Accept: application/json; charset=utf-8")
    @GET("products/{id}")
    suspend fun getDetailProducts(
        @Path("id") id: Int?
    ): Response<ProductResponse>

    @Headers("Content-Type: application/json; charset=utf-8", "Accept: application/json; charset=utf-8")
    @GET("categories")
    suspend fun getCategories(): Response<CategoryResponse>

    @Headers("Content-Type: application/json; charset=utf-8", "Accept: application/json; charset=utf-8")
    @GET("demands")
    suspend fun getAllDemands(): Response<DemandResponse>

    @Headers("Content-Type: application/json; charset=utf-8", "Accept: application/json; charset=utf-8")
    @GET("products")
    suspend fun getAllProducts(): Response<ProductResponse>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("users/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("users/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<RegisterResponse>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("products")
    suspend fun postProducts(
        @Header("x-access-token") tokenAuth: String,
        @Body request: ProductRequest
    ): Response<ProductCreateResponse>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("demands")
    suspend fun postDemand(
        @Header("x-access-token") tokenAuth: String,
        @Body request: DemandRequest
    ): Response<DemandCreateResponse>

    @Headers("Content-Type: application/json; charset=utf-8", "Accept: application/json; charset=utf-8")
    @GET("demands/search")
    suspend fun searchDemands(@Query("name") query: String): Response<DemandResponse>

    @GET("products")
    suspend fun getAllProdAgro(): ProductResponse

    @PUT("products/{id}")
    suspend fun updateProdAgro(
        @Path("id") id: Int?,
    ): ProductResponse

    @DELETE("products/{id}")
    suspend fun deleteProdAgro(
        @Path("id") id: Int?,
    ): ProductResponse

    @POST("demands/{id}")
    suspend fun postDemAgro(
        @Path("id") id: Int?,
    ): ProductResponse

    @GET("demands/{id}")
    suspend fun searchDemAgro(
        @Path("id") id: Int?,
    ): ProductResponse

    @PUT("demands/{id}")
    suspend fun updateDemAgro(
        @Path("id") id: Int?,
    ): ProductResponse

    @DELETE("demands/{id}")
    suspend fun deleteDemAgro(
        @Path("id") id: Int?,
    ): ProductResponse

    @POST("users/register/{id}")
    suspend fun postRegisterUserAgro(
        @Path("id") id: Int?,
    ): ProductResponse




}
