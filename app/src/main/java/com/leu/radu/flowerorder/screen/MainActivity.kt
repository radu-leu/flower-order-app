package com.leu.radu.flowerorder.screen

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.leu.radu.flowerorder.R
import com.leu.radu.flowerorder.data.Order
import com.leu.radu.flowerorder.utils.Outcome
import com.leu.radu.flowerorder.utils.observeNonNull
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity: AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: OrderListAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()
        mainViewModel.getOrdersLiveData().observeNonNull(this) { outcome ->
            when (outcome) {
                is Outcome.Success -> {
                    updateOrdersList(outcome.data)
                    swipeContainer.isRefreshing = false
                }
            }
        }
        swipeContainer.setOnRefreshListener {
            mainViewModel.fetchOrders()
        }
        swipeContainer.isRefreshing = true
        mainViewModel.fetchOrders()
    }

    private fun initRecyclerView() {
        viewManager = LinearLayoutManager(this)
        viewAdapter = OrderListAdapter(arrayListOf())

        recyclerView = findViewById<RecyclerView>(R.id.mainRecyclerView).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    private fun updateOrdersList(orders: List<Order>) {
        viewAdapter.updateOrders(orders)
    }

}
