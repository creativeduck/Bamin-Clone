package com.ssacproject.thirdweek.customview

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager2.widget.ViewPager2
import com.ssacproject.thirdweek.R

class CustomMainMenuAd : ConstraintLayout {
    lateinit var cur : TextView
    lateinit var tot: TextView
    lateinit var viewpager_ad: ViewPager2
    lateinit var show_all: TextView

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
        val view: View = inflater.inflate(R.layout.custom_main_menu_ad, this, false)
        addView(view)

        viewpager_ad = view.findViewById(R.id.viewpager_ad)
        cur = view.findViewById(R.id.cur)
        tot = view.findViewById(R.id.tot)
        show_all = view.findViewById(R.id.show_all)

    }

    private fun getAttrs(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomMainMenuAd)
        setAttrs(typedArray)
    }

    private fun getAttrs(attrs: AttributeSet?, delStyleAttrs: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomMainMenuAd)
        setAttrs(typedArray)
    }

    private fun setAttrs(typedArray: TypedArray) {
        cur.text = typedArray.getText(R.styleable.CustomMainMenuAd_custom_main_menu_cur)
        tot.text = typedArray.getText(R.styleable.CustomMainMenuAd_custom_main_menu_tot)
    }


}