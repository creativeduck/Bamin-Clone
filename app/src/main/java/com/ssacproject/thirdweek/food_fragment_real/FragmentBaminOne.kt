package com.ssacproject.thirdweek.food_fragment_real

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.ssacproject.thirdweek.*
import com.ssacproject.thirdweek.activity.SelectMenuActivity
import com.ssacproject.thirdweek.customadapter.CustomMainMenuAdAdapter
import com.ssacproject.thirdweek.customadapter.CustomTodaySaleAdapter
import com.ssacproject.thirdweek.customadapter.ItemTodaySale
import com.ssacproject.thirdweek.customview.CustomMainMenuAd
import com.ssacproject.thirdweek.customview.CustomMainRecyclerView

class FragmentBaminOne : Fragment() {
    lateinit var customMainMenuAdBaminOne: CustomMainMenuAd
    lateinit var custom1: CustomMainRecyclerView
    lateinit var custom2: CustomMainRecyclerView
    lateinit var handler: Handler
    lateinit var handRun: Runnable

    val imageList = listOf(R.drawable.ad1, R.drawable.ad2, R.drawable.ad3, R.drawable.ad4, R.drawable.ad5, R.drawable.ad6)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_baminone, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        customMainMenuAdBaminOne = view.findViewById(R.id.customMainMenuAdBaminOne)
        val customAdAdapter = CustomMainMenuAdAdapter()
        customAdAdapter.imageList = imageList
        customAdAdapter.setShowAll = true
        val len = customAdAdapter.imageList.size
        customMainMenuAdBaminOne.tot.text = "${len}"
        if (len > 0) {
            customMainMenuAdBaminOne.cur.text = "${1}"
        } else {
            customMainMenuAdBaminOne.cur.text = "${0}"
        }
        customMainMenuAdBaminOne.viewpager_ad.adapter = customAdAdapter
        // 핸들러 설정해서 자동으로 스와이프
        handler = Handler()
        handRun = Runnable {
            customMainMenuAdBaminOne.viewpager_ad.currentItem = customMainMenuAdBaminOne.viewpager_ad.currentItem + 1
        }
        // 뷰페이저 스와이프할 때마다 숫자 바꿔주기
        customMainMenuAdBaminOne.viewpager_ad.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(handRun)
                handler.postDelayed(handRun, 3000)
                customMainMenuAdBaminOne.tot.text = "${len}"
                val now = position%len + 1
                customMainMenuAdBaminOne.cur.text ="${now}"
            }
        })

        custom1 = view.findViewById(R.id.custom1)
        val layoutManager1 = LinearLayoutManager(context)
        layoutManager1.orientation = LinearLayoutManager.HORIZONTAL
        custom1.recycler.layoutManager = layoutManager1
        val customAdapter1 = CustomTodaySaleAdapter(requireContext())
        val data1 = ArrayList<ItemTodaySale>()
        data1.add(ItemTodaySale(R.drawable.logo_dominos, "도미노피자 전 메뉴", "7천원 할인", "기간:9/13~9/23", "", R.drawable.today_sale_food_domino, R.color.mint))
        data1.add(ItemTodaySale(R.drawable.logo_dominos, "도미노피자 전 메뉴", "7천원 할인", "기간:9/13~9/23", "", R.drawable.today_sale_food_domino, R.color.basy))
        data1.add(ItemTodaySale(R.drawable.logo_dominos, "도미노피자 전 메뉴", "7천원 할인", "기간:9/13~9/23", "", R.drawable.today_sale_food_domino, R.color.mint))
        data1.add(ItemTodaySale(R.drawable.logo_dominos, "도미노피자 전 메뉴", "7천원 할인", "기간:9/13~9/23", "", R.drawable.today_sale_food_domino, R.color.skyblue))

        customAdapter1.todaySaleList = data1
        custom1.recycler.adapter = customAdapter1


        custom2 = view.findViewById(R.id.custom2)
        val layoutManager2 = LinearLayoutManager(context)
        layoutManager2.orientation = LinearLayoutManager.VERTICAL
        custom2.recycler.layoutManager = layoutManager2
        val customAdapter2 = CustomTodaySaleAdapter(requireContext())
        val data2 = ArrayList<ItemTodaySale>()
        data2.add(ItemTodaySale(R.drawable.logo_dominos, "도미노피자 전 메뉴", "7천원 할인", "기간:9/13~9/23", "", R.drawable.today_sale_food_domino, R.color.skyblue))
        data2.add(ItemTodaySale(R.drawable.logo_dominos, "도미노피자 전 메뉴", "7천원 할인", "기간:9/13~9/23", "", R.drawable.today_sale_food_domino, R.color.basy))
        data2.add(ItemTodaySale(R.drawable.logo_dominos, "도미노피자 전 메뉴", "7천원 할인", "기간:9/13~9/23", "", R.drawable.today_sale_food_domino, R.color.mint))
        data2.add(ItemTodaySale(R.drawable.logo_dominos, "도미노피자 전 메뉴", "7천원 할인", "기간:9/13~9/23", "", R.drawable.today_sale_food_domino, R.color.skyblue))

        customAdapter2.todaySaleList = data2
        custom2.recycler.adapter = customAdapter2

        val food_grid_baminone: GridLayout = view.findViewById(R.id.food_grid_baminone)
        for (i in 1 until food_grid_baminone.childCount-1) {
            var grid = food_grid_baminone.getChildAt(i)
            grid.setOnClickListener {
                val intent = Intent(context, SelectMenuActivity::class.java)
                intent.putExtra("position", i-1)
                intent.putExtra("what", 1)
                startActivity(intent)
            }
        }
    }
    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(handRun)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(handRun, 3000)
    }
}