package com.trueandtrust.shoplex.model.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.databinding.LocationItemRowBinding
import com.trueandtrust.shoplex.model.firebase.ProductsDBModel
import com.trueandtrust.shoplex.model.pojo.PendingLocation
import com.trueandtrust.shoplex.viewmodel.StoreVM

class LocationAdapter(val locationsList: ArrayList<PendingLocation>) :
    RecyclerView.Adapter<LocationAdapter.LocationsViewHolder>() {

    private lateinit var storeVM: StoreVM
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationsViewHolder {
        context = parent.context
        storeVM = ViewModelProvider(context as AppCompatActivity).get(StoreVM::class.java)
        return LocationsViewHolder(
            LocationItemRowBinding.inflate(LayoutInflater.from(context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: LocationsViewHolder, position: Int) =
        holder.bind(locationsList[position])

    override fun getItemCount() = locationsList.size

    inner class LocationsViewHolder(val binding: LocationItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(location: PendingLocation) {
            binding.tvLocation.text = location.address

            binding.btnColse.setOnClickListener {
                if (locationsList.count() > 1) {
                    storeVM.removeLocationAddress(location)
                    storeVM.isLocationRemoved.observe(
                        it.context as AppCompatActivity,
                        { isRemoved ->
                            if (isRemoved == true) {

                                val builder = AlertDialog.Builder(context)
                                builder.setTitle(context.getString(R.string.delete))
                                builder.setMessage(context.getString(R.string.deleteMessage))

                                builder.setPositiveButton(context.getString(R.string.yes)) { _, _ ->
                                    locationsList.remove(location.address)
                                    notifyItemRemoved(bindingAdapterPosition)
                                    Snackbar.make(binding.root, context.getString(R.string.deleteSuccess), Snackbar.LENGTH_LONG)
                                        .show()
                                }

                                builder.setNegativeButton(context.getString(R.string.no)) { dialog, _ ->
                                    dialog.cancel()
                                }

                                builder.show()

                            }
                        })
                } else {
                    Toast.makeText(
                        binding.root.context,
                        "You must have at least one location",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
    }
}