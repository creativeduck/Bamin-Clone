package com.ssacproject.thirdweek.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.ssacproject.thirdweek.FragmentHeart
import com.ssacproject.thirdweek.FragmentMenu
import com.ssacproject.thirdweek.R
import com.ssacproject.thirdweek.activity.MainActivity.Companion.heartHelper
import com.ssacproject.thirdweek.activity.MainActivity.Companion.helper
import com.ssacproject.thirdweek.customadapter.SelectFoodItem
import com.ssacproject.thirdweek.customadapter.ViewPagerFragmentAdapter
import com.ssacproject.thirdweek.databinding.ActivityHeartBinding
import com.ssacproject.thirdweek.food_fragment_real.*
import com.ssacproject.thirdweek.room.RoomHeart
import java.util.ArrayList

class HeartActivity : AppCompatActivity() {
    lateinit var binding: ActivityHeartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHeartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar(binding.heartToolbar)

        var tabTitles = listOf("배달/포장", "B마트","쇼핑라이브")
        var fragmentList = listOf<Fragment>(
            FragmentHeart(), FragmentDelivery(),
            FragmentBaminOne()
        )
        val adapter = ViewPagerFragmentAdapter(this)
        adapter.fragmentList = fragmentList
        binding.heartViewpager.let {
            it.adapter = adapter
            it.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }
        TabLayoutMediator(binding.heartTabLayout, binding.heartViewpager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            else -> return true
        }
        return super.onOptionsItemSelected(item)
    }
    fun setToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowTitleEnabled(false)
    }
}