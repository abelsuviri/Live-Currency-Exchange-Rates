package com.abelsuviri.livecurrencyrate.ui

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.abelsuviri.livecurrencyrate.R
import com.abelsuviri.livecurrencyrate.ui.adapter.CurrencyAdapter
import com.abelsuviri.livecurrencyrate.ui.adapter.viewholder.ItemClick
import com.abelsuviri.viewmodel.MainViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import javax.inject.Inject

/**
 * @author Abel Suviri
 */

class MainActivity : AppCompatActivity(), ItemClick {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory    //Inject the ViewModel factory

    private lateinit var mainViewModel: MainViewModel

    private var baseCurrency = "EUR"    //Set euro as default currency
    private var inputValue = "1.0"      //Set 1 as default input value

    private var adapter: CurrencyAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProviders.of(this, viewModelFactory)[MainViewModel::class.java]    //Initialize the ViewModel

        initViews()
        subscribeLiveData()
        mainViewModel.getCurrencyRate(baseCurrency, inputValue, false)     //Make the first request
    }

    //Setup the RecyclerView LayoutManager and ItemDecoration
    private fun initViews() {
        currenciesRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        currenciesRecyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
    }

    //Subscribe to the ViewModel LiveData values
    private fun subscribeLiveData() {
        //If network call fails then display an error message
        mainViewModel.hasFailed.observe(this, Observer<Boolean> { hasFailed ->  if (hasFailed!!) showError()})

        //Observe the changes in currencyAmount after each network call and display the data in the RecyclerView
        mainViewModel.currencyAmount.observe(this, Observer <LinkedHashMap<String, Double>> {
            if (adapter == null) {  //If the adapter is null recreate the list, otherwise update the changed values
                adapter = CurrencyAdapter(it.keys.toMutableList(), it.values.toMutableList(), this, inputValue)
                currenciesRecyclerView.adapter = null
                currenciesRecyclerView.adapter = adapter
            } else {
                adapter!!.setNewData(it.values.toMutableList(), inputValue)
            }
        })
    }

    private fun showError() {
        Toast.makeText(this, "Request Error", Toast.LENGTH_SHORT).show()
    }

    //Change the base currency when a row is clicked and re-create then list
    override fun onItemClick(currency: String) {
        mainViewModel.onUpdatingRate()
        adapter = null
        baseCurrency = currency
        mainViewModel.getCurrencyRate(baseCurrency, inputValue, false)
    }

    //Change the input value when the input amount has been changed and enforce the download of the latest rates.
    override fun onTextChanged(value: String) {
        mainViewModel.onUpdatingRate()
        inputValue = if (!TextUtils.isEmpty(value)) value else "0.0"
        mainViewModel.getCurrencyRate(baseCurrency, inputValue, false)
    }
}
