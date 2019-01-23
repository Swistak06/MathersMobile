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
        var ids = 100
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f)
        for(i in 1..size){
            val horizLay = LinearLayout(context)
            horizLay.orientation = LinearLayout.HORIZONTAL
            horizLay.layoutParams = params
            horizLay.id = ids + i*10
            for(j in 1..size){
//
//                val button = RelativeLayout(context)
//                button.layoutParams = params
//
//                val img = ImageView(context)
//                img.layoutParams = params
//                img.setImageResource(R.drawable.selected70)
//
//                val textV = TextView(context)
//                textV.layoutParams = params
//                textV.gravity = Gravity.CENTER
//                textV.text = "99"
//                textV.textSize = 30f
//                textV.setTextColor(Color.parseColor("#ffffff"))
//
//                button.addView(img)
//                button.addView(textV)
                val gameButton = GameButton(context, "99")
                horizLay.addView(gameButton)
            }
            view.linearLayout.addView(horizLay)
        }
        for(i in 1..size){
            val leftB = RelativeLayout(context)
            leftB.layoutParams = params

            var img = ImageView(context)
            img.layoutParams = params
            img.setImageResource(R.drawable.selected70)

            var textV = TextView(context)
            textV.layoutParams = params
            textV.gravity = Gravity.CENTER
            textV.text = "99"
            textV.textSize = 30f
            textV.setTextColor(Color.parseColor("#ffffff"))
            leftB.addView(img)
            leftB.addView(textV)
            view.leftSumLayout.addView(leftB)

            val topB = RelativeLayout(context)
            topB.layoutParams = params

            img = ImageView(context)
            img.layoutParams = params
            img.setImageResource(R.drawable.selected70)

            textV = TextView(context)
            textV.layoutParams = params
            textV.gravity = Gravity.CENTER
            textV.text = "99"
            textV.textSize = 30f
            textV.setTextColor(Color.parseColor("#ffffff"))
            topB.addView(img)
            topB.addView(textV)
            view.topSumsLayout.addView(topB)
        }
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
    interface GameFragmentListener {
        fun backToMenuListener()
    }
}
