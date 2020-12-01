package com.example.pokemon.vo

import kotlinx.coroutines.cancel
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlin.coroutines.cancellation.CancellationException

fun <R> Flow<R>.either(): Flow<Either<Failure, R>> =
    flow<Either<Failure, R>> {
        collect {
            emit(right(it))
        }
    }.catch {
        it.printStackTrace()
        emit(left(Failure.Error(it)))
    }


suspend inline fun cancelFlow(exception: Exception){
    currentCoroutineContext().cancel(CancellationException(exception))
}