package com.example.a2dgamedemo.Presentation.Views

import com.example.a2dgamedemo.Presentation.Views.Common.IBaseView

interface IMainCharacter : IBaseView{
    fun setPosition(x : Float, y : Float)

    //fun getX() : Float
    //fun getY() : Float

    //fun getWidth() : Int

    //fun getHeight() : Int

    ///fun setLeft(value : Boolean)
    //fun setRight(value: Boolean)
    //fun setJumping(value: Boolean)

    fun setReverse(value: Boolean)
    fun isReverse() : Boolean

    fun setCurrentFocus(value: Boolean)
    fun getCurrentFocus() : Boolean

    fun setJumpingStop()

    fun nextPosition()
}