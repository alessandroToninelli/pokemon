package com.example.pokemon.vo

import com.example.networkcalladapterlib.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

inline fun <S : Any, E : Any, T : Any> ResponseNetwork<S, E>.mapSuccess(transform: (S) -> T): ResponseNetwork<T, E> {
    return when (this) {
        is ResponseNetworkSuccess -> ResponseNetworkSuccess(transform(this.body))
        is ResponseNetworkSuccessEmpty -> ResponseNetworkSuccessEmpty(this.code)
        is ResponseNetworkError -> ResponseNetworkError(this.code, this.body)
        is ResponseNetworkIOFailure -> ResponseNetworkIOFailure(this.error)
        is ResponseNetworkUnknownError -> ResponseNetworkUnknownError(this.code, this.error)
    }
}




fun <S : Any> ResponseNetwork<S, Any>.response(): S {
    return when (this) {
        is ResponseNetworkSuccess -> this.body
        is ResponseNetworkSuccessEmpty -> throw  Failure.Net.EmptyError(this.code)
        is ResponseNetworkError -> throw Failure.Net.Error(this.code)
        is ResponseNetworkIOFailure -> throw Failure.Net.IOError(this.error)
        is ResponseNetworkUnknownError -> throw Failure.Net.UnknownError(this.code, this.error)
    }
}

