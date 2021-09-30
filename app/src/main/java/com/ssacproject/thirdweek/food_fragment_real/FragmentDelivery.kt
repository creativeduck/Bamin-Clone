package com.ssacproject.thirdweek.food_fragment_real

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.ssacproject.thirdweek.*
import com.ssacproject.thirdweek.activity.SelectMenuActivity
import com.ssacproject.thirdweek.activity.SpecificActivity
import com.ssacproject.thirdweek.customadapter.*
import com.ssacproject.thirdweek.customview.CustomMainMenuAd
import com.ssacproject.thirdweek.customview.CustomMainRecyclerView
import com.ssacproject.thirdweek.customview.GridItem
import com.ssacproject.thirdweek.databinding.FragmentDeliveryBinding
import kotlinx.coroutines.selects.select

class FragmentDelivery : Fragment() {
    lateinit var customMainMenuAdDelivery: CustomMainMenuAd
    lateinit var custom1: CustomMainRecyclerView
    lateinit var custom2: CustomMainRecyclerView
    lateinit var handler: Handler
    lateinit var handRun: Runnable

    val imageList = listOf(
        R.drawable.ad1, R.drawable.ad2, R.drawable.ad3, R.drawable.ad4, R.drawable.ad5, R.drawable.ad6)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_delivery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        customMainMenuAdDelivery = view.findViewById(R.id.customMainMenuAdDelivery)
        val customAdAdapter = CustomMainMenuAdAdapter()
        customAdAdapter.let {
            it.imageList = imageList
            it.setShowAll = true
        }
        val len = customAdAdapter.imageList.size
        customMainMenuAdDelivery.tot.text = "${len}"
        if (len > 0) {
            customMainMenuAdDelivery.cur.text = "${1}"
        } else {
            customMainMenuAdDelivery.cur.text = "${0}"
        }
        customMainMenuAdDelivery.viewpager_ad.adapter = customAdAdapter
        // 핸들러 설정해서 자동으로 스와이프
        handler = Handler()
        handRun = Runnable {
            customMainMenuAdDelivery.viewpager_ad.currentItem = customMainMenuAdDelivery.viewpager_ad.currentItem + 1
        }
        // 뷰페이저 스와이프할 때마다 숫자 바꿔주기
        customMainMenuAdDelivery.viewpager_ad.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(handRun)
                handler.postDelayed(handRun, 3000)
                customMainMenuAdDelivery.tot.text = "${len}"
                val now = position%len + 1
                customMainMenuAdDelivery.cur.text ="${now}"
            }
        })

        custom1 = view.findViewById(R.id.custom1)
        val customAdapter1 = CustomTodaySaleAdapter(requireContext())
        val data1 = ArrayList<ItemTodaySale>()
        data1.add(ItemTodaySale(R.drawable.logo_dominos, "도미노피자 전 메뉴", "7천원 할인",
            "기간:9/13~9/23", "", R.drawable.today_sale_food_domino, R.color.mint))
        data1.add(ItemTodaySale(R.drawable.logo_sunsoo, "순수치킨 전 메뉴", "5천원 할인",
            "9월 매주 목,토", "배달 주문시에만 가능합니다.",
                    R.drawable.today_sale_food_sunsoo, R.color.ashgray))
        data1.add(ItemTodaySale(R.drawable.logo_ttu, "신메뉴 2종 출시!", "4천원 할인",
            "세계 3대 진미 트러플크림찜닭!", "극강의 매운 맛 불마왕불닭!", R.drawable.today_sale_food_ttu, R.color.basy))
        data1.add(ItemTodaySale(R.drawable.logo_ashley, "배달·포장 주문시", "5천원 할인",
            "스테이크부터 파스타,샐러드까지!", "할인혜택으로 애슐리를 즐겨보세요!", R.drawable.today_sale_food_ashley, R.color.mint))
        data1.add(
            ItemTodaySale(R.drawable.logo_boor, "색다른, 부어치킨", "7천원 할인",
        "9월 18일~9월 22일(추석 연휴)", "7천원 할인", R.drawable.today_sale_food_boor,
                R.color.littlepurple)
        )
        data1.add(
            ItemTodaySale(R.drawable.logo_gob, "곱창떡볶이 평판1등", "4천원 할인",
        "꾸덕꾸덕 로제와 쫄깃한 곱창-!", "소곱창 로제떡볶이-!", R.drawable.today_sale_food_gob,
                R.color.basy)
        )
        data1.add(
            ItemTodaySale(R.drawable.logo_catnip, "깻잎 추석세트!", "5천원 할인",
        "9월 13일 ~ 22일 (추석세트 5종 할인)", "추석은 깻잎치킨이 챙겨 드릴게요",
                    R.drawable.today_sale_food_catnip, R.color.mint)
        )
        data1.add(ItemTodaySale(R.drawable.logo_sam, "삼첩분식 전 메뉴", "4천원 할인",
        "9월 6일 ~ 9월 19일 (14일간)", "맵싸한 마라로 제 떡볶이부터 튀김, 막창까지!",
        R.drawable.today_sale_food_sam, R.color.basy))
        data1.add(
            ItemTodaySale(R.drawable.logo_pas, "파스쿠찌", "5천원 할인",
        "배달 시 5,000원 할인", "포장 주문 시 3,000원 할인",
                R.drawable.today_sale_food_pas, R.color.ashgray)
        )
        customAdapter1.apply {
            todaySaleList = data1
        }
        custom1.recycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = customAdapter1
        }

        val foodAdapter2 = FoodItemAdapter()
        val data2 = ArrayList<SelectFoodItem>()
        data2.add(SelectFoodItem("원할머니보쌈족발 녹번점", 4.9, 1226, 
            "족발·보쌈, 모둠보쌈, 보족원쌈", 16, 31, R.drawable.food_item_one,
            18000, 3500, true, R.drawable.food_item_one, 5000, 
            "서울특별시 은평구 응암동 96-1 (응암동, 상현빌딩 204호)", 0.8, 1940, 
            888, "바로결제, 만나서결제"))
        data2.add(
            SelectFoodItem("원할머니명품도시락 녹번점", 4.6, 98, 
            "도시락, 보쌈도시락, 제육도시락", 17, 32, R.drawable.food_item_bossam,
            10000, 4000, false, R.drawable.food_item_bossam, 1000, 
            "서울특별시 은평구 응암동 96-1 204", 0.8, 328, 69, 
            "바로결제, 만나서결제")
        )
        data2.add(
            SelectFoodItem("샐러디 응암점", 4.8, 139, "패스트푸드, 탄단지 샐러드, 칠리베이컨 웜볼", 
                30, 90, R.drawable.food_item_salad, 10000, 3000, 
                false, R.drawable.food_item_salad, 1000, 
                "서울특별시 은평구 응암동 110-9 1층 105호(응암동, 메트로럭스주상복합APT)", 1.2, 
                429, 133, null)
        )
        data2.add(
            SelectFoodItem("블리담담 샐러드&요거트", 5.0, 535,
                    "카페·디저트, 닭가슴살 샐러드, 쉬림프 샐러드", 30, 40, 
                R.drawable.food_item_vegetable, 12000, 3000, true, 
                R.drawable.food_item_vegetable, 1000, "서울특별시 은평구 응암동 1-12 상가동 B3층 328호(응암동)",
                0.5, 897, 484, null)
        )
        data2.add(
            SelectFoodItem("돈갓스대장 은평점", 4.6, 11,
                "돈까스·회·일식, 통등심돈까스, 돈까스 쌈밥", 27, 42, 
                R.drawable.food_item_katsu, 5000, 3500, true,
                R.drawable.food_item_katsu, 100, "서울특별시 은평구 녹번동 141-87 1층(녹번동)",
                1.5, 18, 8, "바로결제, 만나서결제")
        )
        data2.add(SelectFoodItem("쥬씨 홍제점", 4.9, 215, "수박, 수박팩",
            45, 55, R.drawable.food_item_juicy, 8000, 3000, true,
            R.drawable.food_item_juicy, 900, "서울특별시 서대문구 홍제동 306-9(홍제동,지상1층)",
            1.3, 168, 177, null))
        data2.add(
            SelectFoodItem("맵당 매운갈비찜 신촌본점", 4.9, 2002,
                "매운갈비찜, 로제 눈꽃치즈 갈비찜", 53, 68, R.drawable.food_item_galbi,
            14000, 2900, true, R.drawable.food_item_galbi, 7000,
            "서울특별시 마포구 서교동 334-19 지1층 B04호", 4.5, 8665, 1016,
            "바로결제, 만나서결제")
        )
        data2.add(
            SelectFoodItem("북촌손만두 응암점", 4.9, 345, "북촌피냉면, 튀김만두",
                27, 37, R.drawable.food_item_dumpling, 10000, 6750, false,
                R.drawable.food_item_dumpling, 5000, "서울특별시 서대문구 북아현동 1015 136-21 4단지 1층 안쪽 115호",
            4.6, 2078, 346, null)
        )
        data2.add(
            SelectFoodItem("하이드미플리즈", 4.9, 172, "반죽쿠키, 초콜릿칩 쿠키",
                30, 40, R.drawable.food_item_cookie, 13000, 3900, true,
                R.drawable.food_item_cookie, 600, "서울특별시 서대문구 홍제동 330-226(홍제동)",
                1.2, 510, 154, null)
        )
        data2.add(
            SelectFoodItem("중화각", 4.1, 20, "짜장면, 짬뽕, 탕수육",
                30, 40, R.drawable.food_item_jja, 10000, 0, true,
                R.drawable.food_item_jja, 100, "서울특별시 은평구 응암동 103-7 (응암동)",
                0.9, 28, 1, null)
        )
        foodAdapter2.apply {
            foodList = data2
            listener = object: OnItemClickListener {
                override fun onItemClicked(view: View, pos: Int) {
                    val intent = Intent(context, SpecificActivity::class.java)
                    val item = foodList[pos]
                    intent.putExtra("parcelItem", item)
                    startActivity(intent)
                }

            }
        }
        custom2 = view.findViewById(R.id.custom2)
        custom2.recycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = foodAdapter2
        }

        var food_grid_layout = view.findViewById<GridLayout>(R.id.food_grid_layout)
        for (i in 1 until food_grid_layout.childCount-6) {
            var grid = food_grid_layout.getChildAt(i)
            grid.setOnClickListener {
                val intent = Intent(context, SelectMenuActivity::class.java)
                intent.putExtra("position", i-1)
                intent.putExtra("what", 0)
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