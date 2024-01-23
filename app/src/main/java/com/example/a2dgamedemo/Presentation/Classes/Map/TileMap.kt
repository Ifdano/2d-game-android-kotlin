package com.example.a2dgamedemo.Presentation.Classes.Map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import com.example.a2dgamedemo.Presentation.Views.ITileMap

class TileMap(context: Context, mapSize: Int) : ITileMap {
    val MAP = "map.txt"

    private var context: Context? = null

    private var x = 0
    private var y = 0

    private var minX = 0
    private var minY = 0
    private var maxX = 0
    private var maxY = 0

    private var mapWidth = 30
    private var mapHeight = 20
    private var mapSize = 0

    private var numRowToDraw = 0
    private var numColToDraw = 0
    private var rowOffset = 0
    private var colOffset = 0

    private val map: Array<Array<Int>>? = null

    private var tileSet: Bitmap? = null
    private var tiles: Array<Array<Tile>>? = null

    init {
        this.context = context
        this.mapSize = mapSize
    }

    override fun draw(canvas: Canvas, paint: Paint) {
        if(tiles?.isEmpty()!!)
            return

        for (row in rowOffset until rowOffset + numRowToDraw) {
            if(row >= mapHeight)
                break

            for(col in colOffset until colOffset + numColToDraw){
                if(col >= mapWidth)
                    break

                if(map?.get(row)?.get(col) == 0)
                    continue

                val currentRowCol = map?.get(row)?.get(col)

                val r = currentRowCol!! / tiles?.get(0)?.size!!
                val c = currentRowCol % tiles?.get(0)?.size!!

                canvas.drawBitmap(
                    tiles!![r][c].image,
                    (x + col * mapSize).toFloat(),
                    (y + row * mapSize).toFloat(),
                    paint
                )
            }
        }
    }

    override fun update() {}

    override fun readMap() {}
}