package com.example.mathersmobile

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_end_game.view.*


class EndGameFragment : Fragment() {


    var activity : Activity? = null
    var score = ScoreBoard()
    var timeMinutes = 0
    var timeSeconds = 0
    var size = 0
    var max = 0


    private var listener: EndGameFragmentListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_end_game, container, false)
        score.setScoreActivity(activity!!)
        score.updateScoreBoard(size ,max)
        view.timeTextView.text = timeMinutes.toString() + ":" + timeSeconds.toString()
        updateScoreBoard(view)
        view.backToMenuButton.setOnClickListener {
            listener?.backToMainMenu()
        }
        return view
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is EndGameFragmentListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface EndGameFragmentListener{
        fun backToMainMenu()
    }

    fun setFragmentActivity(activity: Activity){
        this.activity = activity
    }

    fun setTimeOfGame(minutes : Int, seconds : Int){
        timeMinutes = minutes
        timeSeconds = seconds
    }

    fun setGameMode(size : Int, max : Int){
        this.size = size
        this.max = max
    }

    fun updateScoreBoard(view: View){
        view.r40textView.text =view.r40textView.text.toString() + " " + score.sizeFour[0].toString()
        view.r41textView.text =view.r41textView.text.toString() + " " +  score.sizeFour[1].toString()
        view.r42textView.text =view.r42textView.text.toString() + " " + score.sizeFour[2].toString()
        view.r43textView.text =view.r43textView.text.toString() + " " + score.sizeFour[3].toString()

        view.r50textView.text =view.r50textView.text.toString() + " " + score.sizeFive[0].toString()
        view.r51textView.text =view.r51textView.text.toString() + " " + score.sizeFive[1].toString()
        view.r52textView.text =view.r52textView.text.toString() + " " + score.sizeFive[2].toString()
        view.r53textView.text =view.r53textView.text.toString() + " " + score.sizeFive[3].toString()

        view.r60textView.text =view.r60textView.text.toString() + " " + score.sizeSix[0].toString()
        view.r61textView.text =view.r61textView.text.toString() + " " + score.sizeSix[1].toString()
        view.r62textView.text =view.r62textView.text.toString() + " " + score.sizeSix[2].toString()
        view.r63textView.text =view.r63textView.text.toString() + " " + score.sizeSix[3].toString()

        view.r70textView.text =view.r70textView.text.toString() + " " + score.sizeSeven[0].toString()
        view.r71textView.text =view.r71textView.text.toString() + " " + score.sizeSeven[1].toString()
        view.r72textView.text =view.r72textView.text.toString() + " " + score.sizeSeven[2].toString()
        view.r73textView.text =view.r73textView.text.toString() + " " + score.sizeSeven[3].toString()
    }

}
