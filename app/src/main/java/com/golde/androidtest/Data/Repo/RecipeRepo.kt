package com.golde.androidtest.Data.Repo

import com.golde.androidtest.Data.Models.Recipe

interface RecipeRepo {
    suspend fun getRecipes() : List<Recipe>
}