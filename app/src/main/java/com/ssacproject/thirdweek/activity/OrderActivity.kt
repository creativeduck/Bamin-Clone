package com.ssacproject.thirdweek.activity

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.google.android.material.appbar.AppBarLayout
import com.ssacproject.thirdweek.R
import com.ssacproject.thirdweek.activity.MainActivity.Companion.helper
import com.ssacproject.thirdweek.customadapter.SpecificMenu
import com.ssacproject.thirdweek.databinding.ActivityOrderBinding
import com.ssacproject.thirdweek.room.RoomBasket

class OrderActivity : AppCompatActivity() {
    lateinit var binding: ActivityOrderBinding
    var scrollRange = -1
    var menu: Menu? = null
    var totalQuantity = 1
    var parcelItem: SpecificMenu? = null
    lateinit var title: String
    var detail: String? = null
    var price: Int = 0
    var img: Int? = null
    var totalPrice = 0
    var standardPrice = 0
    var candelivery = false
    lateinit var shopTitle: String
    var checkList = ArrayList<CheckBox>()
    var priceList = ArrayList<TextView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        parcelItem = intent.getParcelableExtra("parcel")
        title = parcelItem!!.title
        detail = parcelItem?.detail
        price = parcelItem!!.price
        img = parcelItem?.img
        candelivery = intent.getBooleanExtra("candelivery", false)
        shopTitle = intent.getStringExtra("shopTitle")!!

        setSupportActionBar(binding.orderToolbar)
        val actionBar = supportActionBar
        // ???????????? ?????? ??????
        actionBar!!.setDisplayHomeAsUpEnabled(true)

        setLayout(img, price, title, detail, totalQuantity)

