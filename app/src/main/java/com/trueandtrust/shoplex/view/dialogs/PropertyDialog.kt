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
import com.trueandtrust.shoplex.model.interfaces.INotifyMVP
import com.trueandtrust.shoplex.model.pojo.Property

class PropertyDialog : AppCompatDialogFragment {
    private lateinit var btnAddValue : ImageButton
    private lateinit var edValue : EditText
    private lateinit var edProperty : EditText
    private lateinit var chipValues : ChipGroup
    private lateinit var btnConfirm : Button
    private lateinit var btnCancel : Button
    private var listener: INotifyMVP

    constructor(listener: INotifyMVP){
        this.listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(activity,R.style.ThemeOverlay_AppCompat_Dialog_Alert)
        val inflater = activity?.layoutInflater
        val view: View = inflater!!.inflate(R.layout.prop_dialog, null)

        builder.setView(view).setTitle(getString(R.string.property))

        btnAddValue = view.findViewById(R.id.btnAddValue)
        edProperty = view.findViewById(R.id.edPropertyName)
        edValue = view.findViewById(R.id.edPropertyValue)
        chipValues = view.findViewById(R.id.cg_values)
        // Add Values in Chips
        btnAddValue.setOnClickListener {
            val inflater = LayoutInflater.from(context)
            val chipItem = inflater.inflate(R.layout.chip_entry_item, null, false) as Chip
            chipItem.text = edValue.text.toString()

            chipItem.setOnCloseIconClickListener {
                chipValues.removeView(it)
            }
            when {
                edValue.length() == 0 -> {
                    edValue.error = getString(R.string.Required)
                    return@setOnClickListener
                }
                else -> {
                    chipValues.addView(chipItem)
                    edValue.text.clear()
                }
            }
        }
        //cancel
        btnCancel = view.findViewById(R.id.btnCancelProp)
        btnCancel.setOnClickListener {
            dismiss()
        }
        //Confirm Property
        btnConfirm = view.findViewById(R.id.btnPropertyConfirm)
        btnConfirm.setOnClickListener{
            when{
                edProperty.length() == 0 -> {edProperty.error = getString(R.string.Required)
                    return@setOnClickListener}
                chipValues.size == 0 -> {edValue.error = getString(R.string.Required)
                    return@setOnClickListener}
                else -> dismiss()
            }
            var property : Property = Property()
            property.name = edProperty.text.toString()
            for(item in 0 until chipValues.childCount){
                val chip = chipValues.getChildAt(item) as Chip

                property.values.add(chip.text.toString())
            }

            listener.applyData(property)
        }
        return builder.create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = try {
            context as INotifyMVP
        } catch (e: ClassCastException) {
            throw ClassCastException(
                context.toString() + "PropertyDialogListener"
            )
        }
    }

}