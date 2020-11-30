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


abstract class BaseUseCase<P, M, R> where P : Any, M : Any, R : Any {

    protected abstract fun mapper(value: M): Resource<R>

    protected abstract suspend fun doTask(param: P?, operation: Operation<M>)

    protected fun start(param: P?): Flow<Resource<R>> = callbackFlow<Resource<R>> {
        doTask(param, object : Operation<M> {
            override fun onNextValue(value: M) {
                sendBlocking(mapper(value))
            }

            override fun onError(e: Failure) {
                sendBlocking(Resource.Error(e))
                close()
            }

            override fun onCompletion() {
                close()
            }
        })

        awaitClose { }

    }.onStart {
        emit(Resource.Loading()) }
        .onCompletion { println("Completed ${this@BaseUseCase}")}
        .flowOn(Dispatchers.IO)
        .catch {
            if (it is Failure)
                emit(Resource.Error(it))
            else
                emit(Resource.Error(Failure.Error(it)))
        }



}


abstract class FlowUseCase<P, O, R> :
    BaseUseCase<P, O, R>() where P : Any, R : Any, O : Any {

    operator fun invoke(param: P?): Flow<Resource<R>> = start(param)

}

abstract class SingleUseCase<P, O, R> :
    BaseUseCase<P, O, R>() where P : Any, R : Any, O : Any {

    suspend operator fun invoke(param: P?): Resource<R> =
        start(param).filterNot { it.status == Status.LOADING }.first()

}

abstract class EitherFlowUseCase<P, R>: FlowUseCase<P, Either<Failure, R>, R>()  where P : Any, R : Any {

    override fun mapper(value: Either<Failure, R>): Resource<R> {
        return value.fold(
            {
                Resource.Error(it)
            },
            {
                Resource.Success(it)
            })
    }
}

abstract class EitherSingleUseCase<P, R>: SingleUseCase<P, Either<Failure, R>, R>()  where P : Any, R : Any {

    override fun mapper(value: Either<Failure, R>): Resource<R> {
        return value.fold(
            {
                Resource.Error(it)
            },
            {
                Resource.Success(it)
            })
    }
}

abstract class ValueFlowUseCase<P, R>: FlowUseCase<P, R, R>()where P : Any, R : Any{
    override fun mapper(value: R): Resource<R> {
        return Resource.Success(value)
    }
}

abstract class ValueSingleUseCase<P, R>: SingleUseCase<P, R, R>()where P : Any, R : Any{
    override fun mapper(value: R): Resource<R> {
        return Resource.Success(value)
    }
}


fun <P,O,R> execFlow(
    useCase: FlowUseCase<P,O,R>,
    param: P? = null
): Flow<Resource<R>> where P : Any, R : Any, O : Any{
    return useCase.invoke(param)
}

suspend fun <P,O,R> execSingle(
    useCase: SingleUseCase<P, O, R>,
    param: P? = null
): Resource<R> where P : Any, R : Any, O : Any {
    return useCase.invoke(param)
}

fun <P,O,R> execStream(
    stream : MutableSharedFlow<Resource<R>>,
    useCase: FlowUseCase<P,O,R>,
    param: P? = null,
    scope: CoroutineScope
) where P : Any, R : Any, O : Any{
    useCase.invoke(param).onEach { stream.tryEmit(it) }.launchIn(scope)
}

fun <P,O,R> ViewModel.execStream(
    stream : MutableSharedFlow<Resource<R>>,
    useCase: FlowUseCase<P,O,R>,
    param: P? = null,
    scope: CoroutineScope = viewModelScope
) where P : Any, R : Any, O : Any{
    useCase.invoke(param).onEach { stream.tryEmit(it) }.launchIn(scope)
}








