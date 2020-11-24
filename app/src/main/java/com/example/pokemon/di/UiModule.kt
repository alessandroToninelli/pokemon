package com.example.pokemon.di

import com.example.pokemon.ui.MainActivity
import com.example.pokemon.ui.detail.DetailFragment
import com.example.pokemon.ui.detail.DetailViewModel
import com.example.pokemon.ui.list.ListFragment
import com.example.pokemon.ui.list.ListViewModel
import org.koin.androidx.experimental.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.definition.Definition
import org.koin.core.qualifier.named
import org.koin.dsl.module

object UiModule {
    val mainActivityModule = module {

        scope<MainActivity> {

            scope<ListFragment> {
                viewModel<ListViewModel>()
            }

            scope<DetailFragment> {
                viewModel<DetailViewModel>()
            }

        }
    }
}