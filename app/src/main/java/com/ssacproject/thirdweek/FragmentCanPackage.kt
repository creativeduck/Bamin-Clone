package com.ssacproject.thirdweek

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.ssacproject.thirdweek.databinding.GridCanPackageBinding
import net.daum.mf.map.api.MapView;

class FragmentCanPackage(context: Context, val address_main: String, val address_farfrom: String) : Fragment() {
    var binding: GridCanPackageBinding? = null
//    lateinit var mapView: MapView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = GridCanPackageBinding.inflate(inflater, container, false)
//        mapView = MapView(context)
//        binding!!.mapView.addView(mapView)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.addressMain.text = address_main
        binding!!.addressFarfrom.text = address_farfrom
    }
}