package com.ssacproject.thirdweek

import android.content.Context
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import com.ssacproject.thirdweek.customadapter.ItemSpecificMenuAdapter
import com.ssacproject.thirdweek.customadapter.SpecificMenu
import com.ssacproject.thirdweek.databinding.ExpandChildBinding
import com.ssacproject.thirdweek.databinding.ExpandMenuTitleBinding
import kotlinx.parcelize.Parcelize

class ExpandableListAdapter(
    private val context: Context,
    private val parents: MutableList<MenuTitle>,
    private val childList: MutableList<MutableList<MenuSpecific>>
    ) : BaseExpandableListAdapter() {
    var listener: OnItemClickListener? = null

    override fun getGroupCount(): Int {
        return parents.size
    }

    override fun getChildrenCount(parent: Int): Int {
        return childList[parent].size
    }

    override fun getGroup(parent: Int): Any {
        return parents[parent]
    }

    override fun getChild(parent: Int, child: Int): Any {
        return childList[parent][child]
    }

    override fun getGroupId(parent: Int): Long {
        return parent.toLong()
    }

    override fun getChildId(parent: Int, child: Int): Long {
        return child.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(parent: Int, isExpanded: Boolean, convertView: View?, parentView: ViewGroup?): View {
        val binding = ExpandMenuTitleBinding.inflate(LayoutInflater.from(context), parentView, false)
        binding.expandTitle.text = parents[parent].title
        setArrow(binding, isExpanded)

        return binding.root
    }

    override fun getChildView(parent: Int, child: Int, isLastChild: Boolean, convertView: View?, parentView: ViewGroup?): View {
        val binding = ExpandChildBinding.inflate(LayoutInflater.from(context), parentView, false)
        val item = getChild(parent, child) as MenuSpecific



        binding.expandChildTitle.text = item.title
        binding.expandChildDetail.text = item.detail
        val price = "${item.price}원"
        binding.expandChildPrice.text = price
        if(item.img == null) {
            binding.expandChildImage.visibility = View.GONE
        } else {
            binding.expandChildImage.setImageResource(item.img)
        }

        return binding.root
    }

    override fun isChildSelectable(p0: Int, p1: Int): Boolean {
        return true
    }
    fun setArrow(binding: ExpandMenuTitleBinding, isExpanded: Boolean) {
        if(isExpanded) {
            binding.explainIcon.setImageResource(R.drawable.ic_close_explain)
        } else {
            binding.explainIcon.setImageResource(R.drawable.ic_show_explain)
        }
    }
}



data class MenuTitle(val title: String, var index: Int)
@Parcelize
data class MenuSpecific(val title: String, val detail: String?, val price: Int, val img: Int?) :
    Parcelable