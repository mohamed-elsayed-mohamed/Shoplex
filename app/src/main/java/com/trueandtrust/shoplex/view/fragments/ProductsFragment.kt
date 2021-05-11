package com.trueandtrust.shoplex.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.databinding.FragmentProductsBinding
import com.trueandtrust.shoplex.model.adapter.ProductAdapter


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class ProductsFragment : Fragment() {
    lateinit var binding : FragmentProductsBinding
    lateinit var gridView: GridView
    private var productInfo = arrayOf("Fashion", "shirt", "Silva", "Andre", "Bruno",
        "Fern")
    private var productImages = intArrayOf(R.drawable.product, R.drawable.product, R.drawable.product,
        R.drawable.product,
        R.drawable.product, R.drawable.product)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentProductsBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = getString(R.string.products)
        gridView=binding.gridView

            val productAdapter = context?.let { ProductAdapter(it, productInfo, productImages) }
            gridView.adapter = productAdapter
            gridView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                Toast.makeText(
                    context, "You CLicked " + productInfo[+position],
                    Toast.LENGTH_SHORT
                ).show()
            }
        //gridView.setNumColumns(if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 2 else 4)
        gridView.numColumns = context?.let { getGridColumnsCount(it) }!!
        return binding.root
    }
    fun getGridColumnsCount(context: Context): Int {
        val displayMetrics = context.resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density
        val scalingFactor = 200 // You can vary the value held by the scalingFactor
        val columnCount = (dpWidth / scalingFactor).toInt()
        return if (columnCount >= 2) columnCount else 2 // if column no. is less than 2, we still display 2 columns

    }

}