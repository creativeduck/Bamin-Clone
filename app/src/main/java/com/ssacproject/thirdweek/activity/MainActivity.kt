package com.ssacproject.thirdweek.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Base64
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import androidx.viewpager2.widget.ViewPager2
import com.ssacproject.thirdweek.*
import com.ssacproject.thirdweek.customadapter.*
import com.ssacproject.thirdweek.customview.CustomMainMenu
import com.ssacproject.thirdweek.customview.CustomMainMenuAd
import com.ssacproject.thirdweek.customview.CustomMainRecyclerView
import com.ssacproject.thirdweek.databinding.ActivityMainBinding
import com.ssacproject.thirdweek.room.RoomHeartHelper
import com.ssacproject.thirdweek.room.RoomHelper
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var custom1: CustomMainRecyclerView
    lateinit var custom2: CustomMainRecyclerView
    lateinit var custom3: CustomMainRecyclerView
    lateinit var handler: Handler
    lateinit var handRun: Runnable
    lateinit var handler2: Handler
    lateinit var handRun2: Runnable
    lateinit var customMainMenuAd1: CustomMainMenuAd
    lateinit var customMainMenuAd2: CustomMainMenuAd
    lateinit var terms: LinearLayout

    companion object {
        var helper: RoomHelper? = null
        var heartHelper: RoomHeartHelper? = null
    }

    val frontAdList = listOf(
        R.drawable.ad1, R.drawable.ad2, R.drawable.ad3, R.drawable.ad4, R.drawable.ad5, R.drawable.ad6)
    val bottomAdList = listOf(R.drawable.bottom_ad1, R.drawable.bottom_ad2)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        val actionBar = supportActionBar
        actionBar!!.setDisplayShowTitleEnabled(false)

        helper = Room.databaseBuilder(this, RoomHelper::class.java, "room_basket6")
            .allowMainThreadQueries()
            .build()
        // 먼저 앱 시작할 때 장바구니 데이터 다 지우기
        helper?.roomBasketDao()?.deleteAll()
        setReset()

        heartHelper = Room.databaseBuilder(this, RoomHeartHelper::class.java, "room_heart4")
            .allowMainThreadQueries()
            .build()

        val customMainMenuList = listOf(binding.customMainMenu1, binding.customMainMenu2,
            binding.customMainMenu3, binding.customMainMenu4, binding.customMainMenu5,
            binding.customMainMenu6, binding.customMainMenu7)
        for (i in 0 until customMainMenuList.size) {
            customMainMenuList[i].setOnClickListener {
                val intent = Intent(this, RealMainActivity::class.java)
                intent.putExtra("position", i)
                startActivity(intent)
            }
        }

        // 커스텀 뷰페이저 설정
        customMainMenuAd1 = findViewById(R.id.customMainMenuAd1)
        val customAdAdapter1 = CustomMainMenuAdAdapter()
        customAdAdapter1.apply {
            imageList = frontAdList
            setShowAll = true
            listener = object: OnItemClickListener {
                override fun onItemClicked(view: View, pos: Int) {
                    val intent = Intent(applicationContext, RealMainActivity::class.java)
                    startActivity(intent)
                }
            }
        }
        val len1 = customAdAdapter1.imageList.size
        customMainMenuAd1.tot.text = "${len1}"
        if (len1 > 0) {
            customMainMenuAd1.cur.text = "${1}"
        } else {
            customMainMenuAd1.cur.text = "${0}"
        }
        customMainMenuAd1.viewpager_ad.adapter = customAdAdapter1
        // 핸들러 설정해서 자동으로 스와이프
        handler = Handler()
        handRun = Runnable {
            customMainMenuAd1.viewpager_ad.currentItem = customMainMenuAd1.viewpager_ad.currentItem + 1
        }
        // 뷰페이저 스와이프할 때마다 숫자 바꿔주기
        customMainMenuAd1.viewpager_ad.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(handRun)
                handler.postDelayed(handRun, 3000)
                customMainMenuAd1.tot.text = "${customAdAdapter1.imageList.size}"
                val now1 = position%customAdAdapter1.imageList.size + 1
                customMainMenuAd1.cur.text ="${now1}"
            }
        })

        custom1 = findViewById(R.id.custom1)
        val data1 = ArrayList<FastestItem>()
        data1.add(FastestItem(R.drawable.food_item_pasta, "언니네분식", 4.9, 1900, 12, 22))
        data1.add(FastestItem(R.drawable.food_item_one, "원할머니보쌈족발", 4.7, 2000, 15, 25))
        data1.add(FastestItem(R.drawable.food_item_juicy, "심플디", 4.9, 1900, 15, 25))
        data1.add(FastestItem(R.drawable.food_item_salad, "샐러디 응암점", 4.8, 1000, 13, 23))
        val customAdapter1 = CustomFastestAdapter()
        customAdapter1.apply {
            listener = object : OnItemClickListener {
                override fun onItemClicked(view: View, pos: Int) {
                    Toast.makeText(applicationContext, "not yet", Toast.LENGTH_SHORT).show()
                }
            }
            fastestList = data1
        }
        custom1.recycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = customAdapter1
        }

        custom2 = findViewById(R.id.custom2)
        val data2 = ArrayList<FastestItem>()
        data2.add(FastestItem(R.drawable.food_item_galbi, "곱분이곱창 홍제점", 4.9, 2000, 24, 34))
        val customAdapter2 = CustomFastestAdapter()
        customAdapter2.apply {
            listener = object : OnItemClickListener {
                override fun onItemClicked(view: View, pos: Int) {
                    Toast.makeText(applicationContext, "not yet", Toast.LENGTH_SHORT).show()
                }
            }
            fastestList = data2
        }
        custom2.recycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = customAdapter2
        }

        custom3 = findViewById(R.id.custom3)
        val data3 = ArrayList<ItemTodaySale>()
        setData3(data3)
        setCustomMainRecyclerView(custom3, this, LinearLayoutManager.HORIZONTAL, data3)


        // 커스텀 뷰페이저 설정
        customMainMenuAd2 = findViewById(R.id.customMainMenuAd2)
        customMainMenuAd2.show_all.visibility = View.GONE
        val customAdAdapter2 = CustomMainMenuAdAdapter()
        customAdAdapter2.apply {
            imageList = bottomAdList
            listener = object: OnItemClickListener {
                override fun onItemClicked(view: View, pos: Int) {
                    val intent = Intent(applicationContext, RealMainActivity::class.java)
                    startActivity(intent)
                }
            }
        }
        val len2 = customAdAdapter2.imageList.size
        customMainMenuAd2.tot.text = "${len2}"
        if (len2 > 0) {
            customMainMenuAd2.cur.text = "${1}"
        } else {
            customMainMenuAd2.cur.text = "${0}"
        }
        customMainMenuAd2.viewpager_ad.adapter = customAdAdapter2
        // 핸들러 설정해서 자동으로 스와이프
        handler2 = Handler()
        handRun2 = Runnable {
            customMainMenuAd2.viewpager_ad.currentItem = customMainMenuAd2.viewpager_ad.currentItem + 1
        }
        // 뷰페이저 스와이프할 때마다 숫자 바꿔주기
        customMainMenuAd2.viewpager_ad.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler2.removeCallbacks(handRun2)
                handler2.postDelayed(handRun2, 3000)
                customMainMenuAd2.tot.text = "${len2}"
                val now2 = position%len2 + 1
                customMainMenuAd2.cur.text ="${now2}"
            }
        })

        terms = findViewById(R.id.terms)
        val showMoreTerm: ImageButton = terms.findViewById(R.id.showMoreTerm)
        val termsOfSurplusInfo: LinearLayout = terms.findViewById(R.id.termsOfSurplusInfo)
        showMoreTerm.setOnClickListener {
            if (termsOfSurplusInfo.visibility == View.VISIBLE) {
                termsOfSurplusInfo.visibility = View.GONE
                showMoreTerm.setImageResource(R.drawable.ic_baseline_expand_more_24)
            } else {
                termsOfSurplusInfo.visibility = View.VISIBLE
                showMoreTerm.setImageResource(R.drawable.ic_baseline_expand_less_24)
            }
        }

        binding.showHeart.setOnClickListener {
            val intent = Intent(this, HeartActivity::class.java)
            startActivity(intent)
        }

    }


    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(handRun)
        handler2.removeCallbacks(handRun2)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(handRun, 3000)
        handler2.postDelayed(handRun2, 3000)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.app_bar_menu, menu)
        return true
    }
    fun setData3(data: ArrayList<ItemTodaySale>) {
        data.add(ItemTodaySale(R.drawable.logo_dominos, "도미노피자 전 메뉴", "7천원 할인",
            "기간:9/13~9/23", "", R.drawable.today_sale_food_domino, R.color.mint))
        data.add(ItemTodaySale(R.drawable.logo_sunsoo, "순수치킨 전 메뉴", "5천원 할인",
            "9월 매주 목,토", "배달 주문시에만 가능합니다.",
            R.drawable.today_sale_food_sunsoo, R.color.ashgray))
        data.add(ItemTodaySale(R.drawable.logo_ttu, "신메뉴 2종 출시!", "4천원 할인",
            "세계 3대 진미 트러플크림찜닭!", "극강의 매운 맛 불마왕불닭!", R.drawable.today_sale_food_ttu, R.color.basy))
        data.add(ItemTodaySale(R.drawable.logo_ashley, "배달·포장 주문시", "5천원 할인",
            "스테이크부터 파스타,샐러드까지!", "할인혜택으로 애슐리를 즐겨보세요!", R.drawable.today_sale_food_ashley, R.color.mint))
        data.add(
            ItemTodaySale(R.drawable.logo_boor, "색다른, 부어치킨", "7천원 할인",
                "9월 18일~9월 22일(추석 연휴)", "7천원 할인", R.drawable.today_sale_food_boor,
                R.color.littlepurple)
        )
        data.add(
            ItemTodaySale(R.drawable.logo_gob, "곱창떡볶이 평판1등", "4천원 할인",
                "꾸덕꾸덕 로제와 쫄깃한 곱창-!", "소곱창 로제떡볶이-!", R.drawable.today_sale_food_gob,
                R.color.basy)
        )
        data.add(
            ItemTodaySale(R.drawable.logo_catnip, "깻잎 추석세트!", "5천원 할인",
                "9월 13일 ~ 22일 (추석세트 5종 할인)", "추석은 깻잎치킨이 챙겨 드릴게요",
                R.drawable.today_sale_food_catnip, R.color.mint)
        )
        data.add(ItemTodaySale(R.drawable.logo_sam, "삼첩분식 전 메뉴", "4천원 할인",
            "9월 6일 ~ 9월 19일 (14일간)", "맵싸한 마라로 제 떡볶이부터 튀김, 막창까지!",
            R.drawable.today_sale_food_sam, R.color.basy))
        data.add(
            ItemTodaySale(R.drawable.logo_pas, "파스쿠찌", "5천원 할인",
                "배달 시 5,000원 할인", "포장 주문 시 3,000원 할인",
                R.drawable.today_sale_food_pas, R.color.ashgray)
        )
    }


    fun setReset() {
        setSharedData("prefShopTitle", "shopTitle", null)
        setSharedData("prefCanDelivery", "canDelivery", false)
        setSharedData("prefTotalPrice", "totalPrice", 0)
        setSharedData("prefTotalQuantity", "totalQuantity", 0)
    }

    fun setCustomMainRecyclerView(custom: CustomMainRecyclerView, context: Context,
                                  orientation: Int, data: ArrayList<ItemTodaySale>) {
        val customAdapter = CustomTodaySaleAdapter(context)
        customAdapter.apply {
            listener = object: OnItemClickListener {
                override fun onItemClicked(view: View, pos: Int) {
                    Toast.makeText(applicationContext, "not yet", Toast.LENGTH_SHORT).show()
                }
            }
            todaySaleList = data
        }
        custom.recycler.apply {
            layoutManager = LinearLayoutManager(context, orientation, false)
            adapter = customAdapter
        }
    }

    private fun getHashKey() {
        var packageInfo: PackageInfo? = null
        try {
            packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        if (packageInfo == null) {
            Log.e("KeyHash", "KeyHash:null")
        }
        for (signature in packageInfo!!.signatures) {
            try {
                var md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            } catch (e: NoSuchAlgorithmException) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature="+signature, e)
            }
        }
    }
    fun setSharedData(name: String, key: String, data: Boolean) {
        val pref: SharedPreferences = getSharedPreferences(name, Activity.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = pref.edit()
        editor.putBoolean(key, data)
        editor.apply()
    }
    fun setSharedData(name: String, key: String, data: String?) {
        val pref: SharedPreferences = getSharedPreferences(name, Activity.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = pref.edit()
        editor.putString(key, data)
        editor.apply()
    }
    fun setSharedData(name: String, key: String, data: Int) {
        val pref: SharedPreferences = getSharedPreferences(name, Activity.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = pref.edit()
        editor.putInt(key, data)
        editor.apply()
    }

    fun getSharedBooleanData(name: String, key: String): Boolean {
        val pref: SharedPreferences = getSharedPreferences(name, Activity.MODE_PRIVATE)
        return pref.getBoolean(key, false)
    }
    fun getSharedStringData(name: String, key: String): String? {
        val pref: SharedPreferences = getSharedPreferences(name, Activity.MODE_PRIVATE)
        return pref.getString(key, null)
    }

}