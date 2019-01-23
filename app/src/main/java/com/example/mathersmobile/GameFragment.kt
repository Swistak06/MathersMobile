package com.example.mathersmobile

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.android.synthetic.main.fragment_game.view.*

class GameFragment : Fragment() {
    private var listener: GameFragmentListener? = null
    private var min = 2
    private var max = 4
    private var size = 4
    private var isTimerRunning = false

    private val ELEMENT_SIZE_4 = 130
    private val BUTTON_FONT_SIZE_4 = 30
    private val SUM_FONT_SIZE_4 = 15

    private val ELEMENT_SIZE_5 = 104
    private val BUTTON_FONT_SIZE_5 = 26
    private val SUM_FONT_SIZE_5 = 13

    private val ELEMENT_SIZE_6 = 88
    private val BUTTON_FONT_SIZE_6 = 22
    private val SUM_FONT_SIZE_6 = 10

    private val ELEMENT_SIZE_7 = 76
    private val BUTTON_FONT_SIZE_7 = 17
    private val SUM_FONT_SIZE_7 = 8

    private var elementsSize = ELEMENT_SIZE_4
    private var buttonFontSize = BUTTON_FONT_SIZE_4
    private var sumFontSize = SUM_FONT_SIZE_4

    val handler = Handler()
    var stopwatch = StopWatch(0,0,0,0)
    var numberGenerator = NumberGenerator()


    private val leftGameSums : MutableList<GameSum> = mutableListOf()
    private val topGameSums : MutableList<GameSum> = mutableListOf()
    private val gameButtons : MutableList<GameButton> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        arguments?.let { }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game, container, false)
        clearLayouts(view)
        updateLayoutSizes(view)
        updateFontSizes()
        generateButtons(view)
        numberGenerator.createBoard(size,min,max)
        view.backBtn.setOnClickListener {
            //resetGameArea()
            listener?.backToMenuListener()
            resetTimer()
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is GameFragmentListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onStart() {
        super.onStart()
        startTimer()
    }

    override fun onStop() {
        super.onStop()
        stopTimer()
    }

    fun setMinMax(min:Int, max:Int){
        this.min = min
        this.max = max
    }

    fun setSize(size:Int){
        this.size = size
    }

    private fun updateLayoutSizes(view: View) {
        elementsSize = when (size) {
            5 -> ELEMENT_SIZE_5
            6 -> ELEMENT_SIZE_6
            7 -> ELEMENT_SIZE_7
            else -> ELEMENT_SIZE_4
        }

        var params = view.leftSumLayout.layoutParams as ConstraintLayout.LayoutParams
        Log.d("width", params.width.toString())
        params.width = elementsSize
        params.leftMargin = 160 - elementsSize
//        params.marginStart = 80 - elementsSize
        view.leftSumLayout.layoutParams = params

        params = view.topSumsLayout.layoutParams as ConstraintLayout.LayoutParams
        params.height = elementsSize
        params.topMargin = 312 - elementsSize
        view.topSumsLayout.layoutParams = params
    }

    private fun updateFontSizes() {
        buttonFontSize = when(size){
            5 -> BUTTON_FONT_SIZE_5
            6 -> BUTTON_FONT_SIZE_6
            7 -> BUTTON_FONT_SIZE_7
            else -> BUTTON_FONT_SIZE_4
        }
        sumFontSize = when(size){
            5 -> SUM_FONT_SIZE_5
            6 -> SUM_FONT_SIZE_6
            7 -> SUM_FONT_SIZE_7
            else -> SUM_FONT_SIZE_4
        }
    }

    fun generateButtons(view: View){
        numberGenerator.createBoard(size,min,max)
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f)
        for(i in 1..size){
            val horizLay = LinearLayout(context)
            horizLay.orientation = LinearLayout.HORIZONTAL
            horizLay.layoutParams = params
            for(j in 1..size){
                val gameButton = GameButton(this, numberGenerator.rowTable[i-1][j-1], j-1, i-1)
                gameButton.setFontSize(buttonFontSize.toFloat())
                gameButtons.add(gameButton)
                horizLay.addView(gameButton)
            }
            view.linearLayout.addView(horizLay)
        }
        for(i in 1..size){
            val leftGameSum = GameSum(context, numberGenerator.targetRowSum[i-1], i-1)
            leftGameSum.setFontSize(sumFontSize.toFloat())
            leftGameSums.add(leftGameSum)
            view.leftSumLayout.addView(leftGameSum)

            val topGameSum = GameSum(context, numberGenerator.targetColumnSum[i-1], i-1)
            topGameSum.setFontSize(sumFontSize.toFloat())
            topGameSums.add(topGameSum)
            view.topSumsLayout.addView(topGameSum)
        }
    }
    fun clearLayouts(view: View?){
        leftGameSums.clear()
        topGameSums.clear()
        gameButtons.clear()

        view?.linearLayout?.removeAllViews()
        view?.topSumsLayout?.removeAllViews()
        view?.leftSumLayout?.removeAllViews()


    }
    fun changeGameSum(column:Int, row:Int, value:Int) {
        val leftSum = findLeftSum(row)
        val topSum = findTopSum(column)
        leftSum?.changeValue(value)
        topSum?.changeValue(value)
    }


    /********************Timer methods********************/
    val runningStopWatch = object: Runnable {
        override fun run() {
            addOneSecond()
            handler.postDelayed(this, 1000)
        }
    }
    fun startTimer(){
        handler.removeCallbacks(runningStopWatch)
        handler.post(runningStopWatch)
        isTimerRunning = true
    }
    fun stopTimer(){
        handler.removeCallbacks(runningStopWatch)
        isTimerRunning = false
    }
    fun resetTimer(){
        stopTimer()
        stopwatch = StopWatch(0,0,0,0)
        secondsView.text = "0"
        tenSecondsView.text = "0"
        minutesView.text = "0"
        tenMinutesView.text = "0"
    }
    fun addOneSecond(){
        stopwatch.seconds++
        if(stopwatch.seconds > 9){
            secondsView.text = "0"
            stopwatch.seconds = 0
            addTenSeconds()
        }
        secondsView.text = stopwatch.seconds.toString()
    }
    fun addTenSeconds(){
        stopwatch.tenSeconds++
        if(stopwatch.tenSeconds>5){
            tenSecondsView.text = "0"
            stopwatch.tenSeconds = 0
            addOneMinute()
        }
        tenSecondsView.text = stopwatch.tenSeconds.toString()


    }
    fun addOneMinute(){
        stopwatch.minutes++
        if(stopwatch.minutes>9){
            minutesView.text = "0"
            stopwatch.minutes = 0
            addTenMinutes()
        }
        minutesView.text = stopwatch.minutes.toString()
    }
    fun addTenMinutes(){
        if(stopwatch.tenMinutes<9){
            stopwatch.tenMinutes++
            tenMinutesView.text = stopwatch.tenMinutes.toString()
        }
    }


    private fun findLeftSum(row:Int) : GameSum?{
        leftGameSums.forEach {
            if(it.index == row)
                return it
        }
        return null
    }
    private fun findTopSum(column:Int) : GameSum?{
        topGameSums.forEach {
            if(it.index == column)
                return it
        }
        return null
    }
    interface GameFragmentListener {
        fun backToMenuListener()
    }

    fun Int.toDp(): Int = (this/ Resources.getSystem().displayMetrics.density).toInt()
}

