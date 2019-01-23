package com.example.mathersmobile

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView

class GameSum(ctx: Context?, goal: Int, val index: Int) : RelativeLayout(ctx) {
    private var textView: TextView? = null
    private var imageView: ImageView? = null
    var currentSum = 0
    private var goalSum = goal
    private var completed = false

    init {
        textView = TextView(ctx)
        imageView = ImageView(ctx)

        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f)
        layoutParams = params

        val textStr = currentSum.toString() + " / " + goalSum.toString()
        textView?.text = textStr
        textView?.layoutParams = params
        textView?.gravity = Gravity.CENTER
        textView?.textSize = 15f
        textView?.setTextColor(Color.parseColor("#ffffff"))

        imageView?.layoutParams = params
        imageView?.setImageResource(R.drawable.uncompleted)

        addView(imageView)
        addView(textView)
    }
    private fun checkCompleted(){
        if(currentSum == goalSum){
            if(!completed) {
                completed = true
                imageView?.setImageResource(R.drawable.completed)
            }
        }
        else{
            if(completed){
                completed = false
                imageView?.setImageResource(R.drawable.uncompleted)
            }
        }
    }
    fun changeValue(value:Int){
        currentSum += value
        val textStr = currentSum.toString() + " / " + goalSum.toString()
        textView?.text = textStr

        checkCompleted()
    }
    fun setFontSize(size:Float){
        textView?.textSize = size
    }
}