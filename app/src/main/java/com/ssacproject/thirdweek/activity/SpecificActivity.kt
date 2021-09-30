package com.ssacproject.thirdweek.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AlphaAnimation
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.google.android.material.badge.ExperimentalBadgeUtils
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.ssacproject.thirdweek.*
import com.ssacproject.thirdweek.activity.MainActivity.Companion.heartHelper
import com.ssacproject.thirdweek.activity.MainActivity.Companion.helper
import com.ssacproject.thirdweek.customadapter.SelectFoodItem
import com.ssacproject.thirdweek.customadapter.TabRecycler
import com.ssacproject.thirdweek.customadapter.TabRecyclerAdapter
import com.ssacproject.thirdweek.customadapter.ViewPagerFragmentAdapter
import com.ssacproject.thirdweek.databinding.ActivitySpecificBinding
import com.ssacproject.thirdweek.databinding.GridDeliveryOnlyBinding
import com.ssacproject.thirdweek.food_fragment_real.FragmentDelivery
import com.ssacproject.thirdweek.room.RoomHeart
import java.util.ArrayList

class SpecificActivity : AppCompatActivity() {
    lateinit var binding: ActivitySpecificBinding
    var parcelItem: SelectFoodItem? = null
    var scrollRange = -1
    var menu: Menu? = null
    lateinit var title: String
    var rating: Double = 0.0
    var rating_nums: Int = 0
    lateinit var menu_detail: String
    var minTime: Int = 0
    var maxTime: Int = 0
    var iconImage:Int = 0
    var leastprice: Int = 0
    var tip: Int = 0
    var canPackage: Boolean = false
    var shopImage: Int? = null
    var orderQuantity: Int = 0
    lateinit var location: String
    var farfrom: Double = 0.0
    var heart: Int = 0
    var ceoReviewNums: Int = 0
    var payType: String? = null
    var heartFlag = false
    var badgeDrawable: BadgeDrawable? = null

    var textList = ArrayList<TabRecycler>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpecificBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.specificToolbar)
        val actionBar = supportActionBar
        // 뒤로가기 버튼 생성
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar!!.setDisplayShowCustomEnabled(true)

        parcelItem = intent.getParcelableExtra("parcelItem")
        title = parcelItem!!.title
        rating = parcelItem!!.rating
        rating_nums = parcelItem!!.rating_nums
        menu_detail = parcelItem!!.menu
        minTime = parcelItem!!.minTime
        maxTime = parcelItem!!.maxTime
        iconImage = parcelItem!!.iconImage
        leastprice = parcelItem!!.leastprice
        tip = parcelItem!!.tip
        canPackage = parcelItem!!.canPackage
        shopImage = parcelItem?.shopImage
        orderQuantity = parcelItem!!.orderQuantity
        location = parcelItem!!.location
        farfrom = parcelItem!!.farfrom
        val tmp = getSharedIntData("prefHeartNum", "prefHeartNum${title}")
        if (tmp == 0) {
            heart = parcelItem!!.heart
        } else {
            heart = tmp
        }
        ceoReviewNums = parcelItem!!.ceoReviewNums
        payType = parcelItem?.payType

        val minimum_text = "${leastprice}원"
        val time_text = "${minTime}~${maxTime}분 소요 예상"
        val tip_text = "${tip}원"
        val address_farfrom = "배달주소로부터 약 ${farfrom}km"

        if (shopImage == null) {
            binding.specificImage.visibility = View.GONE
            binding.tmpview.visibility = View.VISIBLE
        } else {
            binding.tmpview.visibility = View.GONE
            binding.specificImage.setImageResource(shopImage!!)
        }

        if (payType == null) {
            binding.deliveryOnly.payType.visibility = View.GONE
            binding.deliveryOnly.howtopay.visibility = View.GONE
        }
        else {
            binding.deliveryOnly.payType.text = payType
        }
        // 포장/방문주문도 가능하다면
        if (canPackage) {
            binding.deliveryOnly.gridDeliveryOnlyLayout.visibility = View.GONE
            val tabs = binding.canPackageTabLayout
            tabs.addTab(tabs.newTab().setText("배달주문"))
            tabs.addTab(tabs.newTab().setText("포장/방문주문"))
            val fragmentDelivery = FragmentDeliveryOnly(minimum_text, payType, time_text, tip_text)
            val fragmentCanPackage = FragmentCanPackage(this, location, address_farfrom)

            supportFragmentManager.beginTransaction().replace(R.id.container, fragmentDelivery).commit()
            tabs.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    val pos = tab!!.position
                    var selected: Fragment? = null
                    when (pos) {
                        0 -> selected = fragmentDelivery
                        else -> selected = fragmentCanPackage
                    }
                    supportFragmentManager.beginTransaction().replace(R.id.container, selected).commit()
                }
                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }
                override fun onTabReselected(tab: TabLayout.Tab?) {
                }
            })
        }
        // 오직 배달만 가능하다면
        else {
            binding.container.visibility = View.GONE
            binding.canPackageTabLayout.visibility = View.GONE
            binding.deliveryOnly.minimumPrice.text = minimum_text
            binding.deliveryOnly.deliveryTime.text = time_text
            binding.deliveryOnly.deliveryTip.text = tip_text
        }

        setCardInfo(title, "${rating}", "${rating_nums}", "${ceoReviewNums}", rating.toFloat())

        val tabTitle = listOf("메뉴", "정보", "리뷰")
        val fragmentList = listOf(getSecificFrag(title), FragmentDelivery(), FragmentSpecificReview())
        setTabViewPager(this, binding.specificTabLayout,
            binding.specificViewpager, tabTitle, fragmentList)

        val tabRecyclerAdapter = TabRecyclerAdapter()
        tabRecyclerAdapter.textList = textList
        binding.recyclerTab.apply {
            adapter = tabRecyclerAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            visibility = View.INVISIBLE
        }
