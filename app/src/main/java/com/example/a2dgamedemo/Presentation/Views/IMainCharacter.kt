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

    //fun setReverse(value: Boolean)
    //fun getReverse() : Boolean

    //fun setFocus(value: Boolean)
    //fun getFocus() : Boolean

    fun setJumpingStop()

    fun nextPosition()
}