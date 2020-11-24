package com.example.pokemon.vo


sealed class Failure(val msg: String) : Exception(msg) {

    class Error(val e: Throwable? = null, msg: String = e?.localizedMessage ?: "Error") :
        Failure(msg)

    sealed class App(mss: String): Failure(mss){
        class InvalidParam(msg: String = "Invalid Param") : App(msg)
    }


    sealed class Net(val code: Int, mss: String) : Failure(mss) {
        class Error(code: Int) : Net(code, "Network Error")
        class IOError(throwable: Throwable) :
            Net(-1, throwable.localizedMessage ?: "Network IO Error")
        class UnknownError(code: Int?, throwable: Throwable?) :
            Net(code ?: -1, throwable?.localizedMessage ?: "Unknown Error")

        class EmptyError(code: Int): Net(code, "Empty Response")
    }

}