//        val animation = AlphaAnimation(0f,1f)
//        animation.duration = 1000
//        binding.recyclerTab.animation = animation
        binding.stickyScrollView.run {
            header = binding.stickLinear
            stickListener = { _ ->
//                Log.d("LOGGER_TAG", "stickListener")
                binding.recyclerTab.visibility = View.VISIBLE
            }
            freeListener = { _ ->
//                Log.d("LOGGER_TAG", "freeListener")
                binding.recyclerTab.visibility = View.INVISIBLE
            }
        }
        binding.specificViewpager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if(position != 0)
                    binding.recyclerTab.visibility = View.GONE
            }
        })


        binding.appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { barLayout, verticalOffset ->
            if (scrollRange == -1) {
                scrollRange = barLayout?.totalScrollRange!!
            }
            if (scrollRange + verticalOffset == 0) {
                binding.toolbarTextView.text = title
                if (menu != null) {
                    menu!!.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_search_shape))
                }
                actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_shape)
            }
            else {
                binding.toolbarTextView.text = ""
                if (menu != null) {
                    menu!!.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_search_shape_white))
                }
                actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_shape_white)
            }
        })
        
        heartFlag = getSharedBooleanData("prefHeart", "prefHeart${title}")
        binding.cardInfo.heartText.apply {
            if (heartFlag) {
                setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_shape, 0, 0, 0)
            } else {
                setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_favorite_border_24, 0, 0, 0)
            }
            val heartText = "찜 ${heart}"
            text = heartText
            setOnClickListener {
                if (heartFlag) {
                    heartFlag = false
                    setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_favorite_border_24, 0, 0, 0)
                    // 즉, 여기선 새로운 heart 변수를 만드는 게 아니라 어떤 조건을 통해서 찾아야 한다.
                    val roomHeart = RoomHeart(title, rating, rating_nums, menu_detail, minTime, maxTime, iconImage,
                        leastprice, tip, canPackage, shopImage, orderQuantity, location, farfrom, heart, ceoReviewNums, payType)
                    heartHelper?.roomHeartDao()?.delete(roomHeart)
                    heart -= 1
                    text = "찜 ${heart}"
                } else {
                    heartFlag = true
                    heart += 1
                    text = "찜 ${heart}"
                    setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_shape, 0, 0, 0)
                    val roomHeart = RoomHeart(title, rating, rating_nums, menu_detail, minTime, maxTime, iconImage,
                        leastprice, tip, canPackage, shopImage, orderQuantity, location, farfrom, heart, ceoReviewNums, payType)
                    heartHelper?.roomHeartDao()?.insert(roomHeart)
                }
                setSharedData("prefHeart", "prefHeart${title}", heartFlag)
                setSharedData("prefHeartNum", "prefHeartNum${title}", heart)
            }
            
        }

        binding.specificFab.apply {
            setOnClickListener {
                val list = helper?.roomBasketDao()?.getAll()?: listOf()
                if (list.size <= 0) {
                    Toast.makeText(applicationContext, "메뉴를 선택해주세요", Toast.LENGTH_SHORT).show()
                } else {
                    val intent = Intent(applicationContext, BasketActivity::class.java)
                    startActivity(intent)
                }
            }
        }

        binding.specificFab.viewTreeObserver.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener {
            @SuppressLint("UnsafeOptInUsageError")
            override fun onGlobalLayout() {
                badgeDrawable = BadgeDrawable.create(this@SpecificActivity)
                badgeDrawable!!.number = 0
                badgeDrawable!!.setVisible(false)
                badgeDrawable!!.horizontalOffset = 55
                badgeDrawable!!.verticalOffset = 55
                badgeDrawable!!.backgroundColor = resources.getColor(R.color.white)
                badgeDrawable!!.badgeTextColor = resources.getColor(R.color.bamin)
                BadgeUtils.attachBadgeDrawable(badgeDrawable!!, binding.specificFab, null)
                binding.specificFab.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }

    override fun onResume() {
        super.onResume()
    }
    override fun onStart() {
        super.onStart()
        val totQuantity = getSharedIntData("prefTotalQuantity", "totalQuantity")
        if (badgeDrawable != null) {
            if (totQuantity == 0) {
                badgeDrawable!!.setVisible(false)
            } else {
                badgeDrawable!!.number = totQuantity
                badgeDrawable!!.setVisible(true)
            }
        }
    }
    fun setCardInfo(title: String, rating: String, rating_num: String, ceo: String, rate: Float) {
        binding.cardInfo.specificInfoTitle.text = title
        binding.cardInfo.specificInfoRating.text = rating
        binding.cardInfo.specificInfoReviews.text = rating_num
        binding.cardInfo.specificInfoCeoComment.text = ceo
        binding.cardInfo.specificInfoRatingbar.rating = rate
    }
    fun setTabViewPager(fragmentActivity: FragmentActivity, tabLayout: TabLayout,
                        viewpager: ViewPager2, tabTitle: List<String>, fragmentList: List<Fragment>,) {
        val adapter = ViewPagerFragmentAdapter(fragmentActivity)
        adapter.fragmentList = fragmentList
        viewpager.adapter = adapter
        viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        TabLayoutMediator(tabLayout, viewpager) {tab, position ->
            tab.text = tabTitle[position]
        }.attach()
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
    fun updatePagerHeightForChild(view: View, pager: ViewPager2) {
        view.post {
            val wMeasureSpec =
                View.MeasureSpec.makeMeasureSpec(view.width, View.MeasureSpec.EXACTLY)
            val hMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            view.measure(wMeasureSpec, hMeasureSpec)
            if(pager.layoutParams.height != view.measuredHeight) {
                pager.layoutParams = (pager.layoutParams)
                    .also {
                            lp -> lp.height = view.measuredHeight
                    }
            }
        }
    }
    fun setSharedData(name: String, key: String, data: Boolean) {
        val pref: SharedPreferences = getSharedPreferences(name, Activity.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = pref.edit()
        editor.putBoolean(key, data)
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
    fun getSharedIntData(name: String, key: String): Int {
        val pref: SharedPreferences = getSharedPreferences(name, Activity.MODE_PRIVATE)
        return pref.getInt(key, 0)
    }
    fun getSecificFrag(title: String): FragmentSpecificMenu {
        val fragtmp = FragmentSpecificMenu(binding.recyclerTab)
        val list = ArrayList<String>()
        when (title) {
            "얼음골칡냉면" -> {
                list.add("메뉴(주방장이 추천)")
                list.add("음료(시원함)")
                list.add("주류메뉴")
                textList.add(TabRecycler("메뉴(주방장이 추천)", true))
                textList.add(TabRecycler("음료(시원함)", false))
                textList.add(TabRecycler("주류메뉴", false))
            }
            else -> {
                list.add("메인메뉴(주방장 추천)")
                list.add("메인으론 애매하지만 맛있는 메뉴")
                list.add("서브메뉴")
                textList.add(TabRecycler("메인메뉴(주방장 추천)", true))
                textList.add(TabRecycler("메인으론 애매하지만 맛있는 메뉴", false))
                textList.add(TabRecycler("서브메뉴", false))
            }
        }

        textList.add(TabRecycler("주류메뉴", false))
        textList.add(TabRecycler("주류메뉴", false))
        textList.add(TabRecycler("주류메뉴", false))

        var bundle = Bundle()
        bundle.putString("title", title)
        bundle.putStringArrayList("titleList", list)
        fragtmp.arguments = bundle
        return fragtmp
    }
}