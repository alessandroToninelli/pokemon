package com.example.pokemon.di

import com.example.pokemon.business.GetPokemonDetailByIdUseCase
import com.example.pokemon.business.GetPokemonListUseCase
import org.koin.dsl.module
import org.koin.experimental.builder.factory
import org.koin.experimental.builder.single


object BusinessModule {

    val useCaseModule = module {
        factory<GetPokemonListUseCase>()
        factory<GetPokemonDetailByIdUseCase>()
    }
}