package com.example.a2dgamedemo.Presentation.Classes.Background

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import com.example.a2dgamedemo.Presentation.Helpers.DisplayHelper
import com.example.a2dgamedemo.Presentation.Views.IMainBackground
import com.example.a2dgamedemo.R

class MainBackground(context: Context) : IMainBackground{
    private val MOVE_SCALE_BACKGROUND_MOUNTAINS = 1.3f
    private val MOVE_SCALE_BACKGROUND_CLOUDS = 1.1f

    private var context: Context

    private var xPositionMountains = 0f
    private var xPositionClouds = 0f
    private var yPosition = 0f

    private lateinit var bitmapMountains: Bitmap
    private lateinit var bitmapClouds: Bitmap

    init {
        this.context = context

        setBackground()
    }

    private fun setBackground(){
        val originalBitmapClouds = BitmapFactory.decodeResource(context.resources, R.drawable.back01)
        val originalBitmapMountains = BitmapFactory.decodeResource(context.resources, R.drawable.back1)

        bitmapClouds = Bitmap.createScaledBitmap(
            originalBitmapClouds,
            DisplayHelper.getDisplayWidth(context),
            DisplayHelper.getDisplayHeight(context),
            true
        )

        bitmapMountains = Bitmap.createScaledBitmap(
            originalBitmapMountains,
            DisplayHelper.getDisplayWidth(context),
            DisplayHelper.getDisplayHeight(context),
            true
        )
    }

    override fun update(xPosition: Float) {
        xPositionMountains = (xPosition * MOVE_SCALE_BACKGROUND_MOUNTAINS) % DisplayHelper.getDisplayWidth(context)
        xPositionClouds = (xPosition * MOVE_SCALE_BACKGROUND_CLOUDS) % DisplayHelper.getDisplayWidth(context)
    }

    override fun update() {}

    override fun draw(canvas: Canvas, paint: Paint) {
        drawBackgroundClouds(canvas, paint)
        drawBackgroundMountains(canvas, paint)
    }

    private fun drawBackgroundMountains(canvas: Canvas, paint: Paint) {
        canvas.drawBitmap(bitmapMountains, xPositionMountains, yPosition, paint)

        if(xPositionMountains < 0){
            canvas.drawBitmap(
                bitmapMountains,
                xPositionMountains + DisplayHelper.getDisplayWidth(context),
                yPosition,
                paint
            )
        }

        if(xPositionMountains > 0){
            canvas.drawBitmap(
                bitmapMountains,
                xPositionMountains - DisplayHelper.getDisplayWidth(context),
                yPosition,
                paint
            )
        }
    }

    private fun drawBackgroundClouds(canvas: Canvas, paint: Paint) {
        canvas.drawBitmap(bitmapClouds, xPositionClouds, yPosition, paint)

        if(xPositionClouds < 0){
            canvas.drawBitmap(
                bitmapClouds,
                xPositionClouds + DisplayHelper.getDisplayWidth(context),
                yPosition,
                paint
            )
        }

        if(xPositionClouds > 0){
            canvas.drawBitmap(
                bitmapClouds,
                xPositionClouds - DisplayHelper.getDisplayWidth(context),
                yPosition,
                paint
            )
        }
    }

}