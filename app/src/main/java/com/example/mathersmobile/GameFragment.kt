package com.example.mathersmobile

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.android.synthetic.main.fragment_game.view.*

class GameFragment : Fragment() {
    private var listener: GameFragmentListener? = null
    private var min = 2
    private var max = 4
    private var size = 4

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

    fun setMinMax(min:Int, max:Int){
        this.min = min
        this.max = max
    }

    fun setSize(size:Int){
        this.size = size
    }
    fun generateButtons(view: View){
        var ids = 100
        for(i in 1..size){
            val horizLay = LinearLayout(context)
            horizLay.orientation = LinearLayout.HORIZONTAL
            var params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f)
            horizLay.layoutParams = params
            horizLay.id = ids + i*10
            for(j in 1..size){
                val btn = Button(context)
                btn.layoutParams = params
                btn.text = (i*10 + j).toString()
                horizLay.addView(btn)
            }
            view.linearLayout.addView(horizLay)
        }
    }


    interface GameFragmentListener {
//        fun onFragmentInteraction(uri: Uri)
    }
}
