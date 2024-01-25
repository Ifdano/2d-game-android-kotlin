package com.example.a2dgamedemo.Presentation.Classes.Characters.Common

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.view.animation.Animation
import com.example.a2dgamedemo.Enums.DensityTypes
import com.example.a2dgamedemo.Presentation.Classes.Audio.Audio
import com.example.a2dgamedemo.Presentation.Classes.Common.MapObject
import com.example.a2dgamedemo.Presentation.Classes.Map.TileMap

open class MainCharacter(context: Context, map: TileMap, mapSize: DensityTypes, density: Int) :
    MapObject(context, map, mapSize, density) {
    protected var x = 0
    protected var y = 0
    protected var dx = 0
    protected var dy = 0

    protected var width = 0
    protected var height = 0

    protected var moveSpeed = 0
    protected var stopSpeed = 0
    protected var maxSpeed = 0
    protected var gravity = 0
    protected var maxFallingSpeed = 0
    protected var jumpStart = 0
    protected var jumpStop = 0

    protected var left = false
    protected var right = false
    protected var jumping = false
    protected var falling = false
    protected var reverse = false
    protected var focus = false

    protected var topTile = 0
    protected var bottomTile = 0
    protected var leftTile = 0
    protected var rightTile = 0

    protected var topLeft = false
    protected var topRight = false
    protected var bottomLeft = false
    protected var bottomRight = false

    protected lateinit var idle : Array<Bitmap>
    protected lateinit var walk : Array<Bitmap>
    protected lateinit var fall : Array<Bitmap>
    protected lateinit var jump : Array<Bitmap>

    protected lateinit var animation : Animation
    protected var facingRight = true

    protected lateinit var matrix : Matrix

    protected lateinit var image : Bitmap
    protected lateinit var frame : Bitmap
    protected lateinit var pixel : IntArray

    protected lateinit var sounds : HashMap<String, Audio>

    init {

    }
}