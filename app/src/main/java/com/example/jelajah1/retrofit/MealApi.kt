package com.example.jelajah1.retrofit

import com.example.jelajah1.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET

interface MealApi {
    @GET("random.php")
    fun getRandomMeal(): Call<MealList>
}