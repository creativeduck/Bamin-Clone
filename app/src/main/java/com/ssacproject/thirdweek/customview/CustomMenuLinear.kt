package com.ssacproject.thirdweek.customview

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.ssacproject.thirdweek.R

class CustomMenuLinear : ConstraintLayout {
    lateinit var custom_menu_linear_title: TextView

    constructor(context: Context) : super(context) {
        init(context, null, 0)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs, 0)
        getAttrs(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, delStyleAttrs: Int)
            : super(context, attrs, delStyleAttrs) {
        init(context, attrs, delStyleAttrs)
        getAttrs(attrs, delStyleAttrs)
    }

    private fun init(context: Context, attrs: AttributeSet?, delStyleAttrs: Int) {
        val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.custom_menu_linear, this, false)
        addView(view)

        custom_menu_linear_title = view.findViewById(R.id.custom_menu_linear_title)
    }

    private fun getAttrs(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomMenuLinear)
        setAttrs(typedArray)
    }

    private fun getAttrs(attrs: AttributeSet?, delStyleAttrs: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomMenuLinear)
        setAttrs(typedArray)
    }

    private fun setAttrs(typedArray: TypedArray) {
        custom_menu_linear_title.text = typedArray.getText(R.styleable.CustomMenuLinear_custom_menu_linear_title)
    }

}