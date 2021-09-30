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

class GridItem : ConstraintLayout {
    lateinit var gridImage : ImageView
    lateinit var gridText: TextView

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
        val view: View = inflater.inflate(R.layout.grid_item, this, false)
        addView(view)

        gridImage = view.findViewById(R.id.gridImage)
        gridText = view.findViewById(R.id.gridText)
    }

    private fun getAttrs(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.GridItem)
        setAttrs(typedArray)
    }

    private fun getAttrs(attrs: AttributeSet?, delStyleAttrs: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.GridItem)
        setAttrs(typedArray)
    }

    private fun setAttrs(typedArray: TypedArray) {
        gridImage.setImageResource(typedArray.getResourceId(
            R.styleable.GridItem_grid_image,
            R.drawable.icon_image
        ))
        gridText.text = typedArray.getText(R.styleable.GridItem_grid_text)

    }

}