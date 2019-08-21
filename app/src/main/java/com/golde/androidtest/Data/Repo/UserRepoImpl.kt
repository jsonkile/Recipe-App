package com.golde.androidtest.Data.Repo

import khangtran.preferenceshelper.PrefHelper

class UserRepoImpl : UserRepo {
    override fun login() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun add2Favourites(id : String) {
        PrefHelper.setVal(id, true)
    }

    override fun isFavourite(id : String) : Boolean{
        return PrefHelper.getBooleanVal(id, false)
    }

    override fun removeFromFavourites(id: String) {
        PrefHelper.removeKey(id)
    }
}