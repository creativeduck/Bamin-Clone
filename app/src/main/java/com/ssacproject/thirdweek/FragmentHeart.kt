package com.ssacproject.thirdweek

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssacproject.thirdweek.activity.MainActivity.Companion.heartHelper
import com.ssacproject.thirdweek.activity.SpecificActivity
import com.ssacproject.thirdweek.customadapter.SelectFoodItem
import com.ssacproject.thirdweek.room.HeartListAdapter
import com.ssacproject.thirdweek.room.RoomHeart

class FragmentHeart : Fragment() {
    lateinit var recyclerHeart: RecyclerView
    lateinit var heart_num: TextView
    var heartListAdapter = HeartListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_heart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        heart_num = view.findViewById(R.id.heart_num)
        heartListAdapter.let {
            it.listener = object: OnItemClickListener {
                override fun onItemClicked(view: View, pos: Int) {
                    val intent = Intent(context, SpecificActivity::class.java)
                    val heartItem = it.currentList[pos]
                    val selectItem = roomHeartToSelectFoodItem(heartItem)
                    intent.putExtra("parcelItem", selectItem)
                    startActivity(intent)
                }
            }
            it.longListener = object: OnItemLongClickListener {
                override fun onItemLongClicked(view: View, pos: Int) {
                    val builder = AlertDialog.Builder(context)
                    builder.apply {
                        setTitle("찜을 취소하시겠습니까?")
                        setPositiveButton("확인", DialogInterface.OnClickListener { dialogInterface, id ->
                            val item = it.currentList[pos]
                            heartHelper?.roomHeartDao()?.delete(item)
                            val newList = heartHelper?.roomHeartDao()?.getAll()?: listOf()
                            heartListAdapter.submitList(newList)
                            setSharedData("prefHeart", "prefHeart${item.title}", false)
                            val heart = getSharedIntData("prefHeartNum", "prefHeartNum${item.title}")
                            setSharedData("prefHeartNum", "prefHeartNum${item.title}", heart-1)
                            var total = heartListAdapter.currentList.size
                            heart_num.text = "총 ${total}개"
                        })
                        setNegativeButton("취소", DialogInterface.OnClickListener { dialogInterface, id ->
                        })
                    }
                    val alert = builder.create()
                    alert.show()
                }
            }
        }
        recyclerHeart = view.findViewById(R.id.recyclerHeart)
        recyclerHeart.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = heartListAdapter
        }

    }

    fun roomHeartToSelectFoodItem(item: RoomHeart) : SelectFoodItem {
        val selectFoodItem = SelectFoodItem(item.title, item.rating, item.rating_nums,
                                            item.menu, item.minTime, item.maxTime, item.iconImage,
                                            item.leastprice, item.tip, item.canPackage,
                                            item.shopImage, item.orderQuantity, item.location,
                                            item.farfrom, item.heart, item.ceoReviewNums, item.payType)
        return selectFoodItem
    }

    override fun onStart() {
        super.onStart()
        Log.d("HeartMessage", "now")
        val newList = heartHelper?.roomHeartDao()?.getAll()?: listOf()
        heartListAdapter.submitList(newList)
        var total = heartListAdapter.currentList.size
        heart_num.text = "총 ${total}개"
    }

    override fun onResume() {
        super.onResume()
        Log.d("HeartMessage", "nowResue")
    }
    fun setSharedData(name: String, key: String, data: Boolean) {
        val pref: SharedPreferences = requireActivity().getSharedPreferences(name, Activity.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = pref.edit()
        editor.putBoolean(key, data)
        editor.apply()
    }
    fun setSharedData(name: String, key: String, data: Int) {
        val pref: SharedPreferences = requireActivity().getSharedPreferences(name, Activity.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = pref.edit()
        editor.putInt(key, data)
        editor.apply()
    }
    fun getSharedIntData(name: String, key: String): Int {
        val pref: SharedPreferences = requireActivity().getSharedPreferences(name, Activity.MODE_PRIVATE)
        return pref.getInt(key, 0)
    }
    fun getSharedBooleanData(name: String, key: String): Boolean {
        val pref: SharedPreferences = requireActivity().getSharedPreferences(name, Activity.MODE_PRIVATE)
        return pref.getBoolean(key, false)
    }
}