package com.ssacproject.thirdweek.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.ssacproject.thirdweek.FragmentMenu
import com.ssacproject.thirdweek.R
import com.ssacproject.thirdweek.customadapter.SelectFoodItem
import com.ssacproject.thirdweek.customadapter.ViewPagerFragmentAdapter
import com.ssacproject.thirdweek.databinding.ActivitySelectMenuBinding
import com.ssacproject.thirdweek.food_fragment_real.FragmentBaminOne
import java.util.*

class SelectMenuActivity : AppCompatActivity() {
    lateinit var binding: ActivitySelectMenuBinding
    lateinit var tabTitle: List<String>
    lateinit var fragmentList: List<Fragment>

    var selectListKorean = ArrayList<SelectFoodItem>()
    var selectListSnack = ArrayList<SelectFoodItem>()
    var selectListDessert = ArrayList<SelectFoodItem>()
    var selectListJapanese = ArrayList<SelectFoodItem>()
    var selectListChicken = ArrayList<SelectFoodItem>()
    var selectListPizza = ArrayList<SelectFoodItem>()
    var selectListAsian = ArrayList<SelectFoodItem>()
    var selectListChinese = ArrayList<SelectFoodItem>()
    var selectListJokbo = ArrayList<SelectFoodItem>()
    var selectListNight = ArrayList<SelectFoodItem>()
    var selectListSoup = ArrayList<SelectFoodItem>()
    var selectListDosirak = ArrayList<SelectFoodItem>()
    var selectListFastFood = ArrayList<SelectFoodItem>()
    var selectListBak = ArrayList<SelectFoodItem>()
    var selectListMeat = ArrayList<SelectFoodItem>()
    var selectListBurger = ArrayList<SelectFoodItem>()
    var selectListWestern = ArrayList<SelectFoodItem>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivitySelectMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setActionBar(binding.toolbar)
        setList(selectListKorean)
        setList2(selectListSnack)

        // 각 프래그먼트 별로 다른 데이터를 주어서 해당 카테고리에 맞는 아이템들 나열하기
        val tabTitle1 = listOf("한식", "분식", "카페·디저트", "돈까스·회·일식", "치킨", "피자", "아시안·양식",
            "중국집", "족발·보쌈", "야식", "찜·탕", "도시락", "패스트푸드")
        // 각 프래그먼트 별로 필요한 데이터를 다르게 하기.
        val fragmentList1 = listOf(getFragMenu(selectListKorean), getFragMenu(selectListKorean),
            getFragMenu(selectListKorean), getFragMenu(selectListKorean), getFragMenu(selectListKorean),
            getFragMenu(selectListKorean), getFragMenu(selectListKorean), getFragMenu(selectListKorean),
            getFragMenu(selectListKorean), getFragMenu(selectListKorean), getFragMenu(selectListKorean),
            getFragMenu(selectListKorean), getFragMenu(selectListKorean)
        )

