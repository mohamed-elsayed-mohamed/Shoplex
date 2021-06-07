package com.trueandtrust.shoplex.view.activities

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
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
import com.trueandtrust.shoplex.model.interfaces.INotifyMVP
import com.trueandtrust.shoplex.model.pojo.Premium
import com.trueandtrust.shoplex.model.pojo.Product
import com.trueandtrust.shoplex.room.viewModel.ProductViewModel
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

class ConfirmProductActivity : AppCompatActivity(), INotifyMVP {
    private lateinit var binding: ActivityConfirmProductBinding
    private lateinit var product: Product
    private var isUpdate: Boolean = false
    private lateinit var productViewModel: ProductViewModel

    private lateinit var paymentSheet: PaymentSheet

    private lateinit var customerId: String
    private lateinit var ephemeralKeySecret: String
    private lateinit var paymentIntentClientSecret: String

    private lateinit var buyButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        product = this.intent.getParcelableExtra(getString(R.string.PRODUCT_KEY))!!
        isUpdate = intent.getBooleanExtra(getString(R.string.update_product), false)
        productViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)

        showAll()

        binding.btnBuyBasic.setOnClickListener {
            product.premium = Premium(Plan.Bronze, 14)
            binding.cardStandard.visibility = View.INVISIBLE
            binding.cardPremium.visibility = View.INVISIBLE
        }

        binding.btnBuyStandard.setOnClickListener {
            product.premium = Premium(Plan.Silver, 30)
            binding.cardBasic.visibility = View.INVISIBLE
            binding.cardPremium.visibility = View.INVISIBLE
        }

        binding.btnBuyPremium.setOnClickListener {
            product.premium = Premium(Plan.Gold, 90)
            binding.cardBasic.visibility = View.INVISIBLE
            binding.cardStandard.visibility = View.INVISIBLE
        }

        /*
        binding.btnConfirm.setOnClickListener {
            val dbModel = ProductsDBModel(product, this, isUpdate)
            product.date = Timestamp.now().toDate()
            dbModel.addProduct()
            productViewModel.addProduct(product)
            startActivity(
                Intent(
                    this,
                    HomeActivity::class.java
                ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            )
            finish()
        }
        */

        /*
        PaymentConfiguration.init(
            applicationContext,
            "pk_test_51IzX9KFY0dskT72W2vHiMNJU0OGs9DukriXP1pfarCuYGkGPvZ8TaMRxxOK2W3WfQa1zO7JEOpiSqRya9BIn6okK00AZ4bRvHz"
        )
        */
        buyButton = binding.btnConfirm

        buyButton.isEnabled = false

        PaymentConfiguration.init(this, STRIPE_PUBLISHABLE_KEY)

        paymentSheet = PaymentSheet(this) { result ->
            onPaymentSheetResult(result)
        }

        buyButton.setOnClickListener {
            presentPaymentSheet()
        }

        fetchInitData()
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

    override fun onUploadSuccess() {
        super.onUploadSuccess()
    }

    override fun onUploadFailed() {
        super.onUploadFailed()
    }


    // Payment
    private fun fetchInitData() {
        val amount: Double = 1000.0

        val payMap: MutableMap<String, Any> = HashMap()
        val itemMap: MutableMap<String, Any> = HashMap()
        val itemList: MutableList<Map<String, Any>> = ArrayList()
        payMap["currency"] = "egp"

        // itemMap["id"] = "photo_subscription"
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
                        Toast.makeText(applicationContext, "Failed", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    if (!response.isSuccessful) {
                        runOnUiThread {
                            Toast.makeText(applicationContext, "Not Success", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        runOnUiThread {
                            Toast.makeText(applicationContext, "Success", Toast.LENGTH_SHORT).show()
                        }
                        val responseData = response.body?.string()
                        val responseJson =
                            responseData?.let { JSONObject(it) } ?: JSONObject()


                        //customerId = responseJson.getString("customer")
                        //ephemeralKeySecret = responseJson.getString("ephemeralKey")
                        paymentIntentClientSecret = responseJson.getString("clientSecret")

                        runOnUiThread {
                            buyButton.isEnabled = true
                        }
                    }
                }
            }
            )
    }

    private fun presentPaymentSheet() {
        paymentSheet.presentWithPaymentIntent(
            paymentIntentClientSecret
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
                    "Payment Failed. See logcat for details.",
                    Toast.LENGTH_LONG
                ).show()
                Log.e("App", "Got error: ${paymentSheetResult.error}")
            }
            is PaymentSheetResult.Completed -> {

                val dbModel = ProductsDBModel(product, this, isUpdate)
                //product.date =  FieldValue.serverTimestamp()
                dbModel.addProduct()
                productViewModel.addProduct(product)
                startActivity(
                    Intent(
                        this,
                        HomeActivity::class.java
                    ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                )
                finish()
                Toast.makeText(
                    this,
                    "Payment Complete",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private companion object {
        private const val BACKEND_URL = "https://evening-sands-34009.herokuapp.com/"
        private const val STRIPE_PUBLISHABLE_KEY = "pk_test_51IzX9KFY0dskT72W2vHiMNJU0OGs9DukriXP1pfarCuYGkGPvZ8TaMRxxOK2W3WfQa1zO7JEOpiSqRya9BIn6okK00AZ4bRvHz"
    }

}