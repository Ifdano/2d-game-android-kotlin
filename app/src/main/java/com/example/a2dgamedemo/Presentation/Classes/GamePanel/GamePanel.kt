package com.example.a2dgamedemo.Presentation.Classes.GamePanel

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import com.example.a2dgamedemo.Application.Models.BraidCharacter
import com.example.a2dgamedemo.Application.Models.SonicCharacter
import com.example.a2dgamedemo.Enums.SoundType
import com.example.a2dgamedemo.Presentation.Classes.Audio.MainAudio
import com.example.a2dgamedemo.Presentation.Classes.Characters.Common.MainCharacter
import com.example.a2dgamedemo.Presentation.Classes.Map.TileMap
import com.example.a2dgamedemo.Presentation.Helpers.DisplayHelper
import com.example.a2dgamedemo.Presentation.ServiceRegistration
import com.example.a2dgamedemo.Presentation.Views.Common.IBaseView
import com.example.a2dgamedemo.Presentation.Views.IMainAudio
import com.example.a2dgamedemo.Presentation.Views.IMainBackground
import com.example.a2dgamedemo.Presentation.Views.ITileMap

class GamePanel(context: Context) : View(context), Runnable, OnTouchListener, IBaseView{
    private val context: Context

    private var thread: Thread? = null
    private var isRunning = false

    private lateinit var doubleHandler: Handler
    private var isDoubleClick = false

    private lateinit var paint: Paint

    private val fps = 30
    private val targetTime = 1000/fps

    private var tileMap: ITileMap? = null
    private var background: IMainBackground? = null

    private var braidCharacter: BraidCharacter? = null
    private var sonicCharacter: SonicCharacter? = null

    private lateinit var audio: IMainAudio

    init {
        this.context = context

        setupPanel()
        //initital()
        //bindListeners()
    }

    private fun initital(){
        background = ServiceRegistration.background
        tileMap = ServiceRegistration.tileMap
        audio = MainAudio(SoundType.MAIN_THEME, context)

        braidCharacter = BraidCharacter(context, tileMap as TileMap, DisplayHelper.getDensityType(context), DisplayHelper.getDensity())
        braidCharacter?.setPosition(
            DisplayHelper.getDensityType(context).value.toFloat() + 150f,
            DisplayHelper.getDensityType(context).value.toFloat() + 50f)

        sonicCharacter = SonicCharacter(context, tileMap as TileMap, DisplayHelper.getDensityType(context), DisplayHelper.getDensity())
        sonicCharacter?.setPosition(
            DisplayHelper.getDensityType(context).value.toFloat() + 350f,
            DisplayHelper.getDensityType(context).value.toFloat() + 50f)

        paint = Paint()
    }

    private fun bindListeners(){
        setOnTouchListener(this)

        tileMap?.loadMap()
        tileMap?.loadTile()

        audio.play()

        isRunning = true
    }

    private fun setupPanel(){
        isFocusable = true
        requestFocus()

        doubleHandler = Handler()

        addNotify()
    }

    private fun addNotify(){
        if(thread == null){
            thread = Thread(this)
            thread!!.start()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if(isRunning){
            background?.draw(canvas!!, paint)

            tileMap?.draw(canvas!!, paint)

            braidCharacter?.draw(canvas!!, paint)
            sonicCharacter?.draw(canvas!!, paint)
        }
    }

    override fun run() {
        initital()
        bindListeners()

        var startTime = 0L
        var upgradeTime = 0L
        var waitTime = 0L

        while(isRunning){
            startTime = System.nanoTime()

            update()
            postInvalidate()

            upgradeTime = (System.nanoTime() - startTime)/1000000
            waitTime = targetTime - upgradeTime

            try{
                Thread.sleep(waitTime)
            }catch (exp: Exception){}

        }
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        val tempX = event!!.x
        val tempY = event.y

        val tx = tileMap!!.getX()

        when(event?.action){
            MotionEvent.ACTION_DOWN -> {
                if (isDoubleClick) {
                    toggleReverse(sonicCharacter as MainCharacter)
                    toggleReverse(braidCharacter as MainCharacter)
                } else {
                    isDoubleClick = true
                    doubleHandler.postDelayed(checkDouble, 200)

                    // Character selection
                    val selectedCharacter: MainCharacter = getSelectedCharacter(tempX, tempY)
                    sonicCharacter?.setCurrentFocus(selectedCharacter is SonicCharacter)
                    braidCharacter?.setCurrentFocus(selectedCharacter is BraidCharacter)

                    // Character movement and actions
                    if (sonicCharacter!!.getCurrentFocus()) {
                        handleCharacterMovement(sonicCharacter, tempX, tempY)
                    } else if (braidCharacter!!.getCurrentFocus()) {
                        handleCharacterMovement(braidCharacter, tempX, tempY)
                    }
                }
            }
            MotionEvent.ACTION_UP -> {}
        }

        return true
    }

    override fun update() {
        tileMap?.update()
        background?.update(tileMap!!.getX().toFloat())
        braidCharacter?.update()
        sonicCharacter?.update()
    }

    override fun draw(canvas: Canvas, paint: Paint) {
        TODO("Not yet implemented")
    }

    /*var checkDouble: Runnable = object : Runnable {
        override fun run() {
            isDoubleClick = false
            doubleHandler.removeCallbacks(this)
        }
    }*/

    private var checkDouble: Runnable = Runnable {
        isDoubleClick = false
        doubleHandler.removeCallbacks(this)
    }

    private fun toggleReverse(character: MainCharacter){
        if(character.getCurrentFocus()){
            character.setReverse(!character.isReverse())
        }
    }

    private fun getSelectedCharacter(x: Float, y: Float, tx: Float, ty: Float,
                                     braid: BraidCharacter, sonic: SonicCharacter) : MainCharacter{
        if(isInsideBounds(x, y, tx + sonic)){

        }else if(){}
    }

    private fun isInsideBounds(x: Float, y: Float, rectX: Float, rectY: Float,
                               rectWidth: Float, rectHeight: Float) =
        x >= rectX - rectWidth/2 && x <= rectX + rectWidth/2 &&
        y >= rectY - rectHeight/2 && y <= rectY + rectHeight/2
}