package com.ssacproject.thirdweek.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayoutMediator
import com.ssacproject.thirdweek.R
import com.ssacproject.thirdweek.customadapter.ViewPagerFragmentAdapter
import com.ssacproject.thirdweek.databinding.ActivityRealMainBinding
import com.ssacproject.thirdweek.food_fragment_real.*
import kotlinx.coroutines.internal.artificialFrame

class RealMainActivity : AppCompatActivity() {
    lateinit var binding: ActivityRealMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRealMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var tabTitles = listOf<String>("배달", "배민1", "포장", "B마트", "쇼핑라이브",
                                        "선물하기", "전국별미")
        var fragmentList = listOf<Fragment>(
            FragmentDelivery(), FragmentBaminOne(),
            FragmentPackaging(), FragmentBMart(), FragmentShoppingLive(),
            FragmentPresent(), FragmentDomestic()
        )

        val adapter = ViewPagerFragmentAdapter(this)
        adapter.fragmentList = fragmentList
        binding.realMainViewpager.let {
            it.adapter = adapter
            it.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }
        TabLayoutMediator(binding.tabLayout, binding.realMainViewpager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()

        setToolbar(binding.toolbar)

        val pos = intent.getIntExtra("position", 0)
        binding.realMainViewpager.setCurrentItem(pos, false)
        binding.bottomnavi.apply {
            setItemIconTintList(null)
            itemRippleColor = null
        }
        binding.baminFab.setOnClickListener {
            finish()
        }

        binding.bottomnavi.setOnNavigationItemSelectedListener(object: BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.heart -> {
                        val intent = Intent(applicationContext, HeartActivity::class.java)
                        startActivity(intent)
                        return true
                    }
                    else -> return false
                }
            }
        })

    }
    fun setToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        // 뒤로가기 버튼 생성
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        // 기본 타이틀 제거
        actionBar!!.setDisplayShowTitleEnabled(false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            else -> return true
        }
        return super.onOptionsItemSelected(item)
    }

}