        binding.appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { barLayout, verticalOffset ->
            if (scrollRange == -1) {
                scrollRange = barLayout?.totalScrollRange!!
            }
            if (scrollRange + verticalOffset == 0) {
                binding.orderToolbarTextView.text = title
                if (menu != null) {
                    menu!!.getItem(0).setIcon(ContextCompat.getDrawable(this,
                        R.drawable.ic_share_order_shape
                    ))
                }
                actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_shape)
            }
            else {
                binding.orderToolbarTextView.text = ""
                if (menu != null) {
                    menu!!.getItem(0).setIcon(ContextCompat.getDrawable(this,
                        R.drawable.ic_share_order_shape_white
                    ))
                }
                actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_shape_white)
            }
        })
        binding.orderBtnMinus.setOnClickListener {
            if (totalQuantity > 0) {
                totalQuantity -= 1
                totalPrice -= standardPrice
                setBtnChange("${totalQuantity}???", "${totalPrice}???", "${totalQuantity}??? ??????")
                if (totalQuantity < 1) {
                    binding.orderBtnMinus.setImageResource(R.drawable.ic_minus_shape_gray)
                }
                else {
                    binding.orderBtnMinus.setImageResource(R.drawable.ic_minus_shape)
                }
            }
        }

        binding.orderBtnPlus.setOnClickListener {
            binding.orderBtnMinus.setImageResource(R.drawable.ic_minus_shape)
            totalQuantity += 1
            totalPrice += standardPrice
            setBtnChange("${totalQuantity}???", "${totalPrice}???", "${totalQuantity}??? ??????")
        }



        binding.linearOrder.setOnClickListener {
            // ????????? SQLite??? ????????? ????????????
            var tmp: String? = getSharedStringData("prefShopTitle", "shopTitle")
            var menumore = ""
            if (checkList[0].isChecked) {
                menumore += checkList[0].text.toString() + " (" + priceList[0].text.toString().substring(1) + ")"
            }
            for (i in 1 until checkList.size) {
                if (checkList[i].isChecked) {
                    menumore += " / " + checkList[i].text.toString() + " (" + priceList[i].text.toString().substring(1) + ")"
                }
            }
            val basket = RoomBasket(null, title, price.toLong(), menumore, totalQuantity.toLong(), totalPrice.toLong(), (totalPrice/totalQuantity).toLong())
            if (tmp == null) {
                setAll(shopTitle, candelivery, totalPrice, totalQuantity)
                // ??? ????????? ????????? SQLite??? ??????
                helper?.roomBasketDao()?.insert(basket)
                Toast.makeText(this, "??????????????? ????????? ??????????????????", Toast.LENGTH_SHORT).show()
                finish()
            } else if (tmp.equals(shopTitle)) {
                // ??? ????????? ????????? SQLite??? ????????? ??????
                // ????????? ????????? ????????? ??????.
                var flag = false
                val titleList = helper?.roomBasketDao()?.getTitleList(title)?: listOf()
                if (titleList.size == 0) {
                    helper?.roomBasketDao()?.insert(basket)
                } else {
                    for(i in 0 until titleList.size) {
                        if (sameContent(basket, titleList[i])) {
                            var id = titleList[i].num
                            var curQuantity = titleList[i].totalquantity
                            var curPrice = titleList[i].totalprice
                            helper?.roomBasketDao()?.updateTQ((curQuantity+totalQuantity), (curPrice+totalPrice), id)
                            flag = true
                        }
                    }
                    if (!flag)
                        helper?.roomBasketDao()?.insert(basket)
                }
                var totPrice = getSharedIntData("prefTotalPrice", "totalPrice")
                var totQuantity = getSharedIntData("prefTotalQuantity", "totalQuantity")
                totPrice += totalPrice
                totQuantity += totalQuantity
                setSharedData("prefTotalPrice", "totalPrice", totPrice)
                setSharedData("prefTotalQuantity", "totalQuantity", totQuantity)

                Toast.makeText(this, "??????????????? ????????? ??????????????????", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                // ??????????????? ?????????
                val builder = AlertDialog.Builder(this)
                builder.apply {
                    setTitle("?????????????????? ?????? ????????? ????????? ?????? ??? ????????????.")
                    setMessage("???????????? ????????? ??????????????? ?????? ?????? ????????? ?????? ????????? ???????????????.")
                    setPositiveButton("??????",
                            DialogInterface.OnClickListener { dialog, id ->
                                // ??????(??????)?????? ????????? ?????? ???????????? ??? ??????????????? ?????? SQLite ????????????????????? ?????? ?????? ??? ?????????
                                setAll(shopTitle, candelivery, totalPrice, totalQuantity)
                                helper?.roomBasketDao()?.deleteAll()
                                helper?.roomBasketDao()?.insert(basket)
                                Toast.makeText(context, "??????????????? ????????? ??????????????????", Toast.LENGTH_SHORT).show()
                                finish()
                            })
                    setNegativeButton("??????",
                            DialogInterface.OnClickListener { dialog, id ->
                                // ?????? ?????? ????????? ?????? ??????. ???????????? ?????????
                            })
                }
                val alertDialog = builder.create()
                alertDialog.show()
            }
        }
        setCheck(binding.checkBob, binding.checkBobPrice)
        setCheck(binding.checkCider, binding.checkCiderPrice)
        setCheck(binding.checkCoke, binding.checkCokePrice)

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

    fun getSharedIntData(name: String, key: String): Int {
        val pref: SharedPreferences = getSharedPreferences(name, Activity.MODE_PRIVATE)
        return pref.getInt(key, 0)
    }
    fun getSharedStringData(name: String, key: String): String? {
        val pref: SharedPreferences = getSharedPreferences(name, Activity.MODE_PRIVATE)
        return pref.getString(key, null)
    }
    fun sameContent(basket1: RoomBasket, basket2: RoomBasket): Boolean {
        if (basket1.price != basket2.price) {
            return false
        } else if(basket1.menumore == null && basket2.menumore == null) {
            return true
        } else if (basket1.menumore == null || basket2.menumore == null) {
            return false
        } else if (basket1.menumore != null && basket2.menumore != null && !basket1.menumore.equals(basket2.menumore)) {
            return false
        }
        return true
    }

    fun setLayout(img: Int?, price: Int, title: String, detail: String?, totalQuantity: Int) {
        if (img == null) {
            binding.orderImage.visibility = View.GONE
        } else {
            binding.orderImage.setImageResource(img!!)
            binding.tmpview.visibility = View.GONE
        }
        binding.orderDefaultPrice.text = "${price}???"
        binding.cardTitle.text = title
        binding.cardDetail.text = detail
        binding.orderTotalPrice.text = "${price}???"
        totalPrice = price
        standardPrice = price
        binding.orderQuantityToBasket.text = "${totalQuantity}??? ??????"
        binding.orderQuantity.text = "${totalQuantity}???"
    }
    fun setAll(shopTitle: String, candelivery: Boolean, totalPrice: Int, totalQuantity: Int) {
        setSharedData("prefShopTitle", "shopTitle", shopTitle)
        setSharedData("prefCanDelivery", "canDelivery", candelivery)
        setSharedData("prefTotalPrice", "totalPrice", totalPrice)
        setSharedData("prefTotalQuantity", "totalQuantity", totalQuantity)
    }

    fun setCheck(checkbox: CheckBox, price: TextView) {
        checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
            val tmp = price.text.toString()
            val real = tmp.substring(1, tmp.length-1).toInt()
            if (checkbox.isChecked) {
                standardPrice+= real
                totalPrice += totalQuantity * real
                binding.orderTotalPrice.text = "${totalPrice}???"
            } else {
                standardPrice -= real
                totalPrice -= totalQuantity * real
                binding.orderTotalPrice.text = "${totalPrice}???"
            }
        }
        checkList.add(checkbox)
        priceList.add(price)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            else -> return true
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menu = menu
        getMenuInflater().inflate(R.menu.specific_toolbar_menu, this.menu)
        return true
    }

    fun setBtnChange(total: String, totalPrice: String, totalQuantity: String) {
        binding.orderQuantity.text = total
        binding.orderTotalPrice.text = totalPrice
        binding.orderQuantityToBasket.text = totalQuantity
    }

}