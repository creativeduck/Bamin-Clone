package com.ssacproject.thirdweek

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.AbsListView
import android.widget.ExpandableListView
import android.widget.ListAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssacproject.thirdweek.activity.OrderActivity
import com.ssacproject.thirdweek.customadapter.*
import com.ssacproject.thirdweek.databinding.TabReyclerBinding

class FragmentSpecificMenu(val tabrecycler: RecyclerView) : Fragment() {
    var linearLayoutManager: LinearLayoutManager? = null
    private var selectedSubCatPosition: Int = 0
    lateinit var expandableListView: ExpandableListView
    lateinit var title: String
    var textList = ArrayList<TabRecycler>()
    var titleList = ArrayList<String>()
    val parentList = mutableListOf<MenuTitle>()
    lateinit var childList: MutableList<MutableList<MenuSpecific>>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_specific_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title = arguments?.getString("title")!!
        titleList = arguments?.getStringArrayList("titleList") as ArrayList<String>

        expandableListView = view.findViewById(R.id.expandableListView)
        setExpandableList()
        setListViewHeightInit(expandableListView)

        expandableListView.setOnScrollListener(object: AbsListView.OnScrollListener {
            override fun onScroll(view: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
                var index = firstVisibleItem
                Log.d("Parent", "index = ${index}")
            }

            override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {

            }
        })

        var index = expandableListView.expandableListAdapter.groupCount
        for (i in 0 until index) {
            var parentItem = expandableListView.expandableListAdapter.getGroupView(i, false, null, expandableListView)
            parentItem.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    Log.d("Parent", "index = ${i}")
                }
            })
        }




    }

    fun setExpandableList() {
        for (i in 0 until titleList.size) {
            parentList.add(MenuTitle(titleList[i], i))
            if (i == 0) {
                textList.add(TabRecycler(titleList[i], true))
            } else {
                textList.add(TabRecycler(titleList[i], false))
            }
        }

        childList = mutableListOf<MutableList<MenuSpecific>>(
            mutableListOf(
                MenuSpecific("1인세트메뉴", "물냉면or비빔냉면or불비빔냉면or열무냉면+만두+석쇠불고기",
                    16000, R.drawable.food_item_one),
                MenuSpecific("2인세트메뉴", "(물or비빔or불비빔or열무냉면 중 택2) +만두+석쇠불고기",
                    26000, null),
                MenuSpecific("물냉면", "냉면 맛집 1위\n냉면의 기본 물냉면", 7500, R.drawable.nagmyeon_mul),
                MenuSpecific("비빔냉면", "냉면 맛집 1위\n기본의 맛에 특제소스로 매콤한 비빔냉면", 7500, R.drawable.nagmyeon_bibim),
                MenuSpecific("열무냉면", "냉면 맛집 1위\n기본의 맛에 아삭한 열무냉면 (오이X)", 8500, R.drawable.nagmyeon_yeolmu)
            ),
            mutableListOf(
                MenuSpecific("수제살얼음식혜", null, 16000, R.drawable.food_item_one),
                MenuSpecific("콜라", null, 26000, R.drawable.food_item_one),
                MenuSpecific("사이다", null, 7500, R.drawable.food_item_one)
            ),
            mutableListOf(
                MenuSpecific("수제살얼음식혜", null, 16000, R.drawable.food_item_one),
                MenuSpecific("콜라", null, 26000, R.drawable.food_item_one),
                MenuSpecific("사이다", null, 7500, R.drawable.food_item_one)
            )
        )

        val adapter = ExpandableListAdapter(requireContext(), parentList, childList)
        expandableListView.setAdapter(adapter)
        expandableListView.setOnGroupClickListener { parent, view, groupPosition, id ->
            setListViewHeight(parent, groupPosition)
            false
        }
//        expandableListView.setOnScrollChangeListener(object: View.OnScrollChangeListener {
//            override fun onScrollChange(view: View?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
//                Log.d("Expand", "index")
//           }
//        })

        expandableListView.setOnChildClickListener(object: ExpandableListView.OnChildClickListener {
            override fun onChildClick(
                parent: ExpandableListView?,
                view: View?,
                groupPosition: Int,
                childPosition: Int,
                id: Long
            ): Boolean {
                val item = parent?.expandableListAdapter?.getChild(groupPosition, childPosition)
                            as MenuSpecific
                val intent = Intent(context, OrderActivity::class.java)
                var candelivery = false
                when (title) {
                    "얼음골칡냉면" -> candelivery = false
                    else -> candelivery = true
                }
                val specificItem = menuSpecificToSpecificMenu(item)
                intent.putExtra("parcel", specificItem)
                intent.putExtra("candelivery", candelivery)
                intent.putExtra("shopTitle", title)
                startActivity(intent)
                return true
            }
        })
        for (i in 0 until parentList.size) {
            expandableListView.expandGroup(i)
        }
        val params = expandableListView.layoutParams
        params.height = 4000
        expandableListView.layoutParams = params
        expandableListView.requestLayout()



    }
    fun setListViewHeightInit(listView: ExpandableListView) {
        val listAdapter = listView.expandableListAdapter as ExpandableListAdapter
        var totalHeight = 0
        val desiredWidth: Int = View.MeasureSpec.makeMeasureSpec(
            listView.width,
            View.MeasureSpec.AT_MOST
        )
        for (i in 0 until listAdapter.groupCount) {
            val groupItem: View = listAdapter.getGroupView(i, true, null, listView)
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED)
            totalHeight += groupItem.measuredHeight
            for(j in 0 until listAdapter.getChildrenCount(i)) {
                val listItem: View = listAdapter.getChildView(
                    i, j, false, null, listView
                )
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED)
                totalHeight += listItem.measuredHeight
            }
        }
        val params = listView.layoutParams
        var height = (totalHeight + listView.dividerHeight * (listAdapter.groupCount -1))
        params.height = height
        listView.layoutParams = params
        listView.requestLayout()
    }


    fun setListViewHeight(listView: ExpandableListView, group: Int) {
        val listAdapter = listView.expandableListAdapter as ExpandableListAdapter
        var totalHeight = 0
        val desiredWidth: Int = View.MeasureSpec.makeMeasureSpec(
            listView.width,
            View.MeasureSpec.EXACTLY
        )
        for (i in 0 until listAdapter.groupCount) {
            val groupItem: View = listAdapter.getGroupView(i, false, null, listView)
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED)
            totalHeight += groupItem.measuredHeight
            if(listView.isGroupExpanded(i) && i != group
                || !listView.isGroupExpanded(i) && i == group) {
                for (j in 0 until listAdapter.getChildrenCount(i)) {
                    val listItem: View = listAdapter.getChildView(
                        i, j, false, null, listView
                    )
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED)
                    totalHeight += listItem.measuredHeight
                }
            }
        }
        val params = listView.layoutParams
        var height = (totalHeight + listView.dividerHeight * (listAdapter.groupCount -1))
        if(height < 10) {
            height = 200
        }
        params.height = height
        listView.layoutParams = params
        listView.requestLayout()
    }
    fun menuSpecificToSpecificMenu(item: MenuSpecific) : SpecificMenu {
        val specific = SpecificMenu(item.title, item.detail, item.price, item.img)
        return specific
    }

}