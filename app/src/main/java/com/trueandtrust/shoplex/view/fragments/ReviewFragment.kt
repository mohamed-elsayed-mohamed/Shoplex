package com.trueandtrust.shoplex.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.trueandtrust.shoplex.databinding.FragmentReviewBinding
import com.trueandtrust.shoplex.model.adapter.ReviewsAdapter
import com.trueandtrust.shoplex.viewmodel.ProductsVM
import java.util.*


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
//        val review = ArrayList<Review>()
//        val attrs: AttributeSet? = null
//
//        review.add(Review("azhar",
//             "https://i.pinimg.com/236x/35/11/21/351121d0c57db7df186885dc077f7323.jpg",
//             "Which review is likely to influence someone with an intense pizza craving? A five-star rating and “good pizza” is not bad, but it doesn’t have the same impact. A review doesn’t have to be the length of War and Peace, but an honest, detailed, and specific recollection goes a long way to building credibility.",
//
//                Date(15), RatingBar(context,attrs,5)
//        ))
//        review.add(Review("azhar",
//            "https://i.pinimg.com/236x/35/11/21/351121d0c57db7df186885dc077f7323.jpg",
//            "Which review is likely to influence someone with an intense pizza craving? A five-star rating and “good pizza” is not bad, but it doesn’t have the same impact. A review doesn’t have to be the length of War and Peace, but an honest, detailed, and specific recollection goes a long way to building credibility.",
//
//            Date(15), RatingBar(context,attrs,5)
//        ))
//        review.add(Review("azhar",
//            "https://i.pinimg.com/236x/35/11/21/351121d0c57db7df186885dc077f7323.jpg",
//            "Which review is likely to influence someone with an intense pizza craving? A five-star rating and “good pizza” is not bad, but it doesn’t have the same impact. A review doesn’t have to be the length of War and Peace, but an honest, detailed, and specific recollection goes a long way to building credibility.",
//
//            Date(15), RatingBar(context,attrs,5)
//        ))
//        review.add(Review("azhar",
//            "https://i.pinimg.com/236x/35/11/21/351121d0c57db7df186885dc077f7323.jpg",
//            "Which review is likely to influence someone with an intense pizza craving? A five-star rating and “good pizza” is not bad, but it doesn’t have the same impact. A review doesn’t have to be the length of War and Peace, but an honest, detailed, and specific recollection goes a long way to building credibility.",
//
//            Date(15), RatingBar(context,attrs,5)
//        ))
//
//        reviewAdapter = ReviewAdapter(review)
//        binding.rvReview.adapter = reviewAdapter
        productsVm.getReviewByProductId(productId)
        productsVm.reviews.observe(viewLifecycleOwner, Observer{ reviews ->
            reviewAdapter = ReviewsAdapter(reviews)
            binding.rvReview.adapter = reviewAdapter

        })
      return binding.root

    }

}