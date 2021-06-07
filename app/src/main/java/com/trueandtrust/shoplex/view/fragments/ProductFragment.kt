package com.trueandtrust.shoplex.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.trueandtrust.shoplex.databinding.FragmentProductBinding
import com.trueandtrust.shoplex.model.adapter.PropertyAdapter
import com.trueandtrust.shoplex.model.pojo.Product
import com.trueandtrust.shoplex.viewmodel.ProductsVM

class ProductFragment(val product: Product) : Fragment() {

    private lateinit var binding: FragmentProductBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var propertiesAdapter: PropertyAdapter
    //private lateinit var productsVm: ProductsVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val imageList = ArrayList<SlideModel>()
        binding = FragmentProductBinding.inflate(inflater, container, false)
        //productsVm = ProductsVM()

        for (imgURL in product.images)
            imageList.add(SlideModel(imgURL))

        binding.imgSliderDetails.setImageList(imageList, ScaleTypes.CENTER_INSIDE)

        binding.tvDetailsName.text = product.name
        binding.tvDetailsReview.text = product.rate.toString()
        binding.tvDetailsDiscount.text = product.discount.toString()+"%"
        binding.tvDetailsOldPrice.text = product.price.toString()
        binding.tvDetailsNewPrice.text = product.newPrice.toString()
        binding.tvDetailsDescription.text = product.description
        binding.tvCatSubCat.text = "${product.category.toLowerCase().capitalize()}, ${product.subCategory.toLowerCase().capitalize()}"

        if (product.premium?.premiumDays != 0) {
            binding.tvPremiumDays.visibility = View.VISIBLE
            binding.tvPremiumDays.text = product.premium?.premiumDays.toString() + " Days"
        }

        //RecyclerView
        linearLayoutManager = LinearLayoutManager(context)
        binding.rcProperties.layoutManager = linearLayoutManager

        propertiesAdapter = PropertyAdapter(product.properties, true)
        binding.rcProperties.adapter = propertiesAdapter


        return binding.root
    }

}