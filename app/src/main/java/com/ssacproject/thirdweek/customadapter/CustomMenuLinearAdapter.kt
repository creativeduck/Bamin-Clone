package com.ssacproject.thirdweek.customadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssacproject.thirdweek.OnItemClickListener
import com.ssacproject.thirdweek.R
import com.ssacproject.thirdweek.databinding.CustomMenuLinearBinding

class CustomMenuLinearAdapter(val context: Context) : RecyclerView.Adapter<CustomMenuLinearAdapter.Holder>() {
    var menuLinearList = ArrayList<MenuLinear>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(CustomMenuLinearBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = menuLinearList[position]
        holder.setMenuLinear(item)
    }

    override fun getItemCount(): Int {
        return menuLinearList.size
    }



    inner class Holder(val binding: CustomMenuLinearBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.explainIcon.setOnClickListener {
                if (binding.customMenuLienarRecycler.visibility == View.GONE) {
                    binding.customMenuLienarRecycler.visibility = View.VISIBLE
                    binding.explainIcon.setImageResource(R.drawable.ic_close_explain)
                } else {
                    binding.customMenuLienarRecycler.visibility = View.GONE
                    binding.explainIcon.setImageResource(R.drawable.ic_show_explain)
                }
            }
        }

        fun setMenuLinear(item: MenuLinear) {
            binding.customMenuLinearTitle.text = item.title
            val layoutManager = LinearLayoutManager(context)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            binding.customMenuLienarRecycler.layoutManager = layoutManager
            val adapter = item.specificMenuAdapter
            adapter.specificMenuList = item.specificMenuList
            binding.customMenuLienarRecycler.adapter = adapter
        }
    }
}

data class MenuLinear(val title: String, val specificMenuList: ArrayList<SpecificMenu>,
                      val specificMenuAdapter: ItemSpecificMenuAdapter, var index: Int)