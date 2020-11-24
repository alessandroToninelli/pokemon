package com.example.pokemon.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.pokemon.business.GetPokemonDetailByIdUseCase
import com.example.pokemon.business.exec
import com.example.pokemon.business.execAsync
import com.example.pokemon.model.PokemonDetail
import com.example.pokemon.model.Sprites
import com.example.pokemon.vo.Resource
import com.example.pokemon.vo.case
import com.example.pokemon.vo.mapSuccess
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.memberProperties

class DetailViewModel(
    private val getPokemonDetailByIdUseCase: GetPokemonDetailByIdUseCase
) : ViewModel() {

   private val pokemonResult = getPokemonDetailByIdUseCase.getAsyncResult()

    val urls = getPokemonDetailByIdUseCase.getAsyncResult().map {
        it.mapSuccess { detail ->
            val sprites = detail.sprites
            val urlsList = mutableListOf<String>()
            Sprites::class.declaredMemberProperties.forEach {
                val a = it.get(sprites) as? String
                a?.let { url -> urlsList.add(url) }
            }
            sprites.other.officialArtwork.front_default?.takeIf { it.isNotBlank() }
                ?.let { urlsList.add(it) }
            urlsList
        }
    }.asLiveData()

    val stats = pokemonResult.map { it.mapSuccess { it.stats } }.asLiveData()

    val types = pokemonResult.map { it.mapSuccess { it.types } }.asLiveData()

    val name = pokemonResult.map { it.mapSuccess { it.name } }.asLiveData()

    fun loadDetail(id: Int) {
        execAsync(getPokemonDetailByIdUseCase, id)
    }

}
