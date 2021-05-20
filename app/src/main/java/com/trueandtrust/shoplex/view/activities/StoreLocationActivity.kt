package com.trueandtrust.shoplex.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.google.android.gms.maps.model.LatLng
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.databinding.ActivityLastOrderBinding
import com.trueandtrust.shoplex.databinding.ActivityStoreLocationBinding
import com.trueandtrust.shoplex.model.extra.FirebaseReferences
import com.trueandtrust.shoplex.model.pojo.Store

class StoreLocationActivity : AppCompatActivity() {
    lateinit var binding : ActivityStoreLocationBinding
    lateinit var toolbar: Toolbar
    private var store : Store = Store()
    private val LOCATION_CODE = 203
    val LOCATION_STORE = "LOCATION STORE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoreLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = getString(R.string.StoreLocation)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)

        }
        if (getSupportActionBar() != null){
            getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
            getSupportActionBar()?.setDisplayShowHomeEnabled(true);
        }

        binding.fabAddLocation.setOnClickListener{
            val intent = Intent(this, MapsActivity::class.java)
            intent.putExtra(LOCATION_STORE,"Location Store")
            startActivityForResult(intent, LOCATION_CODE)
        }

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            finish() // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == LOCATION_CODE){
            if(resultCode == RESULT_OK){
                val location: Parcelable? = data?.getParcelableExtra("AddLoc")

                if(location != null) {
                    val address = location as LatLng
                    FirebaseReferences.locationRef.add(address)
                }
            }
        }
    }
}