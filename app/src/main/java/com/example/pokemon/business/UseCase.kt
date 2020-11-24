package com.example.pokemon.business

import androidx.lifecycle.*
import androidx.paging.Pager
import com.example.pokemon.vo.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


abstract class UseCase<P : Any, R : Any> : BaseUseCase<P, Either<Failure, R>, R>() {

    override fun start(param: P?): Flow<Resource<R>> = callbackFlow {
        doTask(param, object : Operation<Either<Failure, R>> {
            override fun onNextValue(value: Either<Failure, R>) {
                value.fold(
                    {
                        val resource: Resource<R> = Resource.Error(it)
                        sendBlocking(resource)
                    },
                    {
                        val resource: Resource<R> = Resource.Success(it)
                        sendBlocking(resource)
                    })
            }

            override fun onError(e: Failure) {
                val resource: Resource<R> = Resource.Error(e)
                sendBlocking(resource)
                close()
            }

            override fun onCompletion() {
                close()
            }

        })

        awaitClose { }
    }

}

abstract class PageUseCase<P : Any, R : Any> : BaseUseCase<P, Pager<Int, R>, Pager<Int, R>>() {


    override fun start(param: P?): Flow<Resource<Pager<Int, R>>> = callbackFlow {

        doTask(param, object : Operation<Pager<Int, R>> {
            override fun onNextValue(value: Pager<Int, R>) {
                sendBlocking(Resource.Success(value))
            }

            override fun onCompletion() {
                close()
            }

            override fun onError(e: Failure) {
                val resource: Resource<R> = Resource.Error(e)
                sendBlocking(resource)
                close()
            }
        })

        awaitClose { }
    }

}

abstract class BaseUseCase<P, O, R> where P : Any, O : Any, R : Any {

    private var stream = buildResourceStream<R>()

    @ExperimentalCoroutinesApi
    private operator fun invoke(param: P?): Flow<Resource<R>> {

        return start(param)
            .onStart {
                emit(Resource.Loading())
            }
            .flowOn(Dispatchers.IO)
            .catch {
                if (it is Failure)
                    emit(Resource.Error(it))
                else
                    emit(Resource.Error(Failure.Error(it)))
            }
    }

    fun getAsyncResult(): Flow<Resource<R>> = stream

    fun exec(param: P?): Flow<Resource<R>> = invoke(param)

    fun execAsync(param: P?, scope: CoroutineScope) {
        exec(param).onEach { stream.tryEmit(it) }.launchIn(scope)
    }

    protected abstract suspend fun doTask(param: P?, operation: Operation<O>)

    private var param: P? = null

    abstract fun start(param: P?): Flow<Resource<R>>
}

fun <P : Any, O : Any, R : Any> ViewModel.exec(
    useCase: BaseUseCase<P, O, R>,
    param: P? = null
) = useCase.exec(param)

fun <P : Any, O : Any, R : Any> ViewModel.execAsync(
    useCase: BaseUseCase<P, O, R>,
    param: P? = null,
    scope: CoroutineScope = viewModelScope
) {
    useCase.execAsync(param, scope)
}





