package com.trueandtrust.shoplex.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.databinding.FragmentProductsBinding
import com.trueandtrust.shoplex.model.adapter.ProductAdapter
import com.trueandtrust.shoplex.model.extra.FirebaseReferences
import com.trueandtrust.shoplex.model.extra.StoreInfo
import com.trueandtrust.shoplex.model.pojo.Product
import com.trueandtrust.shoplex.view.activities.AddProductActivity
import com.trueandtrust.shoplex.viewmodel.ProductsVM


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class ProductsFragment : Fragment() {
    private lateinit var binding : FragmentProductsBinding
    private lateinit var rvProducts: RecyclerView
    private lateinit var productsVM: ProductsVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentProductsBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = getString(R.string.products)
        rvProducts = binding.rvProducts
        rvProducts.layoutManager = GridLayoutManager(this.context, getGridColumnsCount())
        this.productsVM = ProductsVM()
        productsVM.getAllProducts(StoreInfo.storeID!!)

        productsVM.products.observe(this.viewLifecycleOwner){ result ->
            val productAdapter = context?.let { ProductAdapter(result) }
            rvProducts.adapter = productAdapter
        }

        binding.fabAddProduct.setOnClickListener {
            startActivity(Intent(this.context, AddProductActivity::class.java))
        }
        return binding.root
    }

    fun getGridColumnsCount(): Int {
        val displayMetrics = requireContext().resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density
        val scalingFactor = 200 // You can vary the value held by the scalingFactor
        val columnCount = (dpWidth / scalingFactor).toInt()
        return if (columnCount >= 2) columnCount else 2 // if column no. is less than 2, we still display 2 columns
    }
}