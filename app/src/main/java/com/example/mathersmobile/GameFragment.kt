package com.example.mathersmobile

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.fragment_game.view.*

class GameFragment : Fragment() {
    private var listener: GameFragmentListener? = null
    private var min = 2
    private var max = 4
    private var size = 4

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
            listener?.backToMenuListener()
            //TODO: reset and stop the timer

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

    fun setMinMax(min:Int, max:Int){
        this.min = min
        this.max = max
    }

    fun setSize(size:Int){
        this.size = size
    }
    fun generateButtons(view: View){
        clearLayouts()
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f)
        for(i in 1..size){
            val horizLay = LinearLayout(context)
            horizLay.orientation = LinearLayout.HORIZONTAL
            horizLay.layoutParams = params
            for(j in 1..size){
                val gameButton = GameButton(this, 10, j-1, i-1)
                gameButtons.add(gameButton)
                horizLay.addView(gameButton)
            }
            view.linearLayout.addView(horizLay)
        }
        for(i in 1..size){
            val leftGameSum = GameSum(context, 10, i-1)
            leftGameSums.add(leftGameSum)
            view.leftSumLayout.addView(leftGameSum)

            val topGameSum = GameSum(context, 10, i-1)
            topGameSums.add(topGameSum)
            view.topSumsLayout.addView(topGameSum)
        }
    }
    fun clearLayouts(){
        leftGameSums.clear()
        topGameSums.clear()
        gameButtons.clear()
    }
    fun changeGameSum(column:Int, row:Int, value:Int){
        val leftSum = findLeftSum(row)
        val topSum = findTopSum(column)

        leftSum?.changeValue(value)
        topSum?.changeValue(value)

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
