package com.example.a2dgamedemo.Presentation.Views

import com.example.a2dgamedemo.Presentation.Views.Common.IBaseView

interface IMainCharacter : IBaseView{
    fun setPosition(x : Float, y : Float)

    fun getCoordinateX() : Float
    fun getCoordinateY() : Float

    fun getCharacterWidth() : Int

    fun getCharacterHeight() : Int

    fun setCharacterLeft(value : Boolean)
    fun setCharacterRight(value: Boolean)
    fun setCharacterJumping(value: Boolean)

    fun setCharacterReverse(value: Boolean)
    fun isReverse() : Boolean

    fun setCurrentFocus(value: Boolean)
    fun getCurrentFocus() : Boolean

    fun setJumpingStop()

    fun nextPosition()
}