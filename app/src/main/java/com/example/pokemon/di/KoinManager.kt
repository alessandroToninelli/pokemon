package com.example.pokemon.di

import com.example.pokemon.di.BusinessModule
import com.example.pokemon.di.DataModule
import com.example.pokemon.di.UiModule
import org.koin.core.Koin
import org.koin.core.component.KoinComponent
import org.koin.core.context.loadKoinModules

class KoinManager: KoinComponent {

    val k: Koin = getKoin()

    companion object {

        fun loadModules() {
            loadKoinModules(
                listOf(
                    DataModule.netModule,
                    DataModule.repoModule,
                    BusinessModule.useCaseModule,
                    UiModule.mainActivityModule,
                )
            )
        }
    }


}