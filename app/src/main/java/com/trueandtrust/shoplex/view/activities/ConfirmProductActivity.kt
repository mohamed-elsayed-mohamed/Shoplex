package com.trueandtrust.shoplex.view.activities

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.databinding.ActivityConfirmProductBinding
import com.trueandtrust.shoplex.model.enumurations.Plan
import com.trueandtrust.shoplex.model.firebase.ProductsDBModel
import com.trueandtrust.shoplex.model.interfaces.ProductsListener
import com.trueandtrust.shoplex.model.pojo.Premium
import com.trueandtrust.shoplex.model.pojo.Product
import com.trueandtrust.shoplex.room.viewModel.ProductViewModel
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

class ConfirmProductActivity : AppCompatActivity(), ProductsListener {
    private lateinit var binding: ActivityConfirmProductBinding
    private lateinit var product: Product
    private var isUpdate: Boolean = false
    private lateinit var productViewModel: ProductViewModel

    private lateinit var paymentSheet: PaymentSheet
    private lateinit var paymentIntentClientSecret: String
    private var premiumPlan: Plan? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        product = this.intent.getParcelableExtra(getString(R.string.PRODUCT_KEY))!!
        isUpdate = intent.getBooleanExtra(getString(R.string.update_product), false)
        productViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)

        showAll()

        binding.btnBuyBasic.setOnClickListener {
            product.premium = Premium(Plan.Bronze, 14 + (product.premium?.premiumDays?:0))
            premiumPlan = Plan.Bronze
            binding.cardStandard.visibility = View.INVISIBLE
            binding.cardPremium.visibility = View.INVISIBLE
        }

        binding.btnBuyStandard.setOnClickListener {
            product.premium = Premium(Plan.Silver, 30 + (product.premium?.premiumDays?:0))
            premiumPlan = Plan.Silver
            binding.cardBasic.visibility = View.INVISIBLE
            binding.cardPremium.visibility = View.INVISIBLE
        }

        binding.btnBuyPremium.setOnClickListener {
            product.premium = Premium(Plan.Gold, 90 + (product.premium?.premiumDays?:0))
            premiumPlan = Plan.Gold
            binding.cardBasic.visibility = View.INVISIBLE
            binding.cardStandard.visibility = View.INVISIBLE
        }

        //binding.btnConfirm.isEnabled = false

        PaymentConfiguration.init(this, STRIPE_PUBLISHABLE_KEY)

        paymentSheet = PaymentSheet(this) { result ->
            onPaymentSheetResult(result)
        }

        binding.btnConfirm.setOnClickListener {
            var premiumPrice = 0
            if(product.premium != null && premiumPlan != null){
                premiumPrice = when(premiumPlan){
                    Plan.Bronze -> 20
                    Plan.Silver -> 30
                    Plan.Gold -> 75
                    else -> 0
                }
            }

            val price = ((product.newPrice * product.quantity) * 0.1) + premiumPrice
            if(!isUpdate) {
                if(price > 10) {
                    fetchInitData(price)
                }else{
                    Toast.makeText(this, "Minimum Charge 100 L.E", Toast.LENGTH_SHORT).show()
                }
            }else if(premiumPrice > 0){
                fetchInitData(premiumPrice.toDouble())
            }else{
                addUpdateProduct()
            }
        }
    }

    private fun presentPaymentSheet() {
        paymentSheet.presentWithPaymentIntent(
            paymentIntentClientSecret
        )
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

        binding.tvNewPriceConfirm.text = product.newPrice.toString() + "EGP"
        binding.tvDiscountConfirm.text = product.discount.toString() + " %"

        if(product.discount == 0) {
            binding.tvSalesOffer.visibility = View.GONE
        }else {
            binding.tvSalesOffer.text = "${product.discount} % ${getString(R.string.sales_Off)}"
        }
    }

    // Payment
    private fun fetchInitData(price: Double) {
        val amount: Double = price * 100

        val payMap: MutableMap<String, Any> = HashMap()
        val itemMap: MutableMap<String, Any> = HashMap()
        val itemList: MutableList<Map<String, Any>> = ArrayList()
        payMap["currency"] = "egp"
        itemMap["amount"] = amount
        itemList.add(itemMap)
        payMap["items"] = itemList

        val mediaType = "application/json; charset=utf-8".toMediaType()
        val json = Gson().toJson(payMap)
        val body = json.toRequestBody(mediaType)
        val request = Request.Builder()
            .url(BACKEND_URL + "checkout")
            .post(body)
            .build()

        OkHttpClient().newCall(request)
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    runOnUiThread {
                        Toast.makeText(
                            applicationContext,
                            "Failed to load payment method",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    if (!response.isSuccessful) {
                        runOnUiThread {
                            Toast.makeText(
                                applicationContext,
                                "Failed to load payment method",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        runOnUiThread {
                            Toast.makeText(
                                applicationContext,
                                "Payment method ready",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        val responseData = response.body?.string()
                        val responseJson =
                            responseData?.let { JSONObject(it) } ?: JSONObject()

                        paymentIntentClientSecret = responseJson.getString("clientSecret")

                        runOnUiThread {
                            // binding.btnConfirm.isEnabled = true
                            presentPaymentSheet()
                        }
                    }
                }
            }
            )
    }

    private fun onPaymentSheetResult(
        paymentSheetResult: PaymentSheetResult
    ) {
        when(paymentSheetResult) {
            is PaymentSheetResult.Canceled -> {
                Toast.makeText(
                    this,
                    "Payment Canceled",
                    Toast.LENGTH_LONG
                ).show()
            }
            is PaymentSheetResult.Failed -> {
                Toast.makeText(
                    this,
                    "Payment Failed.",
                    Toast.LENGTH_LONG
                ).show()
            }
            is PaymentSheetResult.Completed -> {

                addUpdateProduct()

                Toast.makeText(
                    this,
                    "Payment Complete",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun addUpdateProduct() {
        val dbModel = ProductsDBModel(product, this, isUpdate, this)
        dbModel.addUpdateProduct()
        /*
        if (isUpdate)
            productViewModel.updateProduct(product)
        else
            productViewModel.addProduct(product)
        */

        startActivity(
            Intent(
                this,
                HomeActivity::class.java
            ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        )
        finish()
    }

    override fun onProductAdded() {
        Toast.makeText(
            this,
            "Product Added Successfully!",
            Toast.LENGTH_LONG
        ).show()
    }

    private companion object {
        private const val BACKEND_URL = "https://evening-sands-34009.herokuapp.com/"
        private const val STRIPE_PUBLISHABLE_KEY = "pk_test_51IzX9KFY0dskT72W2vHiMNJU0OGs9DukriXP1pfarCuYGkGPvZ8TaMRxxOK2W3WfQa1zO7JEOpiSqRya9BIn6okK00AZ4bRvHz"
    }

}