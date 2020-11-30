package com.example.pokemon.business

import androidx.paging.Pager
import com.example.pokemon.data.repo.PokemonRepository
import com.example.pokemon.model.Pokemon

class GetPokemonListUseCase(private val repo: PokemonRepository) : ValueFlowUseCase<Nothing, Pager<Int, Pokemon>>() {

    override suspend fun doTask(param: Nothing?, operation: Operation<Pager<Int, Pokemon>>) {
        operation.onNextValue(repo.pokemonList())
        operation.onCompletion()
    }

}