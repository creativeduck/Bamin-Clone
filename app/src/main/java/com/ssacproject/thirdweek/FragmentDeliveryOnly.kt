package com.ssacproject.thirdweek

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssacproject.thirdweek.activity.SpecificActivity
import com.ssacproject.thirdweek.customadapter.SelectMenuFoodItemAdapter
import com.ssacproject.thirdweek.databinding.GridDeliveryOnlyBinding

class FragmentDeliveryOnly(val minimum_price: String, val payType: String?,
                        val deliveryTime: String, val deliveryTip: String) : Fragment() {
    private var binding: GridDeliveryOnlyBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = GridDeliveryOnlyBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.minimumPrice.text = minimum_price
        if (payType == null) {
            binding!!.payType.visibility = View.GONE
            binding!!.howtopay.visibility = View.GONE
        } else {
            binding!!.payType.text = payType
        }
        binding!!.deliveryTime.text = deliveryTime
        binding!!.deliveryTip.text = deliveryTip
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}