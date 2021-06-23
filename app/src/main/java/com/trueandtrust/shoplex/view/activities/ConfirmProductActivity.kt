package com.trueandtrust.shoplex.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.databinding.ActivityConfirmProductBinding
import com.trueandtrust.shoplex.model.enumurations.Plan
import com.trueandtrust.shoplex.model.extra.ArchLifecycleApp
import com.trueandtrust.shoplex.model.firebase.ProductsDBModel
import com.trueandtrust.shoplex.model.interfaces.PaymentListener
import com.trueandtrust.shoplex.model.interfaces.ProductsListener
import com.trueandtrust.shoplex.model.pojo.Premium
import com.trueandtrust.shoplex.model.pojo.Product
import com.trueandtrust.shoplex.room.viewModel.ProductViewModel
import com.trueandtrust.shoplex.viewmodel.PaymentMethodFactory
import com.trueandtrust.shoplex.viewmodel.PaymentMethodVM
import kotlinx.android.synthetic.main.activity_confirm_product.*

class ConfirmProductActivity : AppCompatActivity(), ProductsListener, PaymentListener {
    private lateinit var binding: ActivityConfirmProductBinding
    private lateinit var product: Product
    private var isUpdate: Boolean = false
    private lateinit var productViewModel: ProductViewModel
    private var premiumPlan: Plan? = null

    private lateinit var paymentMethodVM: PaymentMethodVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        product = this.intent.getParcelableExtra(getString(R.string.PRODUCT_KEY))!!
        isUpdate = intent.getBooleanExtra(getString(R.string.update_product), false)
        productViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)

        paymentMethodVM =
            ViewModelProvider(this, PaymentMethodFactory(this, this)).get(
                PaymentMethodVM::class.java
            )
        showAll()

        binding.btnBuyBasic.setOnClickListener {
            product.premium = Premium(Plan.Bronze, 15 + (product.premium?.premiumDays ?: 0))
            premiumPlan = Plan.Bronze
            binding.cardStandard.visibility = View.INVISIBLE
            binding.cardPremium.visibility = View.INVISIBLE
        }

        binding.btnBuyStandard.setOnClickListener {
            product.premium = Premium(Plan.Silver, 31 + (product.premium?.premiumDays ?: 0))
            premiumPlan = Plan.Silver
            binding.cardBasic.visibility = View.INVISIBLE
            binding.cardPremium.visibility = View.INVISIBLE
        }

        binding.btnBuyPremium.setOnClickListener {
            product.premium = Premium(Plan.Gold, 91 + (product.premium?.premiumDays ?: 0))
            premiumPlan = Plan.Gold
            binding.cardBasic.visibility = View.INVISIBLE
            binding.cardStandard.visibility = View.INVISIBLE
        }

        binding.btnConfirm.setOnClickListener {
            if(ArchLifecycleApp.isInternetConnected) {
                var premiumPrice = 0
                if (product.premium != null && premiumPlan != null) {
                    premiumPrice = when (premiumPlan) {
                        Plan.Bronze -> 20
                        Plan.Silver -> 30
                        Plan.Gold -> 75
                        else -> 0
                    }
                }

                val price = calcTax(product.newPrice * product.quantity) + premiumPrice
                btnConfirm.isEnabled = false
                if (!isUpdate) {
                    paymentMethodVM.pay(price)
                } else if (premiumPrice > 0) {
                    paymentMethodVM.pay(premiumPrice.toFloat())
                } else {
                    addUpdateProduct()
                }
            } else {
                Snackbar.make(binding.root, getString(R.string.NoInternetConnection), Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun calcTax(price: Float): Float {
        return when {
            price <= 50 -> price * 0.1F
            price <= 100 -> price * 0.085F
            price <= 500 -> price * 0.075F
            price <= 1000 -> price * 0.070F
            price <= 3000 -> price * 0.060F
            price <= 5000 -> price * 0.050F
            price <= 10000 -> price * 0.040F
            price <= 50000 -> price * 0.035F
            else -> price * 0.030F
        }
    }

    private fun showAll() {
        binding.product = product
        //   binding.tvProductName.text = product.name
        binding.imgSlideConfirm.setImageList(product.getImageSlides())
        binding.imgProductConfirm.setImageURI(product.imagesListURI[0])
    }

    private fun addUpdateProduct() {
        val dbModel = ProductsDBModel(product, this, isUpdate, this)
        dbModel.addUpdateProduct()

        startActivity(
            Intent(
                this,
                HomeActivity::class.java
            ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        )
        finish()
    }

    override fun onProductAdded() {
        Snackbar.make(binding.root, "Product Added Successfully!", Snackbar.LENGTH_LONG).show()

    }


    override fun onPaymentComplete() {
        addUpdateProduct()
    }

    override fun onPaymentFailedToLoad() {
        Snackbar.make(binding.root, "Failed to load payment method", Snackbar.LENGTH_LONG).show()

    }

    override fun onMinimumPrice(price: Float) {
        Snackbar.make(binding.root, "You are going to pay $price L.E. for your product but unfortunately minimum charge is 10 L.E.", Snackbar.LENGTH_LONG).show()
        btnConfirm.isEnabled = true
    }
}