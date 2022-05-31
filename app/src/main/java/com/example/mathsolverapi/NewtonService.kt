package com.example.mathsolverapi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface NewtonService {
    // ADD ENCODED = TRUE TO ALL FUNCTIONS
    @GET("simplify/{expression}")
    fun simplify(@Path("expression", encoded = true) expression : String) : Call<JsonInfo>

    @GET("factor/{expression}")
    fun factor(@Path("expression", encoded = true) expression : String) : Call<JsonInfo>

    @GET("derive/{expression}")
    fun derive(@Path("expression", encoded = true) expression : String) : Call<JsonInfo>

    @GET("integrate/{expression}")
    fun integrate(@Path("expression", encoded = true) expression : String) : Call<JsonInfo>

    @GET("zeroes/{expression}")
    fun zeroes(@Path("expression", encoded = true) expression : String) : Call<JsonInfo>

    @GET("tangent/{expression}")
    fun tangentLine(@Path("expression", encoded = true) expression : String) : Call<JsonInfo>

    @GET("area/{expression}")
    fun area(@Path("expression", encoded = true) expression : String) : Call<JsonInfo>

    @GET("cos/{expression}")
    fun cos(@Path("expression", encoded = true) expression : String) : Call<JsonInfo>

    @GET("sin/{expression}")
    fun sin(@Path("expression", encoded = true) expression : String) : Call<JsonInfo>

    @GET("tan/{expression}")
    fun tan(@Path("expression", encoded = true) expression : String) : Call<JsonInfo>

    @GET("arccos/{expression}")
    fun arccos(@Path("expression", encoded = true) expression : String) : Call<JsonInfo>

    @GET("arcsin/{expression}")
    fun arcsin(@Path("expression", encoded = true) expression : String) : Call<JsonInfo>

    @GET("arctan/{expression}")
    fun arctan(@Path("expression", encoded = true) expression : String) : Call<JsonInfo>

    @GET("abs/{expression}")
    fun abs(@Path("expression", encoded = true) expression : String) : Call<JsonInfo>

    @GET("log/{expression}")
    fun log(@Path("expression", encoded = true) expression : String) : Call<JsonInfo>
}
