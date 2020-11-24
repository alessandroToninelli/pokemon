package com.example.pokemon.vo

import android.content.Context
import android.widget.Toast
import com.example.pokemon.di.KoinManager
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.transform
import org.koin.core.component.get

sealed class Resource<out T>(
    val status: Status,
    open val data: T? = null,
    open val failure: Failure? = null
) {

    data class Success<out T>(override val data: T) : Resource<T>(Status.SUCCESS, data)
    data class Loading<out T>(override val data: T? = null) : Resource<T>(Status.LOADING, data)
    data class Error<out T>(override val failure: Failure, override val data: T? = null) :
        Resource<T>(Status.ERROR, data, failure)

}

inline fun <T> Resource<T>.case(
    loading: (r: Resource.Loading<T>) -> Unit = {},
    error: (r: Resource.Error<T>) -> Unit = {
        val context: Context = KoinManager().get()
        Toast.makeText(context, it.failure.msg, Toast.LENGTH_SHORT).show()
    },
    success: (r: Resource.Success<T>) -> Unit = {}
) {
    when (this.status) {
        Status.SUCCESS -> success(this as Resource.Success<T>)
        Status.ERROR -> error(this as Resource.Error<T>)
        Status.LOADING -> loading(this as Resource.Loading<T>)
    }
}


inline fun <O, N> Resource<O>.mapSuccess(mapper: (O) -> N): Resource<N> {
    return when (this) {
        is Resource.Success -> Resource.Success(mapper(this.data))
        is Resource.Loading -> Resource.Loading()
        is Resource.Error -> Resource.Error(this.failure)
    }
}

fun <T> Flow<Resource<T>>.throwOnError(): Flow<Resource<T>> {
    return this.transform {
        it.case(
            loading = { emit(it) },
            error = { throw it.failure },
            success = { emit(it) }
        )
    }
}

fun <T> buildResourceStream() =
    MutableSharedFlow<Resource<T>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)