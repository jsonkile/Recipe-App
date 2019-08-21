package com.golde.androidtest.Data

import com.golde.androidtest.Data.Models.Recipe
import retrofit2.http.GET

interface RecipeAPI {
    @GET("/Nsikaktopdown/Recipe/master/Recipe.json")
    suspend fun getRecipes() : List<Recipe>
}