package com.abelsuviri.livecurrencyrate.ui.adapter.viewholder

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*


/**
 * @author Abel Suviri
 */

class CurrencyViewHolder constructor(view: View, private val itemClick: ItemClick) : RecyclerView.ViewHolder(view) {
    fun bind(currency: String, rate: Double, position: Int) {
        itemView.currencyTextView.text = currency   //Set the currency
        itemView.rateTextView.setText(String.format("%.4f", rate), TextView.BufferType.EDITABLE)    //Set only 4 decimal places for the exchange rate
        itemView.rateTextView.setSelection(itemView.rateTextView.length())  //Set cursor to the last place of the EditText

        //When the row is clicked trigger the event to set the clicked currency as the base currency
        itemView.currencyTextView.setOnClickListener { itemClick.onItemClick(currency) }
        itemView.setOnClickListener { itemClick.onItemClick(currency) }

        //Set all the EditText fields non focusable except the base currency one
        if (position != 0) {
            itemView.rateTextView.isFocusable = false
        } else {
            //Add a text change listener to update the exchange rates for the input value
            itemView.rateTextView.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(text: Editable?) {
                    itemClick.onTextChanged(text.toString())
                }
            })
        }
    }

    //Update the exchange rate view only
    fun bindRate(rate: Double) {
        itemView.rateTextView.setText(String.format("%.4f", rate), TextView.BufferType.EDITABLE)
    }
}