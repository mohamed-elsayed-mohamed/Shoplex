package com.trueandtrust.shoplex.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.model.adapter.ColorAdapter
import com.trueandtrust.shoplex.databinding.FragmentProductBinding

class ProductFragment : Fragment() {

    private lateinit var binding : FragmentProductBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var colorAdapter: ColorAdapter


    val imageList = ArrayList<SlideModel>() // Create image list
    val colors = arrayOf(R.drawable.teal, R.drawable.green, R.drawable.teal)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductBinding.inflate(inflater, container, false)

        //imgSlider
        imageList.add(SlideModel("https://bit.ly/2YoJ77H"))
        imageList.add(SlideModel("https://bit.ly/2BteuF2"))
        imageList.add(SlideModel("https://bit.ly/3fLJf72"))

        binding.imgSliderDetails.setImageList(imageList, ScaleTypes.CENTER_CROP)

        //RecyclerView
        linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = RecyclerView.HORIZONTAL
        binding.recyclerViewColor.layoutManager = linearLayoutManager

        colorAdapter = ColorAdapter(colors)
        binding.recyclerViewColor.adapter = colorAdapter


        return binding.root
    }

}