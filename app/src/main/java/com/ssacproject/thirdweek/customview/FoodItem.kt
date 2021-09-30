package com.ssacproject.thirdweek.customview

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.ssacproject.thirdweek.R

class FoodItem : CardView {
    lateinit var food_item_image: ImageView
    lateinit var food_item_title: TextView
    lateinit var food_item_time: TextView
    lateinit var food_item_menu: TextView

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
        val view: View = inflater.inflate(R.layout.food_item, this, false)
        addView(view)

        food_item_image = view.findViewById(R.id.food_item_image)
        food_item_title = view.findViewById(R.id.food_item_title)
        food_item_time = view.findViewById(R.id.food_item_time)
        food_item_menu = view.findViewById(R.id.food_item_menu)
    }

    private fun getAttrs(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.FoodItem)
        setAttrs(typedArray)
    }

    private fun getAttrs(attrs: AttributeSet?, delStyleAttrs: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.FoodItem)
        setAttrs(typedArray)
    }

    private fun setAttrs(typedArray: TypedArray) {
        food_item_image.setImageResource(typedArray.getResourceId(
            R.styleable.FoodItem_food_item_image,
            R.drawable.food_item_one
        ))
        food_item_title.text = typedArray.getText(R.styleable.FoodItem_food_item_title)
        food_item_time.text = typedArray.getText(R.styleable.FoodItem_food_item_time)
        food_item_menu.text = typedArray.getText(R.styleable.FoodItem_food_item_menu)
    }

}