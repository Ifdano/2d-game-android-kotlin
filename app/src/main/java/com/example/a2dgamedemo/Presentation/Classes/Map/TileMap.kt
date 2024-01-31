package com.example.a2dgamedemo.Presentation.Classes.Map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import com.example.a2dgamedemo.Enums.DensityTypes
import com.example.a2dgamedemo.Presentation.Helpers.DisplayHelper
import com.example.a2dgamedemo.Presentation.Views.ITileMap
import com.example.a2dgamedemo.R
import java.io.BufferedReader
import java.io.InputStreamReader

class TileMap(context: Context, mapSize: DensityTypes) : ITileMap {
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
    private var mapSize : DensityTypes? = null

    private var numRowToDraw = 0
    private var numColToDraw = 0
    private var rowOffset = 0
    private var colOffset = 0

    //private var map: Array<Array<Int>>? = null
    private var map: Array<IntArray>? = null

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

        colOffset = -x/mapSize!!.value
    }

    override fun getX() = x

    override fun setY(value : Int){
        y = value

        if(y < minY) y = minY
        if(y > maxY) y = maxY

        rowOffset = -y/ mapSize!!.value
    }

    override fun getY() = y

    override fun getRowTile(y : Int): Int = y/mapSize!!.value
    override fun getColTile(x : Int): Int = x/mapSize!!.value

    override fun getMapSize(): Int = mapSize!!.value

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
                    tiles!![r][c].image!!,
                    (x + col * mapSize!!.value).toFloat(),
                    (y + row * mapSize!!.value).toFloat(),
                    paint
                )
            }
        }
    }

    override fun update() {}

    override fun readMap() {}
    override fun loadMap() {
        try{
            val inputStreamReader = InputStreamReader(context?.resources?.openRawResource(R.raw.map))
            val bufferedReader = BufferedReader(inputStreamReader)

            minX = DisplayHelper.getDisplayWidth(context!!) - mapWidth*mapSize!!.value
            minY = DisplayHelper.getDisplayHeight(context!!) - mapHeight*mapSize!!.value

            numRowToDraw = DisplayHelper.getDisplayHeight(context!!)/mapSize!!.value + 2
            numColToDraw = DisplayHelper.getDisplayWidth(context!!)/mapSize!!.value + 2

            //map = arrayOf(arrayOf(mapHeight, mapWidth))

            //переносим карту в массив
            map = Array<IntArray>(mapHeight) { IntArray(mapWidth) }
            val delimeters = " "

            for(row in 0 until mapHeight){
                val line = bufferedReader.readLine()
                //val tokens: List<String> = line.split(delimeters)
                val tokens = line.split(delimeters).toTypedArray()

                for(col in 0 until mapWidth){
                    map!![row][col] = tokens[col].toInt()
                }
            }
        }catch (exp : Exception){
            exp.printStackTrace()
        }
    }

    override fun loadTile() {
        tileSet = getTileSet()

        val numTileAcross = tileSet!!.width / mapSize!!.value
        tiles = Array(2){ Array(numTileAcross) {Tile(null, false)} }

        var subImage : Bitmap
        var pixel: IntArray

        for(col in 0 until numTileAcross){
            subImage = Bitmap.createBitmap(mapSize!!.value, mapSize!!.value, Bitmap.Config.ARGB_8888)
            pixel = IntArray(mapSize!!.value * mapSize!!.value)

            tileSet?.getPixels(pixel, 0, mapSize!!.value, col * mapSize!!.value, 0, mapSize!!.value, mapSize!!.value)
            subImage.setPixels(pixel, 0, mapSize!!.value, 0, 0, mapSize!!.value, mapSize!!.value)
            tiles!![0][col] = Tile(subImage, false)

            subImage = Bitmap.createBitmap(mapSize!!.value, mapSize!!.value, Bitmap.Config.ARGB_8888)
            pixel = IntArray(mapSize!!.value * mapSize!!.value)

            tileSet?.getPixels(pixel, 0, mapSize!!.value, col * mapSize!!.value, mapSize!!.value, mapSize!!.value, mapSize!!.value)
            subImage.setPixels(pixel, 0, mapSize!!.value, 0, 0, mapSize!!.value, mapSize!!.value)
            tiles!![1][col] = Tile(subImage, true)
        }
    }

    override fun getTileSet(): Bitmap {
        tileSet = BitmapFactory.decodeResource(context!!.resources, R.drawable.tileset)

        var tileSetFinal = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
        val tileSetTemp : Bitmap = tileSet as Bitmap

        if(mapSize!! == DensityTypes.DENSITY_XXXHIGH)
            tileSetFinal = Bitmap.createScaledBitmap(tileSetTemp, 3744, 576, true)
        else if (mapSize!! == DensityTypes.DENSITY_35X)
            tileSetFinal = Bitmap.createScaledBitmap(tileSetTemp, 3276, 504, true)
        else if (mapSize!! == DensityTypes.DENSITY_XXHIGH)
            tileSetFinal = Bitmap.createScaledBitmap(tileSetTemp, 2808, 432, true)
        else if (mapSize!! == DensityTypes.DENSITY_26X)
            tileSetFinal = Bitmap.createScaledBitmap(tileSetTemp, 2433, 374, true)
        else if (mapSize!! == DensityTypes.DENSITY_22X)
            tileSetFinal = Bitmap.createScaledBitmap(tileSetTemp, 2059, 316, true)
        else if (mapSize!! == DensityTypes.DENSITY_XHIGH)
            tileSetFinal = Bitmap.createScaledBitmap(tileSetTemp, 1872, 288, true)
        else if (mapSize!! == DensityTypes.DENSITY_HIGH)
            tileSetFinal = Bitmap.createScaledBitmap(tileSetTemp, 1404, 216, true)

        return tileSetTemp
    }
}