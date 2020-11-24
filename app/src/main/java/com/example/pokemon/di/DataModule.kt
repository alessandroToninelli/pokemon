package com.example.pokemon.di


import com.example.networkcalladapterlib.NetworkCallAdapterFactory
import com.example.pokemon.data.RemoteApi
import com.example.pokemon.data.repo.PokemonRepoImpl
import com.example.pokemon.data.repo.PokemonRepository
import com.squareup.moshi.Moshi

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import org.koin.experimental.builder.singleBy

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

import java.util.concurrent.TimeUnit


object DataModule {

    val netModule = module {

        single {
            Retrofit.Builder()
                .client(get())
                .baseUrl("https://pokeapi.co/")
                .addCallAdapterFactory(NetworkCallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(RemoteApi::class.java)
        }


        single {
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    this.level = HttpLoggingInterceptor.Level.BASIC
                })
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .build()
        }

    }


    val repoModule = module {
        singleBy<PokemonRepository, PokemonRepoImpl>()
    }

}

