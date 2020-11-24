package com.example.pokemon.data

import androidx.paging.*
import com.example.pokemon.vo.Either
import com.example.pokemon.vo.Failure
import com.example.pokemon.vo.fold


abstract class PageDataSource<Type : Any> : PagingSource<Int, Type>() {

    private var startOffset = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Type> {

        var result: LoadResult<Int, Type>? = null


        when(params){
            is LoadParams.Refresh -> {
                startOffset = 0
            }
            else -> {}
        }
        getCall(params).fold(
            {
                result = LoadResult.Error(it)
            },
            {
                startOffset += params.loadSize
                result = if (it.isEmpty())
                    LoadResult.Error(Failure.Net.EmptyError(-1))
                else {
                    LoadResult.Page(
                        data = it,
                        nextKey = startOffset,
                        prevKey = null
                    )
                }
            }
        )

        return result!!
    }

    abstract suspend fun getCall(params: LoadParams<Int>): Either<Failure, List<Type>>

    companion object PageBuilder {

        fun <Type : Any> build(
            pageSize: Int,
            offset: Int,
            call: suspend (params: LoadParams<Int>) -> Either<Failure, List<Type>>
        ): Pager<Int, Type> {


            return Pager(
                PagingConfig(
                    pageSize = pageSize,
                    initialLoadSize = pageSize,
                    enablePlaceholders = false
                )
            ) {
                object : PageDataSource<Type>() {
                    override suspend fun getCall(params: LoadParams<Int>): Either<Failure, List<Type>> {
                        return call(params)
                    }
                }
            }

        }
    }
}