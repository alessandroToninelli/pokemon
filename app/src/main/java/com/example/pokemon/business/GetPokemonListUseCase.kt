package com.example.pokemon.business

import androidx.paging.Pager
import com.example.pokemon.data.repo.PokemonRepository
import com.example.pokemon.model.Pokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetPokemonListUseCase(private val repo: PokemonRepository) : ValueFlowUseCase<Nothing, Pager<Int, Pokemon>>() {

    override fun doTask(param: Nothing?): Flow<Pager<Int, Pokemon>> = flow {
        emit(repo.pokemonList())
    }

}