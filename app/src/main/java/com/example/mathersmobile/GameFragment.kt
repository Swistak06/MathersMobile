package com.example.mathersmobile

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
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
        generateButtons(view)
        view.backBtn.setOnClickListener {
            //resetGameArea()
            numberGenerator.createBoard(5,2,4)
            listener?.backToMenuListener()
            resetTimer()

        }
        return view
    }

    private fun resetGameArea() {
        //TODO: not implemented
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
    fun generateButtons(view: View){
        clearLayouts()
        numberGenerator.createBoard(size,min,max)
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f)
        for(i in 1..size){
            val horizLay = LinearLayout(context)
            horizLay.orientation = LinearLayout.HORIZONTAL
            horizLay.layoutParams = params
            for(j in 1..size){
                val gameButton = GameButton(this, numberGenerator.rowTable[i-1][j-1], j-1, i-1)
                gameButtons.add(gameButton)
                horizLay.addView(gameButton)
            }
            view.linearLayout.addView(horizLay)
        }
        for(i in 1..size){
            val leftGameSum = GameSum(context, numberGenerator.targetRowSum[i-1], i-1)
            leftGameSums.add(leftGameSum)
            view.leftSumLayout.addView(leftGameSum)

            val topGameSum = GameSum(context, numberGenerator.targetColumnSum[i-1], i-1)
            topGameSums.add(topGameSum)
            view.topSumsLayout.addView(topGameSum)
        }
    }
    fun clearLayouts(){
        leftGameSums.clear()
        topGameSums.clear()
        gameButtons.clear()
    }
    fun changeGameSum(column:Int, row:Int, value:Int) {
        val leftSum = findLeftSum(row)
        val topSum = findTopSum(column)
        leftSum?.changeValue(value)
        topSum?.changeValue(value)
    }
    val runningStopWatch = object: Runnable {
        override fun run() {
            addOneSecond()
            handler.postDelayed(this, 1000)
        }
    }

    fun startTimer(){
        handler.removeCallbacks(runningStopWatch)
        handler.post(runningStopWatch)
    }
    fun stopTimer(){
        handler.removeCallbacks(runningStopWatch)
    }
    fun resetTimer(){
        handler.removeCallbacks(runningStopWatch)
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
}
