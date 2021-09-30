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

class CustomMainMenu : ConstraintLayout {

    lateinit var custom_main_menu_title: TextView
    lateinit var custom_main_menu_content: TextView
    lateinit var custom_main_menu_img: ImageView
    lateinit var custom_main_menu_background: ImageView

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
        val view: View = inflater.inflate(R.layout.custom_main_menu, this, false)
        addView(view)

        custom_main_menu_title = view.findViewById(R.id.custom_main_menu_title)
        custom_main_menu_content = view.findViewById(R.id.custom_main_menu_content)
        custom_main_menu_img = view.findViewById(R.id.custom_main_menu_img)
        custom_main_menu_background = view.findViewById(R.id.custom_main_menu_background)
    }

    private fun getAttrs(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomMainMenu)
        setAttrs(typedArray)
    }

    private fun getAttrs(attrs: AttributeSet?, delStyleAttrs: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomMainMenu)
        setAttrs(typedArray)
    }

    private fun setAttrs(typedArray: TypedArray) {
        custom_main_menu_title.text = typedArray.getText(R.styleable.CustomMainMenu_title)
        custom_main_menu_content.text = typedArray.getText(R.styleable.CustomMainMenu_this_content)
        custom_main_menu_img.setImageResource(typedArray.getResourceId(
            R.styleable.CustomMainMenu_img,
            R.drawable.icon_image
        ))
        custom_main_menu_background.setImageResource(typedArray.getResourceId(
            R.styleable.CustomMainMenu_back,
            R.drawable.icon_image
        ))
    }

}