package com.golde.androidtest.UI

import android.annotation.TargetApi
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType.*
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.golde.androidtest.Data.Models.Recipe
import com.golde.androidtest.UI.VMs.MainViewModel
import com.golde.androidtest.Util.Stringer
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val vm : MainViewModel by viewModel()
    private val stringer : Stringer = get()
    private var location = 0
    private var recipes = emptyList<Recipe>()
    private val ready = MutableLiveData<Boolean>(false)
    private val dialog by lazy { indeterminateProgressDialog(message = "Please wait a bit…", title = "Fetching recipes") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        makeLoginPage()

        ready.observeForever {
            if(it) {
                dialog.show()
                vm.getRecipes()
            }
        }

        vm.recipes.observeForever {
            dialog.dismiss()
            recipes = it
            makeRecipesView(it)
        }
    }

    private fun makeLoginPage(){
        location = 0
        verticalLayout {
                val layout = this
                textView{ text = "Recipes App - Login"; textSize = 25f }.lparams{ bottomMargin = dip(10) }
                verticalLayout {
                    val email = editText {
                        hint = "recipeking@gmail.com"
                        text = Editable.Factory.getInstance().newEditable("recipeking@gmail.com")
                        textSize = 17f
                        inputType = TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                    }

                    val pass = editText {
                        hint = "recipeking"
                        text = Editable.Factory.getInstance().newEditable("recipeking")
                        textSize = 17f
                        inputType = TYPE_CLASS_TEXT or TYPE_TEXT_VARIATION_PASSWORD
                    }

                    button{ text = "Continue"; onClick { login(email.text.toString(), pass.text.toString(), layout) }; }

                }.lparams { width = matchParent; leftPadding = dip(10); rightPadding = dip(10)}
                gravity = Gravity.CENTER
            }
    }

    private fun login(email : String, pass : String, layout: _LinearLayout){
        if(email == "recipeking@gmail.com" && pass == "recipeking"){
            layout.visibility = View.GONE
            ready.postValue(true)
        }else{
            toast("Incorrect details, Retry.")
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun makeRecipesView(recipes : List<Recipe>){
        location = 1
        scrollView {
            verticalLayout {
                val layout = this
                textView{ text = "Recipes"; textSize = 25f }.lparams{ leftMargin = dip(10); topMargin = dip(10); bottomMargin = dip(10) }
                for(recipe in recipes){
                    verticalLayout {
                        textView{ text = recipe.name; textSize = 17f; }.lparams{ leftMargin = dip(10); topMargin = dip(5); }
                        val pic = imageView().lparams { leftMargin = dip(10); width = matchParent; height = 20 }
                        Picasso.get().load(recipe.image).into(pic)
                        textView{ text = "by ${recipe.user.name}"; textSize = 15f; }.lparams{ leftMargin = dip(10); bottomMargin = dip(5); }
                        background =
                            if(vm.isFavourite(recipe.id)) ColorDrawable(Color.parseColor("#a6a4a4")) else ColorDrawable(Color.parseColor("#e9e9ea"))

                        onClick {
                            makeRecipeView(layout, recipe)
                        }
                    }.lparams{ bottomMargin = dip(15); width = matchParent }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun makeRecipeView(parentLayout : _LinearLayout, recipe : Recipe){
        location = 2
        parentLayout.visibility = View.GONE
        val likeBtnName : String
        val likeText : String
        if(vm.userRepo.isFavourite(recipe.id)) {
            likeBtnName = "Unlike"
            likeText = "You and ${recipe.favorites} other person(s) love this recipe."
        } else {
            likeBtnName = "Like"
            likeText = "${recipe.favorites} person(s) love this recipe."
        }
        scrollView {
            verticalLayout {
                val layout = this
                textView{ text = recipe.name; textSize = 25f }.lparams{ leftMargin = dip(10); topMargin = dip(10) }
                textView{ text = recipe.headline; textSize = 15f; }.lparams{ leftMargin = dip(10); topMargin = dip(3)}

                textView{ text = "by ${recipe.user.name} (${recipe.user.email})"; textSize = 13f; }.lparams{ leftMargin = dip(10); topMargin = dip(3)}
                textView{ text = recipe.description; textSize = 15f; }.lparams{ leftMargin = dip(10); topMargin = dip(10) }
                textView{ text = "Calories: ${recipe.calories.ifEmpty { "inconclusive" }}"; textSize = 15f; }.lparams{ leftMargin = dip(10); topMargin = dip(15) }
                textView{ text = "Carbos: ${recipe.carbos.ifEmpty { "inconclusive" }}"; textSize = 15f; }.lparams{ leftMargin = dip(10); topMargin = dip(3) }
                textView{ text = "Proteins: ${recipe.proteins.ifEmpty { "inconclusive" }}"; textSize = 15f; }.lparams{ leftMargin = dip(10); topMargin = dip(3) }
                textView{ text = "Fats: ${recipe.fats.ifEmpty { "inconclusive" }}"; textSize = 15f; }.lparams{ leftMargin = dip(10); topMargin = dip(3) }

                textView{ text = "Ingredients: ${stringer.list2String(recipe.ingredients)}"; textSize = 15f; }.lparams{ leftMargin = dip(10); topMargin = dip(15) }
                textView{ text = "Products: ${stringer.list2String(recipe.products)}"; textSize = 15f; }.lparams{ leftMargin = dip(10); topMargin = dip(15) }

                val likeTextView = textView{ text = likeText; textSize = 15f; }.lparams{ leftMargin = dip(10); topMargin = dip(15) }

                linearLayout{
                    button{ text = likeBtnName; onClick { like(recipe.id, this@button, likeTextView, recipe.favorites) }; }.lparams{leftMargin = dip(10); topMargin = dip(25); bottomMargin = dip(20) }
                    button{ text = "Back To Recipes "; onClick { layout.visibility = View.GONE; makeRecipesView(recipes); }; }.lparams{ leftMargin = dip(3); topMargin = dip(25); bottomMargin = dip(20) }
                }
            }
        }
    }

    private fun like(id : String, btn : Button, likeTextView: TextView, favourites : Int){
        if(vm.isFavourite(id)){
            vm.removeFromFavourites(id)
            likeTextView.text = "${favourites} person(s) love this recipe."
            btn.text = "Like"
        }else{
            vm.add2Favourites(id)
            likeTextView.text = "You and ${favourites} other person(s) love this recipe."
            btn.text = "Unlike"
        }
    }

    override fun onBackPressed() {
        if(location == 0 || location == 1) super.onBackPressed()
    }
}
