package com.example.a2dgamedemo.Application.Models

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.a2dgamedemo.Enums.DensityTypes
import com.example.a2dgamedemo.Presentation.Classes.Characters.Common.GoodCharacter
import com.example.a2dgamedemo.Presentation.Classes.Map.TileMap
import com.example.a2dgamedemo.Presentation.Helpers.DisplayHelper
import com.example.a2dgamedemo.Presentation.Helpers.MoveHelper
import com.example.a2dgamedemo.R

class SonicCharacter(context: Context, map: TileMap, mapSize: DensityTypes, density: Int) :
    GoodCharacter(context, map, mapSize, density) {

    init {
        idleBitmapList = ArrayList()
        walkBitmapList = ArrayList()
        fallBitmapList = ArrayList()
        jumpBitmapList = ArrayList()

        width = getCharacterSpriteSize().first
        height = getCharacterSpriteSize().second

        setCharacterSpeedValues()

        var idBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.sonic_id)
        idBitmap = getScaledImageVer2(idBitmap)
        idleBitmapList.add(idBitmap)

        var walkBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.sonic_walk)
        walkBitmap = getScaledImageVer1(walkBitmap)
        walkBitmapList = getSpriteList(walkBitmap, 9, getCharacterSpriteSize().first, getCharacterSpriteSize().second)

        var fallBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.sonic_fall)
        fallBitmap = getScaledImageVer2(fallBitmap)
        fallBitmapList.add(fallBitmap)

        var jumpBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.sonic_jump)
        jumpBitmap = getScaledImageVer1(jumpBitmap)
        jumpBitmapList = getSpriteList(jumpBitmap, 9, getCharacterSpriteSize().first, getCharacterSpriteSize().second)
    }

    private fun getScaledImageVer1(currentImage: Bitmap): Bitmap {
        var scaledImage = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)

        scaledImage = when (DisplayHelper.getDensityType(context)) {
            DensityTypes.DENSITY_XXXHIGH -> Bitmap.createScaledBitmap(currentImage, 1152, 168, true)
            DensityTypes.DENSITY_35X -> Bitmap.createScaledBitmap(currentImage, 1008, 147, true)
            DensityTypes.DENSITY_XXHIGH -> Bitmap.createScaledBitmap(currentImage, 864, 126, true)
            DensityTypes.DENSITY_26X -> Bitmap.createScaledBitmap(currentImage, 748, 109, true)
            DensityTypes.DENSITY_22X -> Bitmap.createScaledBitmap(currentImage, 633, 92, true)
            DensityTypes.DENSITY_XHIGH -> Bitmap.createScaledBitmap(currentImage, 576, 84, true)
            DensityTypes.DENSITY_HIGH -> Bitmap.createScaledBitmap(currentImage, 432, 63, true)
        }

        return scaledImage
    }

    private fun getScaledImageVer2(currentImage: Bitmap): Bitmap {
        var scaledImage = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)

        scaledImage = when (DisplayHelper.getDensityType(context)) {
            DensityTypes.DENSITY_XXXHIGH -> Bitmap.createScaledBitmap(currentImage, 128, 168, true)
            DensityTypes.DENSITY_35X -> Bitmap.createScaledBitmap(currentImage, 112, 147, true)
            DensityTypes.DENSITY_XXHIGH -> Bitmap.createScaledBitmap(currentImage, 96, 126, true)
            DensityTypes.DENSITY_26X -> Bitmap.createScaledBitmap(currentImage, 83, 109, true)
            DensityTypes.DENSITY_22X -> Bitmap.createScaledBitmap(currentImage, 70, 92, true)
            DensityTypes.DENSITY_XHIGH -> Bitmap.createScaledBitmap(currentImage, 64, 84, true)
            DensityTypes.DENSITY_HIGH -> Bitmap.createScaledBitmap(currentImage, 48, 63, true)
        }

        return scaledImage
    }

    override fun update() {
        nextPosition()

        checkLeftRightSides()
        checkTopBottomSides()

        animation.update()
    }

    private fun checkLeftRightSides(){
        if(left || right){
            animation.setFrame(walkBitmapList)
            animation.setDelay(90)
        }else {
            animation.setFrame(idleBitmapList)
            animation.setDelay(10)
        }
    }

    private fun checkTopBottomSides(){
        if((dy < 0 && reverse) || (dy > 0 && !reverse)){
            animation.setFrame(fallBitmapList)
            animation.setDelay(90)
        }else if((dy < 0 && !reverse) || (dy > 0 && reverse)){
            animation.setFrame(jumpBitmapList)
            animation.setDelay(90)
        }
    }

    private fun getCharacterSpriteSize() : Pair<Int, Int>{
        return when (DisplayHelper.getDensityType(context)) {
            DensityTypes.DENSITY_XXXHIGH -> Pair(128, 168)
            DensityTypes.DENSITY_35X -> Pair(112, 147)
            DensityTypes.DENSITY_XXHIGH -> Pair(96, 126)
            DensityTypes.DENSITY_26X -> Pair(83, 109)
            DensityTypes.DENSITY_22X -> Pair(70, 92)
            DensityTypes.DENSITY_XHIGH -> Pair(64, 84)
            DensityTypes.DENSITY_HIGH -> Pair(48, 63)
        }
    }

    private fun setCharacterSpeedValues(){
        moveSpeed = MoveHelper.getUpdatedSpeed(context, 0.6f)
        stopSpeed = MoveHelper.getUpdatedSpeed(context, 0.4f)
        maxSpeed = MoveHelper.getUpdatedSpeed(context, 5.0f)
        gravity = MoveHelper.getUpdatedSpeed(context, 0.64f)
        maxFallingSpeed = MoveHelper.getUpdatedSpeed(context, 12.0f)
        jumpStart = MoveHelper.getUpdatedSpeed(context, -17.3f)
    }
}