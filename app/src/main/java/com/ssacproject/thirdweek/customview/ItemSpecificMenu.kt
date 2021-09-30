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

class ItemSpecificMenu : ConstraintLayout {
    lateinit var item_specific_menu_title: TextView
    lateinit var item_specific_menu_detail: TextView
    lateinit var item_specific_menu_price: TextView
    lateinit var item_specificc_menu_image: ShapeableImageView

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
        val view: View = inflater.inflate(R.layout.item_specific_menu, this, false)
        addView(view)

    }

    private fun getAttrs(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ItemSpecificMenu)
        setAttrs(typedArray)
    }

    private fun getAttrs(attrs: AttributeSet?, delStyleAttrs: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ItemSpecificMenu)
        setAttrs(typedArray)
    }

    private fun setAttrs(typedArray: TypedArray) {
        item_specific_menu_title.text = typedArray.getText(R.styleable.ItemSpecificMenu_item_specific_menu_title)
        item_specific_menu_detail.text = typedArray.getText(R.styleable.ItemSpecificMenu_item_specific_menu_detail)
        item_specific_menu_price.text = typedArray.getText(R.styleable.ItemSpecificMenu_item_specific_menu_price)
        item_specificc_menu_image.setImageResource(typedArray.getResourceId(R.styleable.ItemSpecificMenu_item_specific_menu_image, R.drawable.icon_image))
    }

}