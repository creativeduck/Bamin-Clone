package com.ssacproject.thirdweek.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssacproject.thirdweek.OnItemClickListener
import com.ssacproject.thirdweek.R
import com.ssacproject.thirdweek.customadapter.*
import com.ssacproject.thirdweek.databinding.ActivityPayBinding

class PayActivity : AppCompatActivity() {
    lateinit var specific_menu_recycler: RecyclerView
    lateinit var specific_menu_tab_recycler: RecyclerView
    var linearLayoutManager: LinearLayoutManager? = null
    private var selectedPosition: Int = 0
    lateinit var binding: ActivityPayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        specific_menu_recycler = binding.specificMenuRecycler
        val specificMenuLinearAdapter = CustomMenuLinearAdapter(this)

        specific_menu_tab_recycler = binding.specificMenuTabRecycler
        val specificMenuTabRecyclerAdapter = TabRecyclerAdapter()

        val dataNagmyeon = ArrayList<MenuLinear>()
        val specificDataNagmyeonMenu = ArrayList<SpecificMenu>()
        val textList = ArrayList<TabRecycler>()

        specificDataNagmyeonMenu.add(
            SpecificMenu("1인세트메뉴", "물냉면or비빔냉면or불비빔냉면or열무냉면+만두+석쇠불고기",
                16000, R.drawable.nagmyeon_one)
        )
        specificDataNagmyeonMenu.add(
            SpecificMenu("2인세트메뉴", "(물or비빔or불비빔or열무냉면 중 택2) +만두+석쇠불고기",
                26000, null)
        )
        specificDataNagmyeonMenu.add(SpecificMenu("물냉면", "냉면 맛집 1위\n냉면의 기본 물냉면", 7500, R.drawable.nagmyeon_mul))
        specificDataNagmyeonMenu.add(SpecificMenu("비빔냉면", "냉면 맛집 1위\n기본의 맛에 특제소스로 매콤한 비빔냉면", 7500, R.drawable.nagmyeon_bibim))
        specificDataNagmyeonMenu.add(SpecificMenu("열무냉면", "냉면 맛집 1위\n기본의 맛에 아삭한 열무냉면 (오이X)", 8500, R.drawable.nagmyeon_yeolmu))
        val adapterNagmyeonMenu = ItemSpecificMenuAdapter()
        dataNagmyeon.add(MenuLinear("메뉴(주반장이 추천함)", specificDataNagmyeonMenu, adapterNagmyeonMenu, 0))
        adapterNagmyeonMenu.setItemClickListener(object: OnItemClickListener {
            override fun onItemClicked(view: View, pos: Int) {
                val intent = Intent(applicationContext, OrderActivity::class.java)
                // 인텐트에 필요한 정보 넣기
                val item = adapterNagmyeonMenu.specificMenuList[pos]
                intent.putExtra("parcel", item)
                intent.putExtra("candelivery", false)
                intent.putExtra("shopTitle", title)
                startActivity(intent)
            }
        })
        val specificDataNagmyeonBeverage = ArrayList<SpecificMenu>()
        specificDataNagmyeonBeverage.add(
            SpecificMenu("수제살얼음식혜", null,
                16000, R.drawable.food_item_one)
        )
        specificDataNagmyeonBeverage.add(
            SpecificMenu("콜라", null,
                26000, R.drawable.food_item_one)
        )
        specificDataNagmyeonBeverage.add(SpecificMenu("사이다", null, 7500, R.drawable.food_item_one))
        val adapterNagmyeonBeverage = ItemSpecificMenuAdapter()
        dataNagmyeon.add(MenuLinear("음료(시원함)", specificDataNagmyeonBeverage, adapterNagmyeonBeverage, 1))
        adapterNagmyeonBeverage.setItemClickListener(object: OnItemClickListener {
            override fun onItemClicked(view: View, pos: Int) {
                val intent = Intent(applicationContext, OrderActivity::class.java)
                // 인텐트에 필요한 정보 넣기
                val item = adapterNagmyeonBeverage.specificMenuList[pos]
                intent.putExtra("parcel", item)
                intent.putExtra("candelivery", false)
                intent.putExtra("shopTitle", title)
                startActivity(intent)
            }
        })
        dataNagmyeon.add(MenuLinear("주류메뉴", specificDataNagmyeonMenu, adapterNagmyeonMenu, 2))
        specificMenuLinearAdapter.menuLinearList = dataNagmyeon

        textList.add(TabRecycler("메뉴(주반장이 추천함)", true))
        textList.add(TabRecycler("음료(시원함)", false))
        textList.add(TabRecycler("주류메뉴", false))
        textList.add(TabRecycler("메뉴", false))
        textList.add(TabRecycler("음료", false))
        textList.add(TabRecycler("주류메뉴", false))

        textList.add(TabRecycler("메뉴", false))
        textList.add(TabRecycler("음료", false))
        textList.add(TabRecycler("주류메뉴", false))
        specificMenuTabRecyclerAdapter.textList = textList

        linearLayoutManager = LinearLayoutManager(this)
        specific_menu_recycler.apply {
            layoutManager = linearLayoutManager
            adapter = specificMenuLinearAdapter
            addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    linearLayoutManager?.let { lm ->
                        val index = lm.findFirstVisibleItemPosition()
                        if(dataNagmyeon.size-1 >= index) {
                            dataNagmyeon[index].let { sp ->
                                if (selectedPosition != sp.index) {
                                    selectedPosition = sp.index
                                    specificMenuTabRecyclerAdapter.updateList(selectedPosition)
                                    specific_menu_tab_recycler.layoutManager?.scrollToPosition(selectedPosition)
                                }
                            }
                        }
                    }
                }
            })
        }
        specific_menu_tab_recycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = specificMenuTabRecyclerAdapter
        }



    }
}