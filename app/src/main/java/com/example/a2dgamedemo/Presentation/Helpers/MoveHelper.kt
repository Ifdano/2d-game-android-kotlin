package com.example.a2dgamedemo.Presentation.Helpers

import android.content.Context
import com.example.a2dgamedemo.Enums.DensityTypes

class MoveHelper {
    companion object{
        fun getUpdatedSpeed(context: Context, defaultSpeed: Float) : Float {
            return when(DisplayHelper.getDensityType(context)){
                DensityTypes.DENSITY_XXXHIGH -> 4 * defaultSpeed
                DensityTypes.DENSITY_35X -> 3.5f * defaultSpeed
                DensityTypes.DENSITY_XXHIGH -> 3 * defaultSpeed
                DensityTypes.DENSITY_26X -> 2.5f * defaultSpeed
                DensityTypes.DENSITY_22X -> 2.2f * defaultSpeed
                DensityTypes.DENSITY_XHIGH -> 2 * defaultSpeed
                DensityTypes.DENSITY_HIGH -> 1.5f * defaultSpeed
            }

            return 2 * defaultSpeed
        }
    }
}