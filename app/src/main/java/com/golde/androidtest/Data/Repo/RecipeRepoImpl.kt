package com.golde.androidtest.Data.Repo

import android.app.Application
import com.golde.androidtest.Data.Models.Recipe
import com.golde.androidtest.Data.RecipeAPI
import org.jetbrains.anko.toast

class RecipeRepoImpl(private val recipeAPI: RecipeAPI, val application: Application) : RecipeRepo {
    override suspend fun getRecipes() : List<Recipe> {
        return try {
            recipeAPI.getRecipes()
        }catch (e : Exception){
            application.toast("Something went wrong")
            emptyList()
        }
    }
}