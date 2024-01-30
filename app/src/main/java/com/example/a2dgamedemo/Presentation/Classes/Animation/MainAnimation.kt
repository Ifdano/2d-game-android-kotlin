package com.example.a2dgamedemo.Presentation.Classes.Animation

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import com.example.a2dgamedemo.Presentation.Views.IMainAnimationView

class MainAnimation : IMainAnimationView{
    private lateinit var frames : Array<Bitmap>
    private var currentFrame = 0

    private var startTime = 0L
    private var delay = 0L
    override fun setFrame(frames: Array<Bitmap>) {
        this.frames = frames

        if(currentFrame >= frames.size)
            currentFrame = 0
    }

    override fun setDelay(delay: Long) {
        this.delay = delay
    }

    override fun getImage(): Bitmap = frames[currentFrame]

    override fun update() {
        if(delay == -1L)
            return

        val elapsedTime = (System.nanoTime() - startTime)/1000000
        if(elapsedTime > delay){
            currentFrame++
            startTime = System.nanoTime()
        }

        if(currentFrame == frames.size){
            currentFrame = 0
        }
    }

    override fun draw(canvas: Canvas, paint: Paint) {
        TODO("Not yet implemented")
    }
}