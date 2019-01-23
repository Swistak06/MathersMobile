package com.example.mathersmobile

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_main_menu.view.*
import android.R.attr.button
import kotlinx.android.synthetic.main.fragment_main_menu.*

class MainMenuFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var listener: MainMenuListener? = null
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
        val view = inflater.inflate(R.layout.fragment_main_menu, container, false)
        min = 2
        max = 4
        size = 4
        view.startButton.setOnClickListener {
            listener?.startButtonOnClick(min, max, size)
        }
        view.infoButton.setOnClickListener {
            listener?.infoButtonOnClick()
        }
        setRangeListeners(view)
        setSizeListeners(view)
        return view
    }

    private fun setRangeListeners(view: View) {
        view.firstRange.setOnClickListener {
            val color = (it.background as ColorDrawable).color
            if(color != R.color.menuSelected){
                this.min = 2
                this.max = 4
                it.setBackgroundResource(R.color.menuSelected)
                secondRange.setBackgroundResource(R.color.menuUnselected)
                thirdRange.setBackgroundResource(R.color.menuUnselected)
                fourthRange.setBackgroundResource(R.color.menuUnselected)
            }
        }
        view.secondRange.setOnClickListener {
            val color = (it.background as ColorDrawable).color
            if(color != R.color.menuSelected){
                this.min = 2
                this.max = 9
                firstRange.setBackgroundResource(R.color.menuUnselected)
                it.setBackgroundResource(R.color.menuSelected)
                thirdRange.setBackgroundResource(R.color.menuUnselected)
                fourthRange.setBackgroundResource(R.color.menuUnselected)
            }
        }
        view.thirdRange.setOnClickListener {
            val color = (it.background as ColorDrawable).color
            if (color != R.color.menuSelected) {
                this.min = 5
                this.max = 14
                firstRange.setBackgroundResource(R.color.menuUnselected)
                secondRange.setBackgroundResource(R.color.menuUnselected)
                it.setBackgroundResource(R.color.menuSelected)
                fourthRange.setBackgroundResource(R.color.menuUnselected)
            }
        }
        view.fourthRange.setOnClickListener {
            val color = (it.background as ColorDrawable).color
            if (color != R.color.menuSelected) {
                this.min = 9
                this.max = 22
                firstRange.setBackgroundResource(R.color.menuUnselected)
                secondRange.setBackgroundResource(R.color.menuUnselected)
                thirdRange.setBackgroundResource(R.color.menuUnselected)
                it.setBackgroundResource(R.color.menuSelected)
            }
        }
    }

    private fun setSizeListeners(view: View): Unit {
        view.size4Button.setOnClickListener {
            val color = (it.background as ColorDrawable).color
            if(color != R.color.menuSelected){
                this.size = 4
                it.setBackgroundResource(R.color.menuSelected)
                size5Button.setBackgroundResource(R.color.menuUnselected)
                size6Button.setBackgroundResource(R.color.menuUnselected)
                size7Button.setBackgroundResource(R.color.menuUnselected)
            }
        }
        view.size5Button.setOnClickListener {
            val color = (it.background as ColorDrawable).color
            if(color != R.color.menuSelected){
                this.size = 5
                size4Button.setBackgroundResource(R.color.menuUnselected)
                it.setBackgroundResource(R.color.menuSelected)
                size6Button.setBackgroundResource(R.color.menuUnselected)
                size7Button.setBackgroundResource(R.color.menuUnselected)
            }
        }
        view.size6Button.setOnClickListener {
            val color = (it.background as ColorDrawable).color
            if(color != R.color.menuSelected){
                this.size = 6
                size4Button.setBackgroundResource(R.color.menuUnselected)
                size5Button.setBackgroundResource(R.color.menuUnselected)
                it.setBackgroundResource(R.color.menuSelected)
                size7Button.setBackgroundResource(R.color.menuUnselected)
            }
        }
        view.size7Button.setOnClickListener {
            val color = (it.background as ColorDrawable).color
            if(color != R.color.menuSelected){
                this.size = 7
                size4Button.setBackgroundResource(R.color.menuUnselected)
                size5Button.setBackgroundResource(R.color.menuUnselected)
                size6Button.setBackgroundResource(R.color.menuUnselected)
                it.setBackgroundResource(R.color.menuSelected)
            }
        }
    }




    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainMenuListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface MainMenuListener {
        fun startButtonOnClick(min:Int, max:Int, size:Int)
        fun infoButtonOnClick()
    }

}
