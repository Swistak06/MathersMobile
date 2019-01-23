package com.example.mathersmobile

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView

class GameButton: RelativeLayout {
    private var textView: TextView? = null
    private var imageView: ImageView? = null
    private var slected = false

    private constructor(ctx: Context?) : super(ctx)
    constructor(ctx:Context?, text:String) : this(ctx) {
        textView = TextView(ctx)
        imageView = ImageView(ctx)

        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f)
        layoutParams = params

        textView?.text = text
        textView?.layoutParams = params
        textView?.gravity = Gravity.CENTER
        textView?.textSize = 30f
        textView?.setTextColor(Color.parseColor("#ffffff"))

        imageView?.layoutParams = params
        imageView?.setImageResource(R.drawable.unselected70)

        addView(imageView)
        addView(textView)

        setOnClickListener {
            if (slected){
                slected = false
                imageView?.setImageResource(R.drawable.unselected70)
            }
            else{
                slected = true
                imageView?.setImageResource(R.drawable.selected70)
            }

        }
    }

}