package com.ssacproject.thirdweek.customview

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.imageview.ShapeableImageView
import com.ssacproject.thirdweek.R

class CustomFastestItem : ConstraintLayout {
    lateinit var custom_fastest_image: ShapeableImageView
    lateinit var custom_fastest_rating: TextView
    lateinit var custom_fastest_tips: TextView
    lateinit var custom_fastest_time: TextView
    lateinit var custom_fastest_title: TextView

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
        val view: View = inflater.inflate(R.layout.custom_fastest_food_item, this, false)
        addView(view)

        custom_fastest_image = view.findViewById(R.id.custom_fastest_image)
        custom_fastest_rating = view.findViewById(R.id.custom_fastest_rating)
        custom_fastest_tips = view.findViewById(R.id.custom_fastest_tips)
        custom_fastest_time = view.findViewById(R.id.custom_fastest_time)
        custom_fastest_title = view.findViewById(R.id.custom_fastest_title)
    }

    private fun getAttrs(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomFastestItem)
        setAttrs(typedArray)
    }

    private fun getAttrs(attrs: AttributeSet?, delStyleAttrs: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomFastestItem)
        setAttrs(typedArray)
    }

    private fun setAttrs(typedArray: TypedArray) {
        custom_fastest_image.setImageResource(typedArray.getResourceId(R.styleable.CustomFastestItem_custom_fastest_image, R.drawable.food_item_one))
        custom_fastest_rating.text = typedArray.getText(R.styleable.CustomFastestItem_custom_fastest_rating)
        custom_fastest_tips.text = typedArray.getText(R.styleable.CustomFastestItem_custom_fastest_tips)
        custom_fastest_time.text = typedArray.getText(R.styleable.CustomFastestItem_custom_fastest_time)
        custom_fastest_title.text = typedArray.getText(R.styleable.CustomFastestItem_custom_fastest_title)
    }


}