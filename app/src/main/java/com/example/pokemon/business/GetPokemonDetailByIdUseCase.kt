package com.example.pokemon.business

import com.example.pokemon.data.repo.PokemonRepository
import com.example.pokemon.model.PokemonDetail
import com.example.pokemon.vo.Either
import com.example.pokemon.vo.Failure

class GetPokemonDetailByIdUseCase(private val pokemonRepository: PokemonRepository): EitherFlowUseCase<Int, PokemonDetail>() {
    override suspend fun doTask(param: Int?, operation: Operation<Either<Failure, PokemonDetail>>) {
        param?.let {
            operation.onNextValue(pokemonRepository.pokemonDetail(it))
            operation.onCompletion()
        }?: operation.onError(Failure.App.InvalidParam("Invalid Id"))
    }


}