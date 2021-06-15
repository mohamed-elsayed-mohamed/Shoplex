package com.trueandtrust.shoplex.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.databinding.FragmentProductsBinding
import com.trueandtrust.shoplex.model.adapter.ProductsAdapter
import com.trueandtrust.shoplex.view.activities.AddProductActivity
import com.trueandtrust.shoplex.viewmodel.ProductsVM

class ProductsFragment : Fragment() {
    private lateinit var binding: FragmentProductsBinding
    private lateinit var productsVM: ProductsVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductsBinding.inflate(inflater, container, false)
        productsVM = ViewModelProvider(requireActivity()).get(ProductsVM::class.java)

        binding.rvProducts.layoutManager = GridLayoutManager(this.context, getGridColumnsCount())

        if(productsVM.products.value == null)
            productsVM.getAllProducts()

        requireActivity().title = getString(R.string.products)

        productsVM.products.observe(this.viewLifecycleOwner) { products ->
            binding.rvProducts.adapter = ProductsAdapter(products)
        }

        binding.fabAddProduct.setOnClickListener {
            startActivity(Intent(this.context, AddProductActivity::class.java))
        }

        return binding.root
    }

    private fun getGridColumnsCount(): Int {
        val displayMetrics = requireContext().resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density
        val scalingFactor = 200 // You can vary the value held by the scalingFactor
        val columnCount = (dpWidth / scalingFactor).toInt()
        return if (columnCount >= 2) columnCount else 2 // if column no. is less than 2, we still display 2 columns
    }
}