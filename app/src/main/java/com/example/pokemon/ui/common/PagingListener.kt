package com.example.pokemon.ui.common

import androidx.paging.LoadState
import com.example.pokemon.vo.Failure
import com.example.pokemon.vo.Status

interface PagingListener {

    fun refreshStatus(status: Status, failure: Failure?) {}

    fun appendStatus(status: Status, failure: Failure?) {}

    fun prependStatus(status: Status, failure: Failure?) {}

}