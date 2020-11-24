package com.example.pokemon.data.repo

import androidx.paging.Pager
import com.example.pokemon.data.PageDataSource
import com.example.pokemon.data.RemoteApi
import com.example.pokemon.model.Pokemon
import com.example.pokemon.model.PokemonDetail
import com.example.pokemon.vo.Either
import com.example.pokemon.vo.Failure
import com.example.pokemon.vo.response
import com.example.pokemon.vo.rightCatching

interface PokemonRepository {

    suspend fun pokemonList(): Pager<Int, Pokemon>

    suspend fun pokemonDetail(id: Int): Either<Failure, PokemonDetail>


}


class PokemonRepoImpl(private val remoteApi: RemoteApi): PokemonRepository{

    override suspend fun pokemonList(): Pager<Int, Pokemon> {
        return PageDataSource.build(40, 0) {
            rightCatching {
                val offset = it.key ?: 0
                val data = remoteApi.pokemonList(offset, it.loadSize).response()
                data.results
            }
        }
    }

    override suspend fun pokemonDetail(id: Int): Either<Failure, PokemonDetail> {
       return rightCatching {
           remoteApi.pokemonDetail(id).response()
       }
    }


}



