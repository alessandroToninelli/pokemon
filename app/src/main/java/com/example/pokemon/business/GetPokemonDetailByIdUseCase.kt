package com.example.pokemon.business

import com.example.pokemon.data.repo.PokemonRepository
import com.example.pokemon.model.PokemonDetail
import com.example.pokemon.vo.Either
import com.example.pokemon.vo.Failure
import com.example.pokemon.vo.left
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetPokemonDetailByIdUseCase(private val pokemonRepository: PokemonRepository): EitherFlowUseCase<Int, PokemonDetail>() {

    override fun doTask(param: Int?): Flow<Either<Failure, PokemonDetail>>  = flow{
        param?.let {
            emit(pokemonRepository.pokemonDetail(it))
        }?: emit(left(Failure.App.InvalidParam()))
    }


}