package com.example.agroconnect.api

import com.example.agroconnect.*
import com.example.agroconnect.datamodel.LoginRequest
import com.example.agroconnect.datamodel.LoginResponse
import com.example.agroconnect.datamodel.RegisterRequest
import com.example.agroconnect.datamodel.CategoryResponse
import com.example.agroconnect.datamodel.ProductResponse
import com.example.agroconnect.datamodel.RegisterResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
//    @FormUrlEncoded
//    @Headers("Authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoxLCJlbWFpbCI6ImFmYW5AZ21haWwuY29tIiwiaWF0IjoxNjg1MzgxNjczLCJleHAiOjE2ODUzODg4NzN9.YSsICl6Bo_eE-75WE96TRaymkHj9sC7tG5dsu1bp4C0")
//    @POST("products")
//    fun postProdAgro(
//        @Field("name") name: String,
//        @Field("amount") amount: Int,
//        @Field("location") location: String
//    ): AgroResponse

    @GET("products/search")
    suspend fun searchProducts(@Query("name") query: String): Response<ProductResponse>

    @GET("categories")
    suspend fun getCategories(): Response<CategoryResponse>

    @Headers("Content-Type: application/json")
    @POST("users/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @Headers("Content-Type: application/json")
    @POST("users/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<RegisterResponse>


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




//    @JvmSuppressWildcards
//    @GET("users")
//    suspend suspend fun getUserGithub(
//        @Header("Authorization")
//        authorization: String = "token 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"
//    ): MutableList<GithubData.Item>
//
//    @JvmSuppressWildcards
//    @GET("users/{username}")
//    suspend suspend fun getDetailUserGithub(
//        @Path("username") username: String,
//        @Header("Authorization")
//        authorization: String = "token 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"
//    ): DetailGithubData
//
//    @JvmSuppressWildcards
//    @GET("/users/{username}/followers")
//    suspend fun getFollowersUserGithub(
//        @Path("username") username: String,
//        @Header("Authorization")
//        authorization: String = "token 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"
//    ): MutableList<GithubData.Item>
//
//    @JvmSuppressWildcards
//    @GET("/users/{username}/following")
//    suspend fun getFollowingUserGithub(
//        @Path("username") username: String,
//        @Header("Authorization")
//        authorization: String = "token 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"
//    ): MutableList<GithubData.Item>
}
