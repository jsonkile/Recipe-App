package com.golde.androidtest.DI

import com.golde.androidtest.Data.RecipeAPI
import com.golde.androidtest.Data.Repo.RecipeRepo
import com.golde.androidtest.Data.Repo.RecipeRepoImpl
import com.golde.androidtest.Data.Repo.UserRepo
import com.golde.androidtest.Data.Repo.UserRepoImpl
import com.golde.androidtest.UI.VMs.MainViewModel
import com.golde.androidtest.UI.VMs.RecipeViewModel
import com.golde.androidtest.Util.BASE_URL
import com.golde.androidtest.Util.Stringer
import com.google.gson.GsonBuilder
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule : Module = module {
    viewModel { MainViewModel(get(), get(), androidApplication()) }
    viewModel { RecipeViewModel() }

    single<UserRepo> { UserRepoImpl() }
    single<RecipeRepo> { RecipeRepoImpl(get(), androidApplication()) }
    single { Stringer() }

    single { GsonBuilder()
        .setLenient()
        .create()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }

    single { get<Retrofit>().create(RecipeAPI::class.java) }
}