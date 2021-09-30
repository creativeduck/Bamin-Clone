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

class AddressItem : ConstraintLayout {
    lateinit var address_item_image: ImageView
    lateinit var address_item_title: TextView
    lateinit var address_item_content: TextView

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
        val view: View = inflater.inflate(R.layout.item_address, this, false)
        addView(view)

        address_item_image = view.findViewById(R.id.address_item_image)
        address_item_title = view.findViewById(R.id.address_item_title)
        address_item_content = view.findViewById(R.id.address_item_content)
    }

    private fun getAttrs(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.AddressItem)
        setAttrs(typedArray)
    }

    private fun getAttrs(attrs: AttributeSet?, delStyleAttrs: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.AddressItem)
        setAttrs(typedArray)
    }

    private fun setAttrs(typedArray: TypedArray) {
        address_item_image.setImageResource(typedArray.getResourceId(R.styleable.AddressItem_address_item_image, R.drawable.icon_image))
        address_item_title.text = typedArray.getText(R.styleable.AddressItem_address_item_title)
        address_item_content.text = typedArray.getText(R.styleable.AddressItem_address_item_content)
    }

}