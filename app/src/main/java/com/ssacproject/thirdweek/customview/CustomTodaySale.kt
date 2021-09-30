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

class CustomTodaySale : ConstraintLayout {
    lateinit var today_sale_logo: ImageView
    lateinit var today_sale_condition: TextView
    lateinit var today_sale_sale: TextView
    lateinit var today_sale_content1: TextView
    lateinit var today_sale_content2: TextView
    lateinit var today_sale_image: ImageView

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
        val view: View = inflater.inflate(R.layout.custom_today_sale, this, false)
        addView(view)

        today_sale_logo = view.findViewById(R.id.today_sale_logo)
        today_sale_condition = view.findViewById(R.id.today_sale_condition)
        today_sale_sale = view.findViewById(R.id.today_sale_sale)
        today_sale_content1 = view.findViewById(R.id.today_sale_content1)
        today_sale_content2 = view.findViewById(R.id.today_sale_content2)
        today_sale_image = view.findViewById(R.id.today_sale_image)
    }

    private fun getAttrs(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomTodaySale)
        setAttrs(typedArray)
    }

    private fun getAttrs(attrs: AttributeSet?, delStyleAttrs: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomTodaySale)
        setAttrs(typedArray)
    }

    private fun setAttrs(typedArray: TypedArray) {
        today_sale_logo.setImageResource(typedArray.getResourceId(
            R.styleable.CustomTodaySale_today_sale_logo, R.drawable.logo_dominos))
        today_sale_condition.text = typedArray.getText(R.styleable.CustomTodaySale_today_sale_condition)
        today_sale_sale.text = typedArray.getText(R.styleable.CustomTodaySale_today_sale_sale)
        today_sale_content1.text = typedArray.getText(R.styleable.CustomTodaySale_today_sale_content1)
        today_sale_content2.text = typedArray.getText(R.styleable.CustomTodaySale_today_sale_content2)
        today_sale_image.setImageResource(typedArray.getResourceId(
            R.styleable.CustomTodaySale_today_sale_image,
            R.drawable.today_sale_food_domino
        ))
    }

}