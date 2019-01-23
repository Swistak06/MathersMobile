package com.example.mathersmobile

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView

class GameButton(game: GameFragment?, val value: Int, val column: Int, val row: Int) : RelativeLayout(game?.context) {
    private var textView: TextView? = null
    private var imageView: ImageView? = null
    private var slected = false

    init {
        textView = TextView(game?.context)
        imageView = ImageView(game?.context)

        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f)
        layoutParams = params

        textView?.text = value.toString()
        textView?.layoutParams = params
        textView?.gravity = Gravity.CENTER
        textView?.textSize = 30f
        textView?.setTextColor(Color.parseColor("#ffffff"))

        imageView?.layoutParams = params
        imageView?.setImageResource(R.drawable.unselected)

        addView(imageView)
        addView(textView)

        setOnClickListener {
            if (slected){
                slected = false
                imageView?.setImageResource(R.drawable.unselected)
                game?.changeGameSum(column, row, -value)
            }
            else{
                slected = true
                imageView?.setImageResource(R.drawable.selected)
                game?.changeGameSum(column, row, value)
            }

        }
    }

}