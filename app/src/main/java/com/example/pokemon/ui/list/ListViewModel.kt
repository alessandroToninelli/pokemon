package com.example.pokemon.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.pokemon.business.GetPokemonListUseCase
import com.example.pokemon.business.exec
import com.example.pokemon.business.execAsync
import com.example.pokemon.model.Pokemon
import com.example.pokemon.util.toPagingDataFlow
import com.example.pokemon.vo.Resource
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class ListViewModel (private val getPokemonListUseCase: GetPokemonListUseCase): ViewModel() {

//    private val listResultStream = buildStream<Pager<Int, Pokemon>>()

    val listResult = getPokemonListUseCase.getAsyncResult().toPagingDataFlow(viewModelScope)

    fun loadListResult(){
        execAsync(getPokemonListUseCase)
    }

}