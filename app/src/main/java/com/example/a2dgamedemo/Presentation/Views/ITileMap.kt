package com.example.a2dgamedemo.Presentation.Views

import android.graphics.Canvas
import android.graphics.Paint

interface ITileMap {
    fun draw(canvas: Canvas, paint: Paint)

    fun update()

    fun readMap()
}