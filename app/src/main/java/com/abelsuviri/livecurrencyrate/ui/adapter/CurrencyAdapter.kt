package com.abelsuviri.livecurrencyrate.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.abelsuviri.livecurrencyrate.R
import com.abelsuviri.livecurrencyrate.ui.adapter.util.DiffUtilCallback
import com.abelsuviri.livecurrencyrate.ui.adapter.viewholder.CurrencyViewHolder
import com.abelsuviri.livecurrencyrate.ui.adapter.viewholder.ItemClick

/**
 * @author Abel Suviri
 */

class CurrencyAdapter constructor(private val currencies: List<String>, private var rates: List<Double>, private val itemClick: ItemClick,
                                  private var inputAmount: String) : RecyclerView.Adapter<CurrencyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return CurrencyViewHolder(itemView, itemClick)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(currencies[position], calculateRate(position), position)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int, payload: List<Any>) {
        //If there is a payload then bind only the rate field, otherwise bind the whole row
        if (payload.isEmpty()) {
            holder.bind(currencies[position], calculateRate(position), position)
        } else {
            holder.bindRate(calculateRate(position))
        }
    }

    override fun getItemCount(): Int {
        return rates.size
    }

    //Calculate the exchange rate
    private fun calculateRate(position: Int): Double {
        return if (position == 0) rates[position] else rates[position] * inputAmount.toDouble()
    }

    //Check the difference between the current data and the new data
    fun setNewData(rates: List<Double>, inputAmount: String) {
        val diffResult = DiffUtil.calculateDiff(DiffUtilCallback(this.rates, rates))
        this.inputAmount = inputAmount
        this.rates = rates
        diffResult.dispatchUpdatesTo(this)
    }
}