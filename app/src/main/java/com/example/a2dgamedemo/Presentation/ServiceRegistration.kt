package com.example.a2dgamedemo.Presentation

import com.example.a2dgamedemo.Presentation.Classes.Audio.MainAudio
import com.example.a2dgamedemo.Presentation.Classes.Background.MainBackground
import com.example.a2dgamedemo.Presentation.Classes.Map.TileMap
import com.example.a2dgamedemo.Presentation.Helpers.DisplayHelper
import com.example.a2dgamedemo.Presentation.Views.IMainAudio
import com.example.a2dgamedemo.Presentation.Views.IMainBackground
import com.example.a2dgamedemo.Presentation.Views.ITileMap

object ServiceRegistration {
    var tileMap: ITileMap? = null
    var background: IMainBackground? = null

    fun register(startup: StartUp){
        tileMap = TileMap(startup, DisplayHelper.getDensityType(startup))
        background = MainBackground(startup)
    }
}