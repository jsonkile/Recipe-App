package com.golde.androidtest.Data.Models


import com.google.gson.annotations.SerializedName

data class Recipe(
    @SerializedName("calories")
    val calories: String,
    @SerializedName("carbos")
    val carbos: String,
    @SerializedName("card")
    val card: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("deliverable_ingredients")
    val deliverableIngredients: List<String>,
    @SerializedName("description")
    val description: String,
    @SerializedName("difficulty")
    val difficulty: Int,
    @SerializedName("fats")
    val fats: String,
    @SerializedName("favorites")
    val favorites: Int,
    @SerializedName("fibers")
    val fibers: String,
    @SerializedName("headline")
    val headline: String,
    @SerializedName("highlighted")
    val highlighted: Boolean,
    @SerializedName("id")
    val id: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("incompatibilities")
    val incompatibilities: Any,
    @SerializedName("ingredients")
    val ingredients: List<String>,
    @SerializedName("keywords")
    val keywords: List<String>,
    @SerializedName("name")
    val name: String,
    @SerializedName("products")
    val products: List<String>,
    @SerializedName("proteins")
    val proteins: String,
    @SerializedName("rating")
    val rating: Double,
    @SerializedName("ratings")
    val ratings: Int,
    @SerializedName("thumb")
    val thumb: String,
    @SerializedName("time")
    val time: String,
    @SerializedName("undeliverable_ingredients")
    val undeliverableIngredients: List<Any>,
    @SerializedName("user")
    val user: User,
    @SerializedName("weeks")
    val weeks: List<String>
)