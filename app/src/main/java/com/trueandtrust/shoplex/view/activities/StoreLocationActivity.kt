package com.trueandtrust.shoplex.view.activities

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.databinding.ActivityStoreLocationBinding
import com.trueandtrust.shoplex.model.adapter.LocationAdapter
import com.trueandtrust.shoplex.model.enumurations.LocationAction
import com.trueandtrust.shoplex.model.extra.ArchLifecycleApp
import com.trueandtrust.shoplex.model.extra.StoreInfo
import com.trueandtrust.shoplex.model.pojo.Location
import com.trueandtrust.shoplex.model.pojo.PendingLocation
import com.trueandtrust.shoplex.viewmodel.StoreVM

class StoreLocationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStoreLocationBinding

    private lateinit var storeVM: StoreVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoreLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        storeVM = ViewModelProvider(this).get(StoreVM::class.java)

        if (storeVM.storeAddresses.value == null)
            storeVM.getAllAddresses()

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.title = getString(R.string.StoreLocation)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        storeVM.storeAddresses.observe(this, { locations ->
            binding.rcLocation.adapter = LocationAdapter(locations)
        })

        binding.fabAddLocation.setOnClickListener {
            if(ArchLifecycleApp.isInternetConnected) {
                startActivityForResult(
                    Intent(this, MapsActivity::class.java)
                        .apply {
                            putExtra(MapsActivity.LOCATION_ACTION, LocationAction.Add.name)
                        }, MapsActivity.MAPS_CODE
                )
            } else {
                Snackbar.make(binding.root, getString(R.string.NoInternetConnection), Snackbar.LENGTH_LONG).show()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            finish()

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MapsActivity.MAPS_CODE) {
            if (resultCode == RESULT_OK) {
                val location: Parcelable? = data?.getParcelableExtra(MapsActivity.LOCATION)
                val address: String? = data?.getStringExtra(MapsActivity.ADDRESS)

                if (location != null && address != null) {
                    val loc = location as Location
                    val pendingLocation = PendingLocation(
                        StoreInfo.storeID.toString(),
                        StoreInfo.name,
                        address,
                        loc
                    )
                    storeVM.addStoreLocation(pendingLocation)
                    // Add after accept
                    // StoreInfo.addStoreLocation(this, pendingLocation)


                    //storeVM.storeAddresses.value!!.add(PendingLocation(address= address, location = location))
                } else {
                    Snackbar.make(binding.root, "Failed please select valid address", Snackbar.LENGTH_LONG).show()

                }
            }
        }
    }
}