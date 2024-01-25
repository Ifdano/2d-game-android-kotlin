package com.example.a2dgamedemo.Presentation.Views.Common

import android.graphics.Canvas
import android.graphics.Paint

interface IBaseView {
    fun update()
    fun draw(canvas : Canvas, paint : Paint)
}