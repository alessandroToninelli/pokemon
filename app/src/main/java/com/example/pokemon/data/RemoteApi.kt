package com.example.pokemon.data

import android.widget.PopupMenu
import com.example.networkcalladapterlib.ResponseNetwork
import com.example.pokemon.model.Pokemon
import com.example.pokemon.model.PokemonDetail
import com.example.pokemon.model.PokemonListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RemoteApi {

    @GET("api/v2/pokemon/{pokemon}")
    suspend fun pokemonDetail(@Path("pokemon")pokemonId: Int): ResponseNetwork<PokemonDetail, Any>

    @GET("api/v2/pokemon/")
    suspend fun pokemonList(@Query("offset")offset: Int, @Query("limit")limit: Int): ResponseNetwork<PokemonListResponse, Any>

}