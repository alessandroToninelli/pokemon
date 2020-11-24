package com.example.pokemon.vo


sealed class Either<out L, out R> {

    data class Left<out L>(val left: L) : Either<L, Nothing>()

    data class Right<out R>(val right: R) : Either<Nothing, R>()

    val isRight
        get() = this is Right<R>

    val isLeft
        get() = this is Left<L>


}


inline fun <L, R, T> Either<L, R>.fold(
    fl: (L) -> T,
    fr: (R) -> T
): T {
    return when (this) {
        is Either.Left -> fl(left)
        is Either.Right -> fr(right)
    }
}

inline fun <L, R, newR> Either<L, R>.mapRight(transform: (R) -> newR): Either<L, newR> {
    return this.fold(
        {
            left(it)
        },
        {
            right(transform(it))
        }
    )
}

inline fun <L, R, newR> Either<L, R>.mapEitherRight(transform: (R) -> Either<L, newR>): Either<L, newR> {
    return this.fold(
        {
            left(it)
        },
        {
            transform(it)
        }
    )
}


fun <L, R> Either<L, R>.leftOrNull(): L? {
    return this.fold(
        {
            it
        },
        {
            null
        }
    )
}

fun <L, R> Either<L, R>.rightOrNull(): R? {
    return this.fold(
        {
            null
        },
        {
            it
        }
    )
}

inline fun <R, L> Either<L, R>.rightOrElse(onLeft: (L) -> R): R {
    return fold(
        {
            onLeft(it)
        },
        {
            it
        })
}

inline fun <R, L> Either<L, R>.leftOrElse(onRight: (R) -> L): L {
    return fold(
        {
            it
        },
        {
            onRight(it)
        })
}

inline fun <R, L> Either<L, R>.onRight(onRight: (R) -> Unit): Either<L, R> {
    this.rightOrNull()?.let(onRight)
    return this
}

inline fun <R, L> Either<L, R>.onLeft(onLeft: (L) -> Unit): Either<L, R> {
    this.leftOrNull()?.let(onLeft)
    return this
}

inline fun <R : Any?> rightCatching(block: () -> R): Either<Failure, R> {
    return try {
        right(block())
    } catch (f: Failure) {
        left(f)
    } catch (e: Exception) {
        left(Failure.Error(e))
    }

}

inline fun <L : Any> leftCatching(block: () -> L): Either<L, Exception> {
    return try {
        left(block())
    } catch (e: Exception) {
        right(e)
    }
}

fun <L> left(left: L) = Either.Left(left)

fun <R> right(right: R) = Either.Right(right)


