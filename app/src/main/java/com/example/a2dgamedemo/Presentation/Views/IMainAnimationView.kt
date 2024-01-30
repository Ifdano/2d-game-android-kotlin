package com.example.a2dgamedemo.Presentation.Views

import android.graphics.Bitmap
import com.example.a2dgamedemo.Presentation.Views.Common.IBaseView

interface IMainAnimationView : IBaseView{
    fun setFrame(frames : Array<Bitmap>)

    fun setDelay(delay : Long)

    fun getImage() : Bitmap
}