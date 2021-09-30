package com.ssacproject.thirdweek.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.dynamic.OnDelegateCreatedListener
import com.ssacproject.thirdweek.OnMinusClickListener
import com.ssacproject.thirdweek.R
import com.ssacproject.thirdweek.activity.MainActivity.Companion.helper
import com.ssacproject.thirdweek.customadapter.BasketAdapter
import com.ssacproject.thirdweek.databinding.ActivityBasketBinding
import com.ssacproject.thirdweek.onDeleteClickListener
import com.ssacproject.thirdweek.onPlusClickListener
import com.ssacproject.thirdweek.room.BasketListAdapter
import com.ssacproject.thirdweek.room.RoomBasket

class BasketActivity : AppCompatActivity() {
    lateinit var binding: ActivityBasketBinding
//    lateinit var basketAdapter: BasketAdapter
    var basketListAdapter = BasketListAdapter()
    var basketSale = false
    var totalPrice = 0
    var totalQuantity = 0
    var standardPrice = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBasketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToolbar(binding.basketToolbar)

        totalPrice = getSharedIntData("prefTotalPrice", "totalPrice")
        totalQuantity = getSharedIntData("prefTotalQuantity", "totalQuantity")
        setPrice()
        setQuan()

        val shopTitle = getSharedStringData("prefShopTitle", "shopTitle")
        binding.basketTitle.text = shopTitle

        val canDelivery = getSharedBooleanData("prefCanDelivery", "canDelivery")
        setCanDeliveryVisible(canDelivery)
        basketListAdapter.apply {
            minusListener = object : OnMinusClickListener {
                override fun onMinusClick(view: View, pos: Int) {
                    val item = currentList[pos]
                    if (item.totalquantity > 1) {
                        totalPrice -= item.standardprice.toInt()
                        totalQuantity -= 1
                        setQuan()
                        setPrice()
                    }
                }
            }
            plusListener = object : onPlusClickListener {
                override fun onPlusClick(view: View, pos: Int) {
                    val item = currentList[pos]
                    totalPrice += item.standardprice.toInt()
                    totalQuantity += 1
                    setQuan()
                    setPrice()
                }
            }
            deleteListener = object : onDeleteClickListener {
                override fun onDeleteClick(view: View, pos: Int) {
                    val item = currentList[pos]
                    totalPrice -= item.totalprice.toInt()
                    totalQuantity -= item.totalquantity.toInt()
                    if (totalQuantity == 0) {
                        binding.nested.visibility = View.GONE
                        binding.basketEmpty.visibility = View.VISIBLE
                        binding.basketDeleteAll.setTextColor(resources.getColor(R.color.ashgray))
                    }
                    setQuan()
                    setPrice()
                }
            }
            baskethelper = helper
            val newList = helper?.roomBasketDao()?.getAll()?: listOf()
            submitList(newList)
        }
        binding.basketRecycler.apply {
            adapter = basketListAdapter
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
            itemAnimator = null
        }
        setTextColorSplit()

        binding.basketCanPackageRadioDelivery.isChecked = true
        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            if(checkedId == R.id.basket_can_package_radio_delivery) {
                binding.basketPackageSale.visibility = View.GONE
                if (basketSale) {
                    totalPrice += 2000
                    setPrice()
                    basketSale = false
                }
            } else if (checkedId == R.id.basket_can_package_radio_package) {
                binding.basketPackageSale.visibility = View.VISIBLE
                basketSale = true
                totalPrice -= 2000
                setPrice()
            }
        }

        binding.basketMore.setOnClickListener {
            finish()
        }
        binding.basketOrderLinear.setOnClickListener {
            val intent = Intent(this, PayActivity::class.java)
            startActivity(intent)
        }

        binding.basketDeleteAll.setOnClickListener {
            var builder = AlertDialog.Builder(this)
            builder.apply {
                setMessage("모두 삭제하시겠습니까?")
                setNegativeButton("취소", DialogInterface.OnClickListener { dialog, which ->

                })
                setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                    setReset()
                    helper?.roomBasketDao()?.deleteAll()
                    val newList = helper?.roomBasketDao()?.getAll()?: listOf()
                    basketListAdapter.submitList(newList)
//                    basketListAdapter.notifyDataSetChanged()
                    binding.nested.visibility = View.GONE
                    binding.basketEmpty.visibility = View.VISIBLE
                    binding.basketDeleteAll.setTextColor(resources.getColor(R.color.ashgray))
                    totalQuantity = 0
                    totalPrice = 0
                })
            }
            var alert = builder.create()
            alert.show()
        }
    }

    override fun onStart() {
        super.onStart()
//        basketAdapter.basketList.clear()
//        basketAdapter.basketList.addAll(helper?.roomBasketDao()?.getAll()?: listOf())
//        basketAdapter.notifyDataSetChanged()
    }

    override fun onPause() {
        super.onPause()
        setSharedData("prefTotalPrice", "totalPrice", totalPrice)
        setSharedData("prefTotalQuantity", "totalQuantity", totalQuantity)

    }

    override fun onStop() {
        super.onStop()
    }
    fun setToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar!!.setDisplayShowTitleEnabled(false)
    }

    fun setPrice() {
        binding.basketTotalPrice.text = "${totalPrice}원"
        binding.basketTotalText.text = "${totalPrice}원 배달 주문하기"
    }
    fun setQuan() {
        binding.basketTotalNum.text = "${totalQuantity}"
    }
    fun setTextColorSplit() {
        var text = binding.basketCanPackageRadioPackage.text.toString()
        val spannableString = SpannableString(text)
        var word = "2,000원 할인"
        val start = text.indexOf(word)
        val end = start + word.length
        spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#F89606")), start, end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.basketCanPackageRadioPackage.text = spannableString
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            else -> return true
        }
        return super.onOptionsItemSelected(item)
    }
    fun setCanDeliveryVisible(canDelivery: Boolean) {
        if (canDelivery) {
            binding.basketDeliveryOnly.visibility = View.GONE
            binding.borderDeliveryOnly.visibility = View.GONE
        } else {
            binding.basketCanPackage.visibility = View.GONE
            binding.borderCanPackage.visibility = View.GONE
        }
    }

    fun setReset() {
        setSharedData("prefShopTitle", "shopTitle", null)
        setSharedData("prefCanDelivery", "canDelivery", false)
        setSharedData("prefTotalPrice", "totalPrice", 0)
        setSharedData("prefTotalQuantity", "totalQuantity", 0)
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
    fun getSharedIntData(name: String, key: String): Int {
        val pref: SharedPreferences = getSharedPreferences(name, Activity.MODE_PRIVATE)
        return pref.getInt(key, 0)
    }
}