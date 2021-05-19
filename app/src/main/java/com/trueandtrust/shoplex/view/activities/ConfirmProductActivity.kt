package com.trueandtrust.shoplex.view.activities

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Timestamp
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.databinding.ActivityConfirmProductBinding
import com.trueandtrust.shoplex.model.firebase.ProductsDBModel
import com.trueandtrust.shoplex.model.enumurations.Premium
import com.trueandtrust.shoplex.model.interfaces.INotifyMVP
import com.trueandtrust.shoplex.model.pojo.Product

class ConfirmProductActivity : AppCompatActivity(), INotifyMVP {
    private lateinit var binding: ActivityConfirmProductBinding
    private lateinit var product: Product
    private var isUpdate: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        product = this.intent.getParcelableExtra(getString(R.string.PRODUCT_KEY))!!
        if(intent.hasExtra(getString(R.string.update_product)))
            isUpdate = true

        showAll()

        binding.btnBuyBasic.setOnClickListener {
            product.premium = Premium.BASIC
            product.premiumDays = 14
            binding.cardStandard.visibility = View.INVISIBLE
            binding.cardPremium.visibility = View.INVISIBLE
        }
        binding.btnBuyStandard.setOnClickListener {
            product.premium = Premium.STANDARD
            product.premiumDays = 30
            binding.cardBasic.visibility = View.INVISIBLE
            binding.cardPremium.visibility = View.INVISIBLE
        }

        binding.btnBuyPremium.setOnClickListener {
            product.premium = Premium.PREMIUM
            product.premiumDays = 90
            binding.cardBasic.visibility = View.INVISIBLE
            binding.cardStandard.visibility = View.INVISIBLE
        }

        binding.btnConfirm.setOnClickListener {
            val dbModel = ProductsDBModel(product, this, isUpdate)
            product.date = Timestamp.now().toDate()
            dbModel.addProduct()
            startActivity(
                Intent(
                    this,
                    HomeActivity::class.java
                ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            )
            finish()
        }
    }

    private fun showAll(){
        binding.tvProductName.text = product.name
        binding.imgSlideConfirm.setImageList(product.getImageSlides())
        binding.imgProductConfirm.setImageURI(product.imagesListURI[0])
        if(product.price == product.newPrice) {
            binding.tvOldPriceConfirm.visibility = View.GONE
            binding.tvDiscountConfirm.visibility = View.GONE
        }

        binding.tvOldPriceConfirm.apply {
            paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            text = product.price.toString()
        }

        binding.tvNewPriceConfirm.text = product.newPrice.toString()
        binding.tvDiscountConfirm.text = product.discount.toString() + " %"

        if(product.discount == 0) {
            binding.tvSalesOffer.visibility = View.GONE
        }else {
            binding.tvSalesOffer.text = "${product.discount} % ${getString(R.string.sales_Off)}"
        }
    }

    override fun onUploadSuccess() {
        super.onUploadSuccess()
    }

    override fun onUploadFailed() {
        super.onUploadFailed()
    }
}