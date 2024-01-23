package com.example.a2dgamedemo.Common

class ActionResultT<T> : ActionResult() {
    // Getters
    private var data: T? = null

    companion object {
        fun <T> succeed(data: T): ActionResultT<T> {
            val result: ActionResultT<T> = ActionResultT()

            result.data = data
            succeedResult(result)

            return result
        }

        fun <T> failure(data: T?, exception: Exception?): ActionResultT<T> {
            val result: ActionResultT<T> = ActionResultT()

            result.data = data
            failureResult(result, exception)

            return result
        }
    }
}