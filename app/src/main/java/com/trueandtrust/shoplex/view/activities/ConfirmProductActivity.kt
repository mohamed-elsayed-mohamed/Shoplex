package com.trueandtrust.shoplex.view.activities

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.databinding.ActivityConfirmProductBinding
import com.trueandtrust.shoplex.model.DBModel
import com.trueandtrust.shoplex.model.enumurations.Premium
import com.trueandtrust.shoplex.model.interfaces.INotifyMVP
import com.trueandtrust.shoplex.model.pojo.Product
import com.trueandtrust.shoplex.model.pojo.Properties

class ConfirmProductActivity : AppCompatActivity(), INotifyMVP {
    private lateinit var binding: ActivityConfirmProductBinding
    private lateinit var product: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        product = this.intent.getParcelableExtra(getString(R.string.PRODUCT_KEY))!!

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

        val property1 = Properties("Size", arrayListOf("20", "30", "40", "50"))
        val property2 = Properties("Color", arrayListOf("Red", "Green", "Blue"))

        product.properties = arrayListOf(property1, property2)

        binding.btnConfirm.setOnClickListener {
            val dbModel = DBModel(this)
            dbModel.addProduct(product, applicationContext)
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