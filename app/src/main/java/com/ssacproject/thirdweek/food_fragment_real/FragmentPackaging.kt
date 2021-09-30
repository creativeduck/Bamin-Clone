package com.ssacproject.thirdweek.food_fragment_real

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.ssacproject.thirdweek.*
import com.ssacproject.thirdweek.customadapter.CustomMainMenuAdAdapter
import com.ssacproject.thirdweek.customadapter.CustomTodaySaleAdapter
import com.ssacproject.thirdweek.customadapter.ItemTodaySale
import com.ssacproject.thirdweek.customview.CustomMainMenuAd
import com.ssacproject.thirdweek.customview.CustomMainRecyclerView

class FragmentPackaging : Fragment() {
    lateinit var customMainMenuAdPackaging: CustomMainMenuAd
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
        return inflater.inflate(R.layout.fragment_packaing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        customMainMenuAdPackaging = view.findViewById(R.id.customMainMenuAdPackaging)
        val customAdAdapter = CustomMainMenuAdAdapter()
        customAdAdapter.imageList = imageList
        customAdAdapter.setShowAll = true
        val len = customAdAdapter.imageList.size
        customMainMenuAdPackaging.tot.text = "${len}"
        if (len > 0) {
            customMainMenuAdPackaging.cur.text = "${1}"
        } else {
            customMainMenuAdPackaging.cur.text = "${0}"
        }
        customMainMenuAdPackaging.viewpager_ad.adapter = customAdAdapter
        // 핸들러 설정해서 자동으로 스와이프
        handler = Handler()
        handRun = Runnable {
            customMainMenuAdPackaging.viewpager_ad.currentItem = customMainMenuAdPackaging.viewpager_ad.currentItem + 1
        }
        // 뷰페이저 스와이프할 때마다 숫자 바꿔주기
        customMainMenuAdPackaging.viewpager_ad.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(handRun)
                handler.postDelayed(handRun, 3000)
                customMainMenuAdPackaging.tot.text = "${len}"
                val now = position%len + 1
                customMainMenuAdPackaging.cur.text ="${now}"
            }
        })

        custom1 = view.findViewById(R.id.custom1)
        val layoutManager1 = LinearLayoutManager(context)
        layoutManager1.orientation = LinearLayoutManager.HORIZONTAL
        custom1.recycler.layoutManager = layoutManager1
        val customAdapter1 = CustomTodaySaleAdapter(requireContext())
        val data1 = ArrayList<ItemTodaySale>()
        data1.add(ItemTodaySale(R.drawable.logo_dominos, "도미노피자 전 메뉴", "7천원 할인", "기간:9/13~9/23", "", R.drawable.today_sale_food_domino, R.color.skyblue))
        data1.add(ItemTodaySale(R.drawable.logo_dominos, "도미노피자 전 메뉴", "7천원 할인", "기간:9/13~9/23", "", R.drawable.today_sale_food_domino, R.color.basy))
        data1.add(ItemTodaySale(R.drawable.logo_dominos, "도미노피자 전 메뉴", "7천원 할인", "기간:9/13~9/23", "", R.drawable.today_sale_food_domino, R.color.mint))
        data1.add(ItemTodaySale(R.drawable.logo_dominos, "도미노피자 전 메뉴", "7천원 할인", "기간:9/13~9/23", "", R.drawable.today_sale_food_domino, R.color.basy))
        customAdapter1.todaySaleList = data1
        custom1.recycler.adapter = customAdapter1


        custom2 = view.findViewById(R.id.custom2)
        val layoutManager2 = LinearLayoutManager(context)
        layoutManager2.orientation = LinearLayoutManager.VERTICAL
        custom2.recycler.layoutManager = layoutManager2
        val customAdapter2 = CustomTodaySaleAdapter(requireContext())
        val data2 = ArrayList<ItemTodaySale>()
        data2.add(ItemTodaySale(R.drawable.logo_dominos, "도미노피자 전 메뉴", "7천원 할인", "기간:9/13~9/23", "", R.drawable.today_sale_food_domino, R.color.basy))
        data2.add(ItemTodaySale(R.drawable.logo_dominos, "도미노피자 전 메뉴", "7천원 할인", "기간:9/13~9/23", "", R.drawable.today_sale_food_domino, R.color.mint))
        data2.add(ItemTodaySale(R.drawable.logo_dominos, "도미노피자 전 메뉴", "7천원 할인", "기간:9/13~9/23", "", R.drawable.today_sale_food_domino, R.color.skyblue))
        data2.add(ItemTodaySale(R.drawable.logo_dominos, "도미노피자 전 메뉴", "7천원 할인", "기간:9/13~9/23", "", R.drawable.today_sale_food_domino, R.color.basy))
        customAdapter2.todaySaleList = data2
        custom2.recycler.adapter = customAdapter2


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