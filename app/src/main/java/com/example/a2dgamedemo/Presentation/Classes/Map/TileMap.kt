package com.example.a2dgamedemo.Presentation.Classes.Map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.icu.number.IntegerWidth
import com.example.a2dgamedemo.Presentation.Helpers.DisplayHelper
import com.example.a2dgamedemo.Presentation.Views.ITileMap
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception

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

    private var map: Array<Array<Int>>? = null

    private var tileSet: Bitmap? = null
    private var tiles: Array<Array<Tile>>? = null

    init {
        this.context = context
        this.mapSize = mapSize
    }

    override fun setX(value : Int){
        x = value

        if(x < minX) x = minX
        if(x > maxX) x = maxX

        colOffset = -x/mapSize
    }

    override fun getX() = x

    override fun setY(value : Int){
        y = value

        if(y < minY) y = minY
        if(y > maxY) y = maxY

        rowOffset = -y/mapSize
    }

    override fun getY() = y

    override fun getRowTile(y : Int): Int = y/mapSize
    override fun getColTile(x : Int): Int = x/mapSize

    override fun getMapSize(): Int = mapSize

    private fun getCurrentRowCol(row : Int, col : Int) = map?.get(row)?.get(col)

    override fun isBlocked(row: Int, col: Int): Boolean {
        val rc = getCurrentRowCol(row, col)

        val r = rc!! / tiles?.get(0)?.size!!
        val c = rc % tiles?.get(0)?.size!!

        return tiles!!.get(r).get(c).blocked
    }

    override fun setHeight(value: Int) {
        TODO("Not yet implemented")
    }

    override fun setWidth(value: Int) {
        TODO("Not yet implemented")
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

                val currentRowCol = getCurrentRowCol(row, col)

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
    override fun loadMap() {
        try{
            val inputStreamReader = InputStreamReader(context?.openFileInput(MAP))
            val bufferedReader = BufferedReader(inputStreamReader)

            minX = DisplayHelper.getDisplayWidth() - mapWidth*mapSize
            minY = DisplayHelper.getDisplayHeight() - mapHeight*mapSize

            numRowToDraw = DisplayHelper.getDisplayHeight()/mapSize + 2
            numColToDraw = DisplayHelper.getDisplayWidth()/mapSize + 2

            map = arrayOf(arrayOf(mapHeight, mapWidth))
            val delimeters = "\\s+"

            for(row in 0 until mapHeight){
                val line = bufferedReader.readLine()
                val tokens = line.split(delimeters)

                for(col in 0 until mapWidth){
                    map!![row][col] = tokens[col].toInt()
                }
            }
        }catch (exp : Exception){
            exp.printStackTrace()
        }
    }

    override fun loadTile() {
        TODO("Not yet implemented")
    }

    override fun getTileSet(): Bitmap {
        TODO("Not yet implemented")
    }
}