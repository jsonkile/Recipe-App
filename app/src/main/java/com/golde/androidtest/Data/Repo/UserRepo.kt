package com.golde.androidtest.Data.Repo

interface UserRepo {
    fun login()

    fun add2Favourites(id : String)

    fun isFavourite(id : String) : Boolean

    fun removeFromFavourites(id : String)
}