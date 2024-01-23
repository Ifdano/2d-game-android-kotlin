package com.example.a2dgamedemo.Common.Base

import java.io.Serializable

open class BaseDTO : Serializable {
    private var id = 0L
    private var version = 0
    private var isImporting = false

    fun isNew() : Boolean = id == 0L
}
