package com.example.a2dgamedemo.Presentation.Classes.Characters.Common

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import com.example.a2dgamedemo.Enums.DensityTypes
import com.example.a2dgamedemo.Enums.SoundType
import com.example.a2dgamedemo.Presentation.Classes.Animation.MainAnimation
import com.example.a2dgamedemo.Presentation.Classes.Audio.MainAudio
import com.example.a2dgamedemo.Presentation.Classes.Common.MapObject
import com.example.a2dgamedemo.Presentation.Classes.Map.TileMap
import com.example.a2dgamedemo.Presentation.Helpers.DisplayHelper
import com.example.a2dgamedemo.Presentation.Views.IMainCharacterView
import java.io.Serializable

open class MainCharacter(context: Context, map: TileMap, mapSize: DensityTypes, density: Int) :
    MapObject(context, map, mapSize, density), IMainCharacterView, Serializable {

    protected var x = 0f
    protected var y = 0f
    protected var dx = 0f
    protected var dy = 0f

    protected var width = 0
    protected var height = 0

    protected var moveSpeed = 0f
    protected var stopSpeed = 0f
    protected var maxSpeed = 0f
    protected var gravity = 0f
    protected var maxFallingSpeed = 0f
    protected var jumpStart = 0f
    protected var jumpStop = 0f

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

    protected lateinit var idleBitmapList : Array<Bitmap>
    protected lateinit var walkBitmapList  : Array<Bitmap>
    protected lateinit var fallBitmapList  : Array<Bitmap>
    protected lateinit var jumpBitmapList  : Array<Bitmap>

    protected lateinit var animation : MainAnimation
    protected var facingRight = true

    protected lateinit var image : Bitmap
    protected lateinit var frame : Bitmap
    protected lateinit var pixel : IntArray

    protected lateinit var sounds : HashMap<String, MainAudio>

    init {
        animation = MainAnimation()

        sounds = HashMap()
        sounds.put(SoundType.JUMP.toString(), MainAudio(SoundType.JUMP, context))
    }

    override fun setPosition(x: Float, y: Float) {
        this.x = x
        this.y = y
    }

    override fun getX(): Float = x

    override fun getY(): Float = y

    override fun getWidth(): Int = width

    override fun getHeight(): Int = height

    override fun setLeft(value: Boolean){
        left = value
    }

    override fun setRight(value: Boolean) {
        right = value
    }

    override fun setJumping(value: Boolean) {
        if(!falling)
            jumping = value
    }

    override fun setReverse(value: Boolean) {
        if(!falling)
            reverse = value
    }

    override fun getReverse(): Boolean = reverse

    override fun setFocus(value: Boolean) {
        focus = value
    }

    override fun getFocus(): Boolean = focus

    override fun setJumpingStop() {
        jumpStop = 0f

        if(falling){
            if(getReverse()){
                if(dy > 0)
                    jumpStop = 2 * gravity
            }else{
                if(dy < 0)
                    jumpStop = 2 * gravity
            }
        }
    }

    override fun nextPosition() {
        checkMoving()
        checkJumping()
        checkFalling()
        checkPosition()
        checkFocus()
        checkFacingRight()
    }

    private fun checkMoving(){
        if(left){
            dx -= moveSpeed
            if(dx < -maxSpeed) dx = -maxSpeed
        }else{
            if(right){
                dx += moveSpeed
                if(dx > maxSpeed) dx = maxSpeed
            }else{
                if(dx > 0){
                    dx -= stopSpeed
                    if(dx < 0) dx = 0f
                }else if(dx < 0){
                    dx += stopSpeed
                    if(dx > 0) dx = 0f
                }
            }
        }
    }

    private fun checkJumping(){
        if(jumping){
            sounds.get(SoundType.JUMP.toString())?.play()

            dy = jumpStart
            jumping = false
            falling = true
        }
    }

    private fun checkFalling(){
        /*if(falling){
            if(getReverse()){
                if(gravity > 0){
                    gravity *= -1
                    jumpStart *= -1
                    jumpStop *= -1
                }

                dy += gravity + jumpStop
                if(dy < -maxFallingSpeed) dy = -maxFallingSpeed

                if(dy <= 0) jumpStop = 0f
            }else{
                if(gravity < 0){
                    gravity *= -1
                    jumpStart *= -1
                    jumpStop *= -1
                }

                dy += gravity + jumpStop
                if(dy > maxFallingSpeed) dy = maxFallingSpeed

                if(dy >= 0) jumpStop = 0f
            }
        } else dy = 0f*/

        if(falling){
            if(gravity > 0 || gravity < 0){
                gravity *= -1
                jumpStart *= -1
                jumpStop *= -1
            }

            dy += gravity + jumpStop

            if(getReverse()){
                if(dy < -maxFallingSpeed) dy = -maxFallingSpeed
                if(dy <= 0) jumpStop = 0f
            }else{
                if(dy > maxFallingSpeed) dy = maxFallingSpeed
                if(dy >= 0) jumpStop = 0f
            }
        } else dy = 0f
    }

    private fun checkPosition(){
        val currentColTile = map.getColTile(getX().toInt())
        val currentRowTile = map.getRowTile(getY().toInt())

        val toX = getX() + dx
        val toY = getY() + dy

        var tempX = getX()
        var tempY = getY()

        calculateCorners(toX, getY())
        if(dx < 0){
            if(topLeft || bottomLeft){
                dx = 0f
                tempX = (currentColTile * map.getMapSize() + getWidth()/2).toFloat()
            }else
                tempX += dx
        }

        if (dx > 0){
            if(topRight || bottomRight){
                dx = 0f
                tempX = ((currentColTile + 1) * map.getMapSize() - getWidth()/2).toFloat()
            }else
                tempX += dx
        }

        calculateCorners(getX(), toY)
        if(dy < 0){
            if(getReverse()){
                if(bottomLeft || bottomRight){
                    dy = 0f
                    falling = false
                    tempY = (currentRowTile * map.getMapSize() + getHeight()/2).toFloat()
                }else
                    tempY += dy
            }else{
                if(topLeft || topRight){
                    dy = 0f
                    tempY = (currentRowTile * map.getMapSize() + getHeight()/2).toFloat()
                }else
                    tempY += dy
            }
        }

        if(dy > 0){
            if(getReverse()){
                if(topLeft || topRight){
                    dy = 0f
                    tempY = ((currentRowTile + 1) * map.getMapSize() - getHeight()/2).toFloat()
                }else
                    tempY += dy
            }else{
                if(bottomLeft || bottomRight){
                    dy = 0f
                    facingRight = false
                    tempY = ((currentRowTile + 1) * map.getMapSize() - getHeight()/2).toFloat()
                }else
                    tempY += dy
            }
        }

        if(!falling){
            if(getReverse()){
                calculateCorners(getX(), getY()-1)
            } else{
                calculateCorners(getX(), getY() + 1)
            }

            if(!bottomLeft && !bottomRight) falling = true
        }

        x = tempX
        y = tempY
    }

    private fun checkFocus(){
        if(getFocus()){
            map.setX((DisplayHelper.getDisplayWidth(context)/2 - x).toInt())
            map.setY((DisplayHelper.getDisplayHeight(context)/2 - y).toInt())
        }
    }

    private fun checkFacingRight(){
        if(dx < 0) facingRight = false
        else if (dx > 0) facingRight = true
    }

    private fun calculateCorners(xPosition: Float, yPosition: Float){
        if(getReverse()){
            topTile = map.getRowTile((getY() + getHeight()/2).toInt() - 1)
            bottomTile = map.getRowTile((getY() - getHeight()/2).toInt())
        }else{
            topTile = map.getRowTile((getY() - getHeight()/2).toInt())
            bottomTile = map.getRowTile((getY() + getHeight()/2).toInt() - 1)
        }

        leftTile = map.getColTile((getX() - getWidth()/2).toInt())
        rightTile = map.getColTile((getX() + getWidth()/2).toInt() - 1)

        topLeft = map.isBlocked(topTile, leftTile)
        topRight = map.isBlocked(topTile, rightTile)
        bottomLeft = map.isBlocked(bottomTile, leftTile)
        bottomRight = map.isBlocked(bottomTile, rightTile)
    }

    override fun update() {}

    override fun draw(canvas: Canvas, paint: Paint) {
        val tx = map.getX()
        val ty = map.getY()

        val matrix = Matrix()
        val spriteImage = animation.getImage()

        if(facingRight){
            when(getReverse()){
                true -> matrix.postScale(1f, -1f)
                false -> matrix.postScale(1f, 1f)
            }
        }else{
            when(getReverse()){
                true -> matrix.postScale(-1f, -1f)
                false -> matrix.postScale(-1f, 1f)
            }
        }

        val frame = Bitmap.createBitmap(spriteImage, 0, 0, getWidth(), getHeight(), matrix, false)
        canvas.drawBitmap(frame, (tx + getX() - getWidth()/2).toFloat(), (ty + getY() - getHeight()/2).toFloat(), paint)
    }
}