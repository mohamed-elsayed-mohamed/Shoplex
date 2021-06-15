package com.trueandtrust.shoplex.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.trueandtrust.shoplex.databinding.FragmentReviewBinding
import com.trueandtrust.shoplex.model.adapter.ReviewsAdapter
import com.trueandtrust.shoplex.viewmodel.ProductsVM

class ReviewFragment(val productId: String) : Fragment() {
    lateinit var binding : FragmentReviewBinding
    private lateinit var reviewAdapter: ReviewsAdapter
    private lateinit var productsVm :ProductsVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentReviewBinding.inflate(inflater,container,false)
        this.productsVm = ProductsVM()

        productsVm.getReviewByProductId(productId)
        productsVm.reviews.observe(viewLifecycleOwner, Observer{ reviews ->
            reviewAdapter = ReviewsAdapter(reviews)
            binding.rvReview.adapter = reviewAdapter

        })

        productsVm.reviewStatistics.observe(viewLifecycleOwner, {
            binding.reviewStat = it
            if(it.total != 0) {
                binding.fiveStars.progress = ((it.fiveStars.toFloat() / it.total) * 100).toInt()
                binding.fourStars.progress = ((it.fourStars.toFloat() / it.total) * 100).toInt()
                binding.threeStars.progress = ((it.threeStars.toFloat() / it.total) * 100).toInt()
                binding.twoStars.progress = ((it.twoStars.toFloat() / it.total) * 100).toInt()
                binding.oneStar.progress = ((it.oneStar.toFloat() / it.total) * 100).toInt()
            }
        })
      return binding.root
    }
}