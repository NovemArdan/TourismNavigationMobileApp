package com.example.jelajah1.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.jelajah1.R
import com.example.jelajah1.databinding.ActivityCategoryMealsBinding
import com.example.jelajah1.videoModel.CategoryMealsViewModel

class CategoryMealsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryMealsBinding
    private lateinit var categoryMealsViewModel: CategoryMealsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        categoryMealsViewModel = ViewModelProvider(this)[CategoryMealsViewModel::class.java]

        categoryMealsViewModel.observeMealsLiveData().observe(this, Observer { mealsList ->
            mealsList.forEach{
                Log.d("test",it.strMeal)
            }
        })


    }
}
