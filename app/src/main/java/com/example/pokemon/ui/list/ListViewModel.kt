package com.example.pokemon.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.pokemon.business.GetPokemonListUseCase
import com.example.pokemon.business.execStream
import com.example.pokemon.model.Pokemon
import com.example.pokemon.util.toPagingDataFlow
import com.example.pokemon.vo.Resource
import com.example.pokemon.vo.buildResourceStream


class ListViewModel (private val getPokemonListUseCase: GetPokemonListUseCase): ViewModel() {

    private val _listResultStream = buildResourceStream<Pager<Int, Pokemon>>()

    val listResult = _listResultStream.toPagingDataFlow(viewModelScope)

    fun loadListResult(){
        execStream(_listResultStream, getPokemonListUseCase)
    }

}