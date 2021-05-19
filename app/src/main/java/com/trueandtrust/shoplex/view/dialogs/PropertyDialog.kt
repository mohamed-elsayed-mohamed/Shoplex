package com.trueandtrust.shoplex.view.dialogs


import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.view.size
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.databinding.PropDialogBinding
import com.trueandtrust.shoplex.model.interfaces.INotifyMVP
import com.trueandtrust.shoplex.model.pojo.Property

class PropertyDialog(val listener: INotifyMVP): AppCompatDialogFragment() {
    private lateinit var binding: PropDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        this.binding = PropDialogBinding.inflate(LayoutInflater.from(this.context))
        val builder = AlertDialog.Builder(activity, R.style.ThemeOverlay_AppCompat_Dialog_Alert)
        builder.setView(binding.root).setTitle(getString(R.string.property))

        // Add Values in Chips
        binding.btnAddValue.setOnClickListener {
            val inflater = LayoutInflater.from(context)
            val chipItem = inflater.inflate(R.layout.chip_entry_item, null, false) as Chip
            chipItem.text = binding.edPropertyValue.text.toString()

            chipItem.setOnCloseIconClickListener {
                binding.cgValues.removeView(it)
            }
            when {
                binding.edPropertyValue.length() == 0 -> {
                    binding.edPropertyValue.error = getString(R.string.Required)
                    return@setOnClickListener
                }
                else -> {
                    binding.cgValues.addView(chipItem)
                    binding.edPropertyValue.text?.clear()
                }
            }
        }
        //cancel
        binding.btnCancelProp.setOnClickListener {
            dismiss()
        }
        //Confirm Property
        binding.btnPropertyConfirm.setOnClickListener{
            when{
                binding.edPropertyName.length() == 0 -> {binding.edPropertyName.error = getString(R.string.Required)
                    return@setOnClickListener}
                binding.cgValues.size == 0 -> {binding.edPropertyValue.error = getString(R.string.Required)
                    return@setOnClickListener}
                else -> dismiss()
            }
            var property = Property()
            property.name = binding.edPropertyName.text.toString()
            for(item in 0 until binding.cgValues.childCount){
                val chip = binding.cgValues.getChildAt(item) as Chip

                property.values.add(chip.text.toString())
            }

            listener.onNewPropertyAdded(property)
        }
        return builder.create()
    }
}