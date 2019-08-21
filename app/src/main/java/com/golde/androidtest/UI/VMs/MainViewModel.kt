package com.golde.androidtest.UI.VMs

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.golde.androidtest.Data.Models.Recipe
import com.golde.androidtest.Data.RecipeAPI
import com.golde.androidtest.Data.Repo.RecipeRepo
import com.golde.androidtest.Data.Repo.UserRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainViewModel(val userRepo: UserRepo, val recipeRepo: RecipeRepo, application: Application) : AndroidViewModel(application) {

    private val job = Job()
    private val coroutineContect : CoroutineContext
            get() = job + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContect)

    val recipes = MutableLiveData<List<Recipe>>()

    fun getRecipes(){
        scope.launch {
            Log.d("Recipes ", recipeRepo.getRecipes().toString())
            recipes.postValue(recipeRepo.getRecipes())
        }
    }

    fun add2Favourites(id : String){
        userRepo.add2Favourites(id)
    }

    fun removeFromFavourites(id : String){
        userRepo.removeFromFavourites(id)
    }

    fun isFavourite(id : String) : Boolean {
        return userRepo.isFavourite(id)
    }
}