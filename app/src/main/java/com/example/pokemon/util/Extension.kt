package com.example.pokemon.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.pokemon.vo.Failure
import com.example.pokemon.vo.Resource
import com.example.pokemon.vo.Status
import com.example.pokemon.vo.throwOnError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.map

fun <T : Any> Flow<Resource<Pager<Int, T>>>.toPagingDataFlow(scope: CoroutineScope): Flow<PagingData<T>> {
    return this.throwOnError()
        .map { it.data }
        .filterNotNull()
        .map { it.flow }
        .flattenMerge()
        .cachedIn(scope)
}

fun LoadState.toStatus(): Status {
    val isError = this is LoadState.Error
    val isLoading = this is LoadState.Loading
    return when {
        isError -> Status.ERROR
        isLoading -> Status.LOADING
        else -> Status.SUCCESS
    }
}

fun LoadState.errorOrNull(): Failure? {
    return when (this) {
        is LoadState.Error -> (this.error) as Failure
        else -> null
    }
}

