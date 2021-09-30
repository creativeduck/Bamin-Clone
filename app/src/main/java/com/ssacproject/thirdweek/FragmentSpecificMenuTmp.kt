package com.ssacproject.thirdweek

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import android.widget.ListAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssacproject.thirdweek.activity.OrderActivity
import com.ssacproject.thirdweek.customadapter.*

class FragmentSpecificMenuTmp(val title: String) : Fragment() {
//    //    lateinit var specific_menu_recycler: RecyclerView
//    lateinit var specific_menu_tab_recycler: RecyclerView
//    var linearLayoutManager: LinearLayoutManager? = null
//    private var selectedSubCatPosition: Int = 0
//    lateinit var expandableListView: ExpandableListView
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.fragment_specific_menu, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//
////        specific_menu_recycler = view.findViewById(R.id.specific_menu_recycler)
////        val specificMenuLinearAdapter = CustomMenuLinearAdapter(requireContext())
//
//        specific_menu_tab_recycler = view.findViewById(R.id.specific_menu_tab_recycler)
//        val specificMenuTabRecyclerAdapter = TabRecyclerAdapter()
//
////        val dataNagmyeon = ArrayList<MenuLinear>()
////        val specificDataNagmyeonMenu = ArrayList<SpecificMenu>()
////        val textList = ArrayList<TabRecycler>()
//
////        when(title) {
////            "얼음골칡냉면" -> {
////                specificDataNagmyeonMenu.add(SpecificMenu("1인세트메뉴", "물냉면or비빔냉면or불비빔냉면or열무냉면+만두+석쇠불고기",
////                                16000, R.drawable.nagmyeon_one))
////                specificDataNagmyeonMenu.add(SpecificMenu("2인세트메뉴", "(물or비빔or불비빔or열무냉면 중 택2) +만두+석쇠불고기",
////                    26000, null))
////                specificDataNagmyeonMenu.add(SpecificMenu("물냉면", "냉면 맛집 1위\n냉면의 기본 물냉면", 7500, R.drawable.nagmyeon_mul))
////                specificDataNagmyeonMenu.add(SpecificMenu("비빔냉면", "냉면 맛집 1위\n기본의 맛에 특제소스로 매콤한 비빔냉면", 7500, R.drawable.nagmyeon_bibim))
////                specificDataNagmyeonMenu.add(SpecificMenu("열무냉면", "냉면 맛집 1위\n기본의 맛에 아삭한 열무냉면 (오이X)", 8500, R.drawable.nagmyeon_yeolmu))
////                val adapterNagmyeonMenu = ItemSpecificMenuAdapter()
////                dataNagmyeon.add(MenuLinear("메뉴(주반장이 추천함)", specificDataNagmyeonMenu, adapterNagmyeonMenu, 0))
////                adapterNagmyeonMenu.setItemClickListener(object: OnItemClickListener {
////                    override fun onItemClicked(view: View, pos: Int) {
////                        val intent = Intent(context, OrderActivity::class.java)
////                        // 인텐트에 필요한 정보 넣기
////                        val item = adapterNagmyeonMenu.specificMenuList[pos]
////                        intent.putExtra("parcel", item)
////                        intent.putExtra("candelivery", false)
////                        intent.putExtra("shopTitle", title)
////                        startActivity(intent)
////                    }
////                })
////                val specificDataNagmyeonBeverage = ArrayList<SpecificMenu>()
////                specificDataNagmyeonBeverage.add(SpecificMenu("수제살얼음식혜", null,
////                                                            16000, R.drawable.food_item_one))
////                specificDataNagmyeonBeverage.add(SpecificMenu("콜라", null,
////                    26000, R.drawable.food_item_one))
////                specificDataNagmyeonBeverage.add(SpecificMenu("사이다", null, 7500, R.drawable.food_item_one))
////                val adapterNagmyeonBeverage = ItemSpecificMenuAdapter()
////                dataNagmyeon.add(MenuLinear("음료(시원함)", specificDataNagmyeonBeverage, adapterNagmyeonBeverage, 1))
////                adapterNagmyeonBeverage.setItemClickListener(object: OnItemClickListener {
////                    override fun onItemClicked(view: View, pos: Int) {
////                        val intent = Intent(context, OrderActivity::class.java)
////                        // 인텐트에 필요한 정보 넣기
////                        val item = adapterNagmyeonBeverage.specificMenuList[pos]
////                        intent.putExtra("parcel", item)
////                        intent.putExtra("candelivery", false)
////                        intent.putExtra("shopTitle", title)
////                        startActivity(intent)
////                    }
////                })
////                dataNagmyeon.add(MenuLinear("주류메뉴", specificDataNagmyeonMenu, adapterNagmyeonMenu, 2))
////                specificMenuLinearAdapter.menuLinearList = dataNagmyeon
////
////                textList.add(TabRecycler("메뉴(주반장이 추천함)", true))
////                textList.add(TabRecycler("음료(시원함)", false))
////                textList.add(TabRecycler("주류메뉴", false))
////                textList.add(TabRecycler("메뉴", false))
////                textList.add(TabRecycler("음료", false))
////                textList.add(TabRecycler("주류메뉴", false))
////
////                textList.add(TabRecycler("메뉴", false))
////                textList.add(TabRecycler("음료", false))
////                textList.add(TabRecycler("주류메뉴", false))
////                specificMenuTabRecyclerAdapter.textList = textList
////
////
////            }
////            else -> {
////                val dataHo = ArrayList<MenuLinear>()
////                val specificDataHoMenu = ArrayList<SpecificMenu>()
////                specificDataHoMenu.add(SpecificMenu("1인세트메뉴", "물냉면or비빔냉면or불비빔냉면or열무냉면+만두+석쇠불고기",
////                    16000, R.drawable.food_item_one))
////                specificDataHoMenu.add(SpecificMenu("2인세트메뉴", "(물or비빔or불비빔or열무냉면 중 택2) +만두+석쇠불고기",
////                    26000, R.drawable.food_item_one))
////                specificDataHoMenu.add(SpecificMenu("물냉면", "냉면 맛집 1위\n냉면의 기본 물냉면", 7500, R.drawable.food_item_one))
////                specificDataHoMenu.add(SpecificMenu("비빔냉면", "냉면 맛집 1위\n기본의 맛에 특제소스로 매콤한 비빔냉면", 7500, R.drawable.food_item_one))
////                specificDataHoMenu.add(SpecificMenu("열무냉면", "냉면 맛집 1위\n기본의 맛에 아삭한 열무냉면 (오이X)", 8500, R.drawable.food_item_one))
////                val adapterHoMenu = ItemSpecificMenuAdapter()
////                adapterHoMenu.setItemClickListener(object: OnItemClickListener {
////                    override fun onItemClicked(view: View, pos: Int) {
////                        val intent = Intent(context, OrderActivity::class.java)
////                        val item = adapterHoMenu.specificMenuList[pos]
////                        intent.putExtra("parcel", item)
////                        intent.putExtra("candelivery", true)
////                        intent.putExtra("shopTitle", title)
////                        startActivity(intent)
////                    }
////                })
////                dataHo.add(MenuLinear("메뉴소개", specificDataHoMenu, adapterHoMenu, 0))
////                dataHo.add(MenuLinear("오리장작 바베큐 한마리", specificDataHoMenu, adapterHoMenu, 1))
////                dataHo.add(MenuLinear("통삼겹 장작 바베큐", specificDataHoMenu, adapterHoMenu, 2))
////                dataHo.add(MenuLinear("모듬 장작 바베큐", specificDataHoMenu, adapterHoMenu, 3))
////                dataHo.add(MenuLinear("기타", specificDataHoMenu, adapterHoMenu, 4))
////                specificMenuLinearAdapter.menuLinearList = dataHo
////            }
////        }
////        linearLayoutManager = LinearLayoutManager(context)
////        specific_menu_recycler.apply {
////            layoutManager = linearLayoutManager
////            adapter = specificMenuLinearAdapter
////            addOnScrollListener(object: RecyclerView.OnScrollListener() {
////                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
////                    linearLayoutManager?.let { lm ->
////                        val index = lm.findFirstVisibleItemPosition()
////                        if(dataNagmyeon.size-1 >= index) {
////                            dataNagmyeon[index]?.let { sd ->
////                                if (selectedSubCatPosition != sd.index) {
////                                    selectedSubCatPosition = sd.index
////                                    specificMenuTabRecyclerAdapter.updateList(selectedSubCatPosition)
////                                    specific_menu_tab_recycler.layoutManager?.scrollToPosition(selectedSubCatPosition)
////                                }
////                            }
////                        }
////                    }
////                }
////            })
////        }
//        specific_menu_tab_recycler.apply {
//            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//            adapter = specificMenuTabRecyclerAdapter
//        }
//
//        expandableListView = view.findViewById(R.id.expandableListView)
//        setExpandableList()
//        setListViewHeightInit(expandableListView)
//
//
//    }
//
//    fun setExpandableList() {
//        val parentList = mutableListOf<MenuTitle>(MenuTitle("메뉴(주방장이 추천함)", 0),
//            MenuTitle("음료(시원함)", 1),
//            MenuTitle("주류메뉴", 2))
//        val childList = mutableListOf<MutableList<MenuSpecific>>(
//            mutableListOf(
//                MenuSpecific("1인세트메뉴", "물냉면or비빔냉면or불비빔냉면or열무냉면+만두+석쇠불고기",
//                    16000, R.drawable.food_item_one),
//                MenuSpecific("2인세트메뉴", "(물or비빔or불비빔or열무냉면 중 택2) +만두+석쇠불고기",
//                    26000, null),
//                MenuSpecific("물냉면", "냉면 맛집 1위\n냉면의 기본 물냉면", 7500, R.drawable.nagmyeon_mul),
//                MenuSpecific("비빔냉면", "냉면 맛집 1위\n기본의 맛에 특제소스로 매콤한 비빔냉면", 7500, R.drawable.nagmyeon_bibim),
//                MenuSpecific("열무냉면", "냉면 맛집 1위\n기본의 맛에 아삭한 열무냉면 (오이X)", 8500, R.drawable.nagmyeon_yeolmu)
//            ),
//            mutableListOf(
//                MenuSpecific("수제살얼음식혜", null, 16000, R.drawable.food_item_one),
//                MenuSpecific("콜라", null, 26000, R.drawable.food_item_one),
//                MenuSpecific("사이다", null, 7500, R.drawable.food_item_one)
//            ),
//            mutableListOf(
//                MenuSpecific("수제살얼음식혜", null, 16000, R.drawable.food_item_one),
//                MenuSpecific("콜라", null, 26000, R.drawable.food_item_one),
//                MenuSpecific("사이다", null, 7500, R.drawable.food_item_one)
//            )
//        )
//        val adapter = ExpandableListAdapter(requireContext(), parentList, childList)
//        expandableListView.setAdapter(adapter)
//        expandableListView.setOnGroupClickListener { parent, view, groupPosition, id ->
//            setListViewHeight(parent, groupPosition)
//            false
//        }
//        expandableListView.setOnChildClickListener(object: ExpandableListView.OnChildClickListener {
//            override fun onChildClick(
//                parent: ExpandableListView?,
//                view: View?,
//                groupPosition: Int,
//                childPosition: Int,
//                id: Long
//            ): Boolean {
//                val item = parent?.expandableListAdapter?.getChild(groupPosition, childPosition)
//                        as MenuSpecific
//                val intent = Intent(context, OrderActivity::class.java)
//                val specificItem = menuSpecificToSpecificMenu(item)
//                intent.putExtra("parcel", specificItem)
//                intent.putExtra("candelivery", false)
//                intent.putExtra("shopTitle", title)
//                startActivity(intent)
//                return true
//            }
//        })
//        for (i in 0 until parentList.size) {
//            expandableListView.expandGroup(i)
//        }
//        val params = expandableListView.layoutParams
//        params.height = 4000
//        expandableListView.layoutParams = params
//        expandableListView.requestLayout()
//
//
//
//    }
//    fun setListViewHeightInit(listView: ExpandableListView) {
//        val listAdapter = listView.expandableListAdapter as ExpandableListAdapter
//        var totalHeight = 0
//        val desiredWidth: Int = View.MeasureSpec.makeMeasureSpec(
//            listView.width,
//            View.MeasureSpec.AT_MOST
//        )
//        for (i in 0 until listAdapter.groupCount) {
//            val groupItem: View = listAdapter.getGroupView(i, true, null, listView)
//            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED)
//            totalHeight += groupItem.measuredHeight
//            for(j in 0 until listAdapter.getChildrenCount(i)) {
//                val listItem: View = listAdapter.getChildView(
//                    i, j, false, null, listView
//                )
//                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED)
//                totalHeight += listItem.measuredHeight
//            }
//        }
//        val params = listView.layoutParams
//        var height = (totalHeight + listView.dividerHeight * (listAdapter.groupCount -1))
//        params.height = height
//        listView.layoutParams = params
//        listView.requestLayout()
//    }
//
//
//    fun setListViewHeight(listView: ExpandableListView, group: Int) {
//        val listAdapter = listView.expandableListAdapter as ExpandableListAdapter
//        var totalHeight = 0
//        val desiredWidth: Int = View.MeasureSpec.makeMeasureSpec(
//            listView.width,
//            View.MeasureSpec.EXACTLY
//        )
//        for (i in 0 until listAdapter.groupCount) {
//            val groupItem: View = listAdapter.getGroupView(i, false, null, listView)
//            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED)
//            totalHeight += groupItem.measuredHeight
//            if(listView.isGroupExpanded(i) && i != group
//                || !listView.isGroupExpanded(i) && i == group) {
//                for (j in 0 until listAdapter.getChildrenCount(i)) {
//                    val listItem: View = listAdapter.getChildView(
//                        i, j, false, null, listView
//                    )
//                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED)
//                    totalHeight += listItem.measuredHeight
//                }
//            }
//        }
//        val params = listView.layoutParams
//        var height = (totalHeight + listView.dividerHeight * (listAdapter.groupCount -1))
//        if(height < 10) {
//            height = 200
//        }
//        params.height = height
//        listView.layoutParams = params
//        listView.requestLayout()
//    }
//    fun menuSpecificToSpecificMenu(item: MenuSpecific) : SpecificMenu {
//        val specific = SpecificMenu(item.title, item.detail, item.price, item.img)
//        return specific
//    }
//
}