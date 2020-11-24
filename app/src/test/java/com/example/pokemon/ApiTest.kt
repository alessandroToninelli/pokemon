package com.example.pokemon

import com.example.pokemon.data.RemoteApi
import com.example.pokemon.di.KoinManager
import com.example.pokemon.vo.response
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test

import org.junit.Before
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ApiTest : KoinTest {

    private val api: RemoteApi by inject()

    @Before
    fun loadKoin() {
        startKoin {
            KoinManager.loadModules()
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun testPokemonDetail() = runBlocking {

        val data = api.pokemonDetail(1)

        println(data.response())
    }


    @Test
    fun testPokemonList() = runBlocking {

        val data = api.pokemonList(0, 10)

        println(data.response())
    }


}