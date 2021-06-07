package com.trueandtrust.shoplex.model.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.FirebaseStorage
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.databinding.LocationItemRowBinding
import com.trueandtrust.shoplex.model.extra.FirebaseReferences
import com.trueandtrust.shoplex.model.extra.StoreInfo
import com.trueandtrust.shoplex.model.pojo.Store

class LocationAdapter(val locationsList: ArrayList<String>) :
    RecyclerView.Adapter<LocationAdapter.LocationsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationsViewHolder {
        return LocationsViewHolder(
            LocationItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: LocationsViewHolder, position: Int) =
        holder.bind(locationsList[position])

    override fun getItemCount() = locationsList.size

    inner class LocationsViewHolder(val binding: LocationItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.tvLocation.text = item
            binding.btnColse.setOnClickListener {
                if(locationsList.count() > 1) {
                    FirebaseReferences.storeRef.document(StoreInfo.storeID.toString())
                        .update("addresses", FieldValue.arrayRemove(item))
                    locationsList.remove(item)
                    notifyDataSetChanged()
                }else{
                    Toast.makeText(binding.root.context, "You must have at least one location", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}