package com.trueandtrust.shoplex.model.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.trueandtrust.shoplex.databinding.ReveiwItemBinding
import com.trueandtrust.shoplex.model.pojo.Review

class ReviewsAdapter(val reviews: ArrayList<Review>) : RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        return ReviewViewHolder(
            ReveiwItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) =
        holder.bind(reviews[position])

    override fun getItemCount() = reviews.size

    inner class ReviewViewHolder(val binding: ReveiwItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(review: Review) {
            // Your custom view code here
            Glide.with(binding.root.context).load(review.image).into(binding.imgHead)
            binding.tvCustomerName.text = review.customerName
            binding.ratingBar.rating = review.rate
            binding.tvDate.text = review.date.toString()
            binding.tvComment.text = review.comment
        }
    }
}
