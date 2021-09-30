package com.ssacproject.thirdweek.customview

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.imageview.ShapeableImageView
import com.ssacproject.thirdweek.R

class SelectMenuFoodItem : ConstraintLayout {

    lateinit var select_menu_food_item_image: ShapeableImageView
    lateinit var select_menu_food_item_title: TextView
    lateinit var select_menu_food_item_rating: TextView
    lateinit var select_menu_food_item_menu: TextView
    lateinit var select_menu_food_item_time: TextView
    lateinit var select_menu_food_item_price: TextView

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
        val view: View = inflater.inflate(R.layout.select_menu_food_item, this, false)
        addView(view)

        select_menu_food_item_image = view.findViewById(R.id.select_menu_food_item_image)
        select_menu_food_item_title = view.findViewById(R.id.select_menu_food_item_title)
        select_menu_food_item_rating = view.findViewById(R.id.select_menu_food_item_rating)
       select_menu_food_item_menu = view.findViewById(R.id.select_menu_food_item_menu)
        select_menu_food_item_time = view.findViewById(R.id.select_menu_food_item_time)
        select_menu_food_item_price = view.findViewById(R.id.select_menu_food_item_price)
    }

    private fun getAttrs(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SelectMenuFoodItem)
        setAttrs(typedArray)
    }

    private fun getAttrs(attrs: AttributeSet?, delStyleAttrs: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SelectMenuFoodItem)
        setAttrs(typedArray)
    }

    private fun setAttrs(typedArray: TypedArray) {
        select_menu_food_item_image.setBackgroundResource(typedArray.getResourceId(
            R.styleable.SelectMenuFoodItem_select_menu_food_item_image, R.drawable.grid_asian))
        select_menu_food_item_title.text = typedArray.getText(R.styleable.SelectMenuFoodItem_select_menu_food_item_title)
        select_menu_food_item_rating.text = typedArray.getText(R.styleable.SelectMenuFoodItem_select_menu_food_item_rating)
        select_menu_food_item_menu.text = typedArray.getText(R.styleable.SelectMenuFoodItem_select_menu_food_item_menu)
        select_menu_food_item_time.text = typedArray.getText(R.styleable.SelectMenuFoodItem_select_menu_food_item_time)
        select_menu_food_item_price.text = typedArray.getText(R.styleable.SelectMenuFoodItem_select_menu_food_item_price)
    }

}