        val tabTitle2 = listOf("돈까스·회·일식", "중식", "치킨", "백반·죽·국수", "카페·디저트", "분식",
            "찜·탕·찌게", "피자", "양식", "고기·구이", "족발·보쌈", "아시안", "버거")
        val fragmentList2 = listOf(getFragMenu(selectListKorean), getFragMenu(selectListKorean),
            getFragMenu(selectListKorean), getFragMenu(selectListKorean), getFragMenu(selectListKorean),
            getFragMenu(selectListKorean),getFragMenu(selectListKorean),getFragMenu(selectListKorean),
            getFragMenu(selectListKorean),getFragMenu(selectListKorean),getFragMenu(selectListKorean),
            getFragMenu(selectListKorean),getFragMenu(selectListKorean)
        )
        // 보내진 곳에 따라서 리스트 다르게 설정하기
        val what = intent.getIntExtra("what", 0)
        when(what) {
            0 -> {tabTitle = tabTitle1; fragmentList = fragmentList1}
            else -> {tabTitle = tabTitle2; fragmentList = fragmentList2}
        }
        val adapter = ViewPagerFragmentAdapter(this)
        adapter.fragmentList = fragmentList
        binding.viewpager.let {
            it.adapter = adapter
            it.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }
        TabLayoutMediator(binding.tabLayout, binding.viewpager) {tab, position ->
            tab.text = tabTitle[position]
        }.attach()
        binding.tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val pos = tab?.position
                binding.toolbarTitle.text = tabTitle[pos!!]
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
        })
        val pos = intent.getIntExtra("position", 0)
        binding.viewpager.setCurrentItem(pos, false)
        binding.toolbarTitle.text = tabTitle[pos]

        binding.chipNormal.isChecked = true
        binding.chipReset.setOnClickListener {
            val chip = binding.chipNormal
            chip.isChecked = true
            binding.chipReset.visibility = View.GONE
        }

        binding.chipGroup.setOnCheckedChangeListener(object: ChipGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: ChipGroup?, checkedId: Int) {
                if (checkedId != R.id.chipReset) {
                    group?.findViewById<Chip>(R.id.chipReset)?.visibility = View.VISIBLE
                }
                when (checkedId) {
                    R.id.chipNormal -> {
                        for(i in 0 until fragmentList.size) {
                            val tmp = fragmentList[i] as FragmentMenu
                            tmp.changeDataSort("chipNormal")
                        }
                    }
                    R.id.chipTipLowest -> {
                        for (i in 0 until fragmentList.size) {
                            val tmp = fragmentList[i] as FragmentMenu
                            tmp.changeDataSort("chipTipLowest")
                        }
                    }
                    R.id.chipTopRatings -> {
                        for (i in 0 until fragmentList.size) {
                            val tmp = fragmentList[i] as FragmentMenu
                            tmp.changeDataSort("chipTopRatings")
                        }
                    }
                    R.id.chipFastest -> {
                        for (i in 0 until fragmentList.size) {
                            val tmp = fragmentList[i] as FragmentMenu
                            tmp.changeDataSort("chipFastest")
                        }
                    }
                    R.id.chipLeastPrice -> {
                        for (i in 0 until fragmentList.size) {
                            val tmp = fragmentList[i] as FragmentMenu
                            tmp.changeDataSort("chipLeastPrice")
                        }
                    }
                    R.id.chipHeart -> {
                        for (i in 0 until fragmentList.size) {
                            val tmp = fragmentList[i] as FragmentMenu
                            tmp.changeDataSort("chipHeart")
                        }
                    }
                    R.id.chipNear -> {
                        for (i in 0 until fragmentList.size) {
                            val tmp = fragmentList[i] as FragmentMenu
                            tmp.changeDataSort("chipNear")
                        }
                    }
                    R.id.chipMostOrdered -> {
                        for (i in 0 until fragmentList.size) {
                            val tmp = fragmentList[i] as FragmentMenu
                            tmp.changeDataSort("chipMostOrdered")
                        }
                    }
                }
            }
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            else -> return true
        }
        return super.onOptionsItemSelected(item)
    }
    fun getFragMenu(list: ArrayList<SelectFoodItem>): FragmentMenu {
        val fragtmp = FragmentMenu()
        var bundle = Bundle()
        bundle.putParcelableArrayList("list", list)
        fragtmp.arguments = bundle
        return fragtmp
    }

    fun setList(list: ArrayList<SelectFoodItem>) {
        list.add(SelectFoodItem("육회란 연어다", 4.9, 2271, "육연덮밥, 육회초밥",
            37, 52, R.drawable.select_korean_yuk,5000, 3000,
            true, null, 11000, "서울특별시 은평구 응암동 349-28 1층 좌측호(응암동)",
        1.3, 3272, 42, "바로결제, 만나서결제"))
        list.add(SelectFoodItem("프리미엄 쫄면삼겹 고돼지은평점", 4.9, 2148,
            "고기만(300g), [주문많은]삼겹/목살 1인 도시락", 30, 45,
            R.drawable.select_korean_gopig, 8000, 6000, true,
            R.drawable.specific_food_item_gopig, 11000, "서울특별시 은평구 역촌동 62-10 1층",
        1.9, 2461, 2165, "바로결제, 만나서결제"))
        list.add(SelectFoodItem("연희옥 연희본점", 4.8, 2000, "순대국, 내장탕",
            29, 44, R.drawable.select_korean_yun, 13000, 3500, true,
           null, 9000, "서울특별시 서대문구 연희동 188-55(연희동, 외1필지 1층)",
            3.3, 3246, 550,"바로결제, 만나서결제"))
        list.add(SelectFoodItem("얼음골칡냉면", 4.9, 9000, "물냉면(다대기포함), 비빔냉면",
            39, 54, R.drawable.select_korean_nagmyeon, 15000, 5000,
            false, R.drawable.specific_food_item_nagmyeon, 9000, "서울특별시 은평구 역촌동 25-11 지1층 좌측호(역촌동)",
            1.5, 3186, 2580, null))
        list.add(SelectFoodItem("한촌설렁탕 북가좌점", 4.9, 6000, "설렁탕, 얼큰설렁탕",
                29, 44, R.drawable.select_korean_hanchon, 5000, 2500,
            true, R.drawable.specific_food_item_hancheon, 5000, "서울특별시 서대문구 북가좌동 307-1(북가좌동,1층)",
                    2.7, 2596, 982, "바로결제, 만나서결제, 예약가능"))
        list.add(SelectFoodItem("곱분이곱창 가재울점", 2.9, 3000, "숯불직화 모듬 (곱창+막창+오돌뼈), 숯", 40, 57,
            R.drawable.select_korean_gob, 16000, 1000, true, R.drawable.specific_food_item_gob,
            8000, "서울특별시 서대문구 남가좌동 385 1층 106-2호(남가좌동, DMC파크뷰자이)",
            2.8, 2357, 1455, "바로결제, 만나서결제"))
        list.add(SelectFoodItem("호세야 오리 바베큐 은평점", 4.9, 4000,"국내산오리장작바베큐, 통삼겹장작바베큐", 35, 50,
                R.drawable.select_korean_ho, 17000, 1000, true, R.drawable.specific_food_item_ho,
                7000, "서울특별시 은평구 신사동 344-16", 2.1, 2845, 17,
                "바로결제, 만나서결제"))
        list.add(SelectFoodItem("독립문1897설렁탕", 4.9, 1000, "설렁탕, 불고기",
            50, 65, R.drawable.select_korean_sul, 6000, 6000, true,
            null, 9000, "서울특별시 서대문구 창천동 31-26 1층(창천동)",
            4.3, 2083, 821, "바로결제"))
    }

    fun setList2(list: ArrayList<SelectFoodItem>) {
        list.add(SelectFoodItem("육회란 연어가 아니다", 4.9, 2271, "육연덮밥, 육회초밥",
            37, 52, R.drawable.select_korean_yuk,5000, 3000,
            true, null, 11000, "서울특별시 은평구 응암동 349-28 1층 좌측호(응암동)",
            1.3, 3272, 42, "바로결제, 만나서결제"))
        list.add(SelectFoodItem("프리미엄 쫄면삼겹 고돼지은평점이 아니다", 4.9, 2148,
            "고기만(300g), [주문많은]삼겹/목살 1인 도시락", 30, 45,
            R.drawable.select_korean_gopig, 8000, 6000, true,
            R.drawable.specific_food_item_gopig, 11000, "서울특별시 은평구 역촌동 62-10 1층",
            1.9, 2461, 2165, "바로결제, 만나서결제"))
        list.add(SelectFoodItem("연희옥 연희본점", 4.8, 2000, "순대국, 내장탕",
            29, 44, R.drawable.select_korean_yun, 13000, 3500, true,
            null, 9000, "서울특별시 서대문구 연희동 188-55(연희동, 외1필지 1층)",
            3.3, 3246, 550,"바로결제, 만나서결제"))
        list.add(SelectFoodItem("얼음골칡냉면", 4.9, 9000, "물냉면(다대기포함), 비빔냉면",
            39, 54, R.drawable.select_korean_nagmyeon, 15000, 5000,
            false, R.drawable.specific_food_item_nagmyeon, 9000, "서울특별시 은평구 역촌동 25-11 지1층 좌측호(역촌동)",
            1.5, 3186, 2580, null))
        list.add(SelectFoodItem("한촌설렁탕 북가좌점", 4.9, 6000, "설렁탕, 얼큰설렁탕",
            29, 44, R.drawable.select_korean_hanchon, 5000, 2500,
            true, R.drawable.specific_food_item_hancheon, 5000, "서울특별시 서대문구 북가좌동 307-1(북가좌동,1층)",
            2.7, 2596, 982, "바로결제, 만나서결제, 예약가능"))
        list.add(SelectFoodItem("곱분이곱창 가재울점", 4.9, 3000, "숯불직화 모듬 (곱창+막창+오돌뼈), 숯", 40, 57,
            R.drawable.select_korean_gob, 16000, 1000, true, R.drawable.specific_food_item_gob,
            8000, "서울특별시 서대문구 남가좌동 385 1층 106-2호(남가좌동, DMC파크뷰자이)",
            2.8, 2357, 1455, "바로결제, 만나서결제"))
        list.add(SelectFoodItem("호세야 오리 바베큐 은평점", 4.9, 4000,"국내산오리장작바베큐, 통삼겹장작바베큐", 35, 50,
            R.drawable.select_korean_ho, 17000, 1000, true, R.drawable.specific_food_item_ho,
            7000, "서울특별시 은평구 신사동 344-16", 2.1, 2845, 17,
            "바로결제, 만나서결제"))
        list.add(SelectFoodItem("독립문1897설렁탕", 4.9, 1000, "설렁탕, 불고기",
            50, 65, R.drawable.select_korean_sul, 6000, 6000, true,
            null, 9000, "서울특별시 서대문구 창천동 31-26 1층(창천동)",
            4.3, 2083, 821, "바로결제"))
    }
    fun setActionBar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        // 커스텀뷰 허용
        actionBar!!.setDisplayShowCustomEnabled(true)
        // 뒤로가기 버튼 생성
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        // 기본 타이틀 제거
        actionBar!!.setDisplayShowTitleEnabled(false)
    }

}