package com.example.pokemon.business

import com.example.pokemon.vo.Failure


interface Operation<T> {
    fun onNextValue(value: T) {}
    fun onError(e: Failure) {}
    fun onCompletion() {}
}