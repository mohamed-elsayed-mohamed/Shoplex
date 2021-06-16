package com.trueandtrust.shoplex.model.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FieldValue
import com.trueandtrust.shoplex.databinding.LocationItemRowBinding
import com.trueandtrust.shoplex.model.extra.FirebaseReferences
import com.trueandtrust.shoplex.model.extra.StoreInfo
import com.trueandtrust.shoplex.viewmodel.StoreVM

class LocationAdapter(val locationsList: ArrayList<String>) :
    RecyclerView.Adapter<LocationAdapter.LocationsViewHolder>() {

    private lateinit var storeVM: StoreVM

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationsViewHolder {
        storeVM = ViewModelProvider(parent.context as AppCompatActivity).get(StoreVM::class.java)
        return LocationsViewHolder(
            LocationItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: LocationsViewHolder, position: Int) =
        holder.bind(locationsList[position])

    override fun getItemCount() = locationsList.size

    inner class LocationsViewHolder(val binding: LocationItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(address: String) {
            binding.tvLocation.text = address

            binding.btnColse.setOnClickListener {
                if(locationsList.count() > 1) {
                    //storeVM.removeLocationAddress(address, )
                    storeVM.isLocationRemoved.observe(it.context as AppCompatActivity, { isRemoved ->
                        if(isRemoved == true){
                            locationsList.remove(address)
                            notifyDataSetChanged()
                        }
                    })

                }else{
                    Toast.makeText(binding.root.context, "You must have at least one location", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}