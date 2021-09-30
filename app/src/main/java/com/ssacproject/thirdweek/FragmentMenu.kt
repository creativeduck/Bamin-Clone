package com.ssacproject.thirdweek

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssacproject.thirdweek.activity.SpecificActivity
import com.ssacproject.thirdweek.customadapter.SelectFoodItem
import com.ssacproject.thirdweek.customadapter.SelectMenuFoodItemAdapter
import com.ssacproject.thirdweek.room.RoomBasket

class FragmentMenu : Fragment() {
    lateinit var recyclerView: RecyclerView
    var selectAdapter: SelectMenuFoodItemAdapter = SelectMenuFoodItemAdapter()
    var selectList1 = ArrayList<SelectFoodItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectList1 = arguments?.getParcelableArrayList<SelectFoodItem>("list") as ArrayList<SelectFoodItem>
        recyclerView = view.findViewById(R.id.recyclerView)
        selectAdapter.apply {
            selectList = selectList1
            listener = object: OnItemClickListener {
                override fun onItemClicked(view: View, pos: Int) {
                    val intent = Intent(context, SpecificActivity::class.java)
                    val item = selectList[pos]
                    intent.putExtra("parcelItem", item)
                    startActivity(intent)
                }
            }
        }
        recyclerView.apply {
            layoutManager  = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = selectAdapter
        }
    }


    // 유형에 따라 다르게 정렬하기
    fun changeDataSort(type: String) {
        when (type) {
            "chipTopRatings" -> {
                selectAdapter.selectList.sortByDescending {
                    it.rating
                }
            }
            "chipTipLowest" -> {
                selectAdapter.selectList.sortBy {
                    it.tip
                }
            }
            "chipFastest" -> {
                selectAdapter.selectList.sortBy {
                    it.minTime
                }
            }
            "chipLeastPrice" -> {
                selectAdapter.selectList.sortBy {
                    it.leastprice
                }
            }
            "chipHeart" -> {
                selectAdapter.selectList.sortByDescending {
                    it.heart
                }
            }
            "chipNear" -> {
                selectAdapter.selectList.sortBy {
                    it.farfrom
                }
            }
            "chipMostOrdered" -> {
                selectAdapter.selectList.sortByDescending {
                    it.orderQuantity
                }
            }
            "chipNormal" -> {
                selectAdapter.selectList.sortBy {
                    it.title
                }
            }
            else -> {
                selectAdapter.selectList.sortBy {
                    it.title
                }
            }
        }
        selectAdapter.notifyDataSetChanged()
    }

}