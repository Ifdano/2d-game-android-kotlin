package com.example.a2dgamedemo.Presentation.Helpers

import android.content.Context
import android.content.res.Configuration
import android.util.TypedValue
import android.view.Display
import android.view.WindowManager
import com.example.a2dgamedemo.Enums.DensityTypes

class DisplayHelper {
    companion object {
        private fun getDefaultDisplay(context: Context) : Display {
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            return windowManager.defaultDisplay
        }
        
        fun getDisplayWidth(context: Context): Int = getDefaultDisplay(context).width

        fun getDisplayHeight(context: Context): Int{
            var statusBarHeight = 0
            val statusBarId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
            if(statusBarId > 0){
                statusBarHeight = context.resources.getDimensionPixelOffset(statusBarId)
            }

            var actionBarHeight = 0
            val typedValue : TypedValue = TypedValue()
            if(context.theme.resolveAttribute(android.R.attr.actionBarSize, typedValue, true)){
                actionBarHeight = TypedValue.complexToDimensionPixelSize(typedValue.data, context.resources.displayMetrics)
            }

            return getDisplayHeight(context) - statusBarHeight - actionBarHeight
        }

        fun getDensity(): Int = 0

        fun getDensityType(context: Context): DensityTypes {
            var isSmall = false
            var isNormal = false
            var isLarge = false
            var isXLarge = false

            when (context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) {
                Configuration.SCREENLAYOUT_SIZE_XLARGE -> isXLarge = true
                Configuration.SCREENLAYOUT_SIZE_LARGE -> isLarge = true
                Configuration.SCREENLAYOUT_SIZE_NORMAL -> isNormal = true
                Configuration.SCREENLAYOUT_SIZE_SMALL -> isSmall = true
            }

            val displayMetrics = context.resources.displayMetrics

            when ((160f * displayMetrics.density).toInt()) {
                in 600..1000 -> DensityTypes.DENSITY_XXXHIGH
                in 520 until 600 -> DensityTypes.DENSITY_35X
                in 460 until 520 -> DensityTypes.DENSITY_XXHIGH
                in 380 until 460 -> DensityTypes.DENSITY_26X
                in 330 until 380 -> DensityTypes.DENSITY_22X
                in 270 until 330 -> DensityTypes.DENSITY_XHIGH
                in 200 until 270 -> DensityTypes.DENSITY_HIGH
            }

            return DensityTypes.DENSITY_XHIGH
        }
    }
}