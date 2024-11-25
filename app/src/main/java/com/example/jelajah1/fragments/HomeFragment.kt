package com.example.jelajah1.fragments

//import android.arch.lifecycle.ViewModelProvider
//import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.jelajah1.activities.CategoryMealsActivity
import com.example.jelajah1.activities.MealActivity
import com.example.jelajah1.adapters.CategoriesAdapter
import com.example.jelajah1.adapters.MostPopularAdapter
import com.example.jelajah1.databinding.FragmentHomeBinding
import com.example.jelajah1.pojo.MealsByCategory
import com.example.jelajah1.pojo.Meal
import com.example.jelajah1.videoModel.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeMvvm: HomeViewModel
    private lateinit var popularItemsAdapter:MostPopularAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter

    private lateinit var randomMeal: Meal

    companion object {
        const val MEAL_ID = "com.example.jelajah1.fragments.idMeal"
        const val MEAL_NAME = "com.example.jelajah1.fragments.nameMeal"
        const val MEAL_THUMB = "com.example.jelajah1.fragments.thumbMeal"
        const val CATEGORY_NAME = "com.example.jelajah1.fragments.categoryName"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //homeMvvm = ViewModelProviders.of(this).get(HomeViewModel::class.java) // Using ViewModelProviders
        homeMvvm = ViewModelProvider(this)[HomeViewModel::class.java]

        popularItemsAdapter = MostPopularAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        homeMvvm.getRandomMeal()
//        observerRandomMeal()
//        onRandomMealClick()
//
//        homeMvvm.getPopularItems()
//        observePopularItemsLiveData()
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup RecyclerView for popular items
        preparePopularItemsRecyclerView()

        // Load and observe random meal
        homeMvvm.getRandomMeal()
        observerRandomMeal()

        // Load and observe popular items
        homeMvvm.getPopularItems()
        observePopularItemsLiveData()

        homeMvvm.getCategories()
        observeCategoriesLiveData()

        prepareCategoriesRecyclerView()

        onCategoryClick()

        // Click listener for random meal card
        onRandomMealClick()

        onPopularItemClick()
    }

    private fun onCategoryClick() {
        categoriesAdapter.onItemClick = { category ->
            val intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME, category.strCategory)
            startActivity(intent)
        }
    }

    private fun prepareCategoriesRecyclerView() {
        categoriesAdapter = CategoriesAdapter()
        binding.recViewCategories.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }
    }

    private fun observeCategoriesLiveData() {
        homeMvvm.observeCategoriesLiveData().observe(viewLifecycleOwner, Observer { categories ->
            categoriesAdapter.setCategoryList(categories)
//            categories.forEach{category ->
//                Log.d("test ",category.strCategory)
//            }
        })
    }

    private fun onPopularItemClick() {
        popularItemsAdapter.onItemClick = { meal ->
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, meal.idMeal)
            intent.putExtra(MEAL_NAME, meal.strMeal)
            intent.putExtra(MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun preparePopularItemsRecyclerView() {
        binding.recViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)

            adapter=popularItemsAdapter

        }
    }

    private fun observePopularItemsLiveData() {
        homeMvvm.observePopularItemsLiveData().observe(viewLifecycleOwner,
            { mealList -> popularItemsAdapter.setMeals(mealList = mealList as ArrayList<MealsByCategory> )
        })
    }

    private fun onRandomMealClick() {
        binding.randomMealCard.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_NAME, randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB, randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observerRandomMeal() {
        homeMvvm.observeRandomMealLiveData().observe(viewLifecycleOwner, Observer { meal ->
            meal?.let {
                Glide.with(this)
                    .load(it.strMealThumb)
                    .into(binding.imgRandomMeal)

                this.randomMeal = meal
            }
        })
    }
}