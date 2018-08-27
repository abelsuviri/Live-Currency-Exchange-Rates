package com.abelsuviri.livecurrencyrate.ui.adapter.util

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil

/**
 * @author Abel Suviri
 */

class DiffUtilCallback constructor(private val oldRates: List<Double>, private val newRates: List<Double>) : DiffUtil.Callback() {

    private val bundleKey = "refresh"

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return true     //Return always true to enforce adding the payload in case the content is different
    }

    override fun getOldListSize(): Int {
        return oldRates.size
    }

    override fun getNewListSize(): Int {
        return newRates.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        //If it is the same position enforce the items are the same to avoid refreshing that row when editing
        return oldItemPosition == 0 && newItemPosition == 0 || oldRates[oldItemPosition] == newRates[newItemPosition]
    }

    //Add a payload if the content is different to just update the changed view in the row
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val bundle = Bundle()
        if (!areItemsTheSame(oldItemPosition, newItemPosition)) {
            bundle.putBoolean(bundleKey, true)
        }

        return bundle
    }
}