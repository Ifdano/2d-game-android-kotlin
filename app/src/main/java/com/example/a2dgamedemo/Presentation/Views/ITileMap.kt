package com.example.a2dgamedemo.Presentation.Views

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint

interface ITileMap {
    fun setX(value : Int)
    fun getX() : Int

    fun setY(value : Int)
    fun getY() : Int

    fun getRowTile(y : Int) : Int
    fun getColTile(x : Int) : Int

    fun getMapSize() : Int

    fun isBlocked(row : Int, col : Int) : Boolean

    fun setHeight(value : Int)
    fun setWidth(value : Int)

    fun readMap()
    fun loadMap()

    fun loadTile()

    fun getTileSet() : Bitmap

    fun draw(canvas: Canvas, paint: Paint)

    fun update()

}