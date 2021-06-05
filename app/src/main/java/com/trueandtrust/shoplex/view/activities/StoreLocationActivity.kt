package com.trueandtrust.shoplex.view.activities

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.ktx.toObject
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.databinding.ActivityStoreLocationBinding
import com.trueandtrust.shoplex.model.adapter.LocationAdapter
import com.trueandtrust.shoplex.model.extra.FirebaseReferences
import com.trueandtrust.shoplex.model.extra.StoreInfo
import com.trueandtrust.shoplex.model.pojo.Location
import com.trueandtrust.shoplex.model.pojo.Store

class StoreLocationActivity : AppCompatActivity() {
    lateinit var binding: ActivityStoreLocationBinding
    lateinit var toolbar: Toolbar
    private val LOCATION_CODE = 203
    val LOCATION_STORE = "LOCATION STORE"
    private var locarions = ArrayList<String>()

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
        if (getSupportActionBar() != null) {
            getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
            getSupportActionBar()?.setDisplayShowHomeEnabled(true);
        }

        FirebaseReferences.storeRef.whereEqualTo("storeID", StoreInfo.storeID).get()
            .addOnSuccessListener {
                for (item in it) {
                    if (item.exists()) {
                        var seller = item.toObject<Store>()
                        for (loc in seller.addresses) {
                            locarions.add(loc)
                        }
                    }
                }
                var locAdapter = LocationAdapter(locarions)
                binding.rcLocation.adapter = locAdapter
            }

        binding.fabAddLocation.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            intent.putExtra(LOCATION_STORE, "Location Store")
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
        if (requestCode == LOCATION_CODE) {
            if (resultCode == RESULT_OK) {
                val location: Parcelable? = data?.getParcelableExtra("Loc")
                if (location != null) {
                    val loc = location as LatLng
                    val location = Location(
                        StoreInfo.storeID.toString(),
                        StoreInfo.name,
                        Location.getAddress(loc, this),
                        loc
                    )
                    FirebaseReferences.locationRef.add(location).addOnSuccessListener {
                        Toast.makeText(this, "susses", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}