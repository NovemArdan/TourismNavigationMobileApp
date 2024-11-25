package com.example.jelajah1.videoModel

import android.util.Log
import com.example.jelajah1.pojo.Meal
import com.example.jelajah1.pojo.MealList
import com.example.jelajah1.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

//import androidx.lifecycle.ViewModel
//import android.arch.lifecycle.ViewModel


class HomeViewModel : ViewModel() {
    private var randomMealLiveData = MutableLiveData<Meal>()

    fun getRandomMeal(){
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                Log.d("HomeFragment", "Response received")
                if (response.isSuccessful && response.body() != null) {
                    val randomMeal: Meal = response.body()!!.meals[0]
                    randomMealLiveData.value=randomMeal
                    //Log.d("TEST", "meal id ${randomMeal.idMeal} name ${randomMeal.strMeal}")

                } else {
                    Log.d("HomeFragment", "Response was not successful")
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("HomeFragment", "Error: ${t.message}")
            }
        })
    }

    fun observeRandomMealLiveData(): LiveData<Meal> {
        return randomMealLiveData
    }

}