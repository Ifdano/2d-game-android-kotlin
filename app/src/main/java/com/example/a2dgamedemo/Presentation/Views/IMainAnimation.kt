package com.example.a2dgamedemo.Presentation.Views

import android.graphics.Bitmap
import com.example.a2dgamedemo.Presentation.Views.Common.IBaseView

interface IMainAnimation : IBaseView{
    fun setFrame(frames : ArrayList<Bitmap>)

    fun setDelay(delay : Long)

    fun getImage() : Bitmap
}