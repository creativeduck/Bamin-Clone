package com.ssacproject.thirdweek.customview

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.ssacproject.thirdweek.R

class CustomMainRecyclerView : ConstraintLayout {

    lateinit var title: TextView
    lateinit var recycler: RecyclerView
    lateinit var showmore: TextView
    lateinit var recycler_image: ImageView

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
        val view: View = inflater.inflate(R.layout.custom_item_scroll, this, false)
        addView(view)

        title = view.findViewById(R.id.title)
        recycler = view.findViewById(R.id.recycler)
        showmore = view.findViewById(R.id.showmore)
        recycler_image = view.findViewById(R.id.recycler_image)
    }

    private fun getAttrs(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomMainRecyclerView)
        setAttrs(typedArray)
    }

    private fun getAttrs(attrs: AttributeSet?, delStyleAttrs: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomMainRecyclerView)
        setAttrs(typedArray)
    }

    private fun setAttrs(typedArray: TypedArray) {
        title.text = typedArray.getText(R.styleable.CustomMainRecyclerView_recycler_title)
        showmore.text = typedArray.getText(R.styleable.CustomMainRecyclerView_showmore)
        recycler_image.setImageResource(typedArray.getResourceId(R.styleable.CustomMainRecyclerView_recycler_image, R.drawable.icon_end_fastest))
    }

}