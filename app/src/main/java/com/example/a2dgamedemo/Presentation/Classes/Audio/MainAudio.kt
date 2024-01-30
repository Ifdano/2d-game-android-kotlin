package com.example.a2dgamedemo.Presentation.Classes.Audio

import android.content.Context
import android.media.MediaPlayer
import com.example.a2dgamedemo.Enums.SoundType
import com.example.a2dgamedemo.Presentation.Views.IMainAudioView
import com.example.a2dgamedemo.R

class MainAudio(soundType: SoundType, context: Context) :IMainAudioView {
    private var mediaPlayer: MediaPlayer? = null
    private var soundType: SoundType
    private var context: Context

    init {
        this.soundType = soundType
        this.context = context

        createAudio()
        /*if (mediaPlayer == null) {
            when(soundType){
                SoundType.MAIN_THEME -> mediaPlayer = MediaPlayer.create(context, R.raw.gametheme)
                SoundType.JUMP -> mediaPlayer = MediaPlayer.create(context, R.raw.jump)
            }
        }*/
    }

    private fun createAudio(){
        if (mediaPlayer == null) {
            mediaPlayer = when(soundType){
                SoundType.MAIN_THEME -> MediaPlayer.create(context, R.raw.gametheme)
                SoundType.JUMP -> MediaPlayer.create(context, R.raw.jump)
            }
        }
    }

    override fun play() {
        if(mediaPlayer == null) return

        stop()

        mediaPlayer?.reset()
        mediaPlayer = null

        createAudio()
        mediaPlayer?.start()
    }

    private fun stop(){
        mediaPlayer?.stop()
    }
}