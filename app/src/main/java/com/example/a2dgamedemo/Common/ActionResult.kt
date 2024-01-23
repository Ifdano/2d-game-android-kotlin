package com.example.a2dgamedemo.Common

open class ActionResult {
    private var isSucceed = false
    private var exception: Exception? = null

    companion object {
        fun succeed(): ActionResult {
            val result = ActionResult()
            succeedResult(result)

            return result
        }

        fun succeedResult(result: ActionResult) {
            result.isSucceed = true
            result.exception = null
        }

        fun failure(exception: Exception?): ActionResultT<Any> {
            val result = ActionResultT<Any>()
            failureResult(result, exception)

            return result
        }

        fun failureResult(result: ActionResult, exception: Exception?) {
            result.isSucceed = false
            result.exception = exception
        }
    }
}
