package com.example.a2dgamedemo.Presentation.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.a2dgamedemo.Presentation.Classes.GamePanel.GamePanel

class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gamePanel: GamePanel = GamePanel(this)
        setContentView(gamePanel)
    }
}