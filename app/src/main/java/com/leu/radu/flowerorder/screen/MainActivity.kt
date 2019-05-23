package com.leu.radu.flowerorder.screen

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import com.leu.radu.flowerorder.R
import com.leu.radu.flowerorder.data.Order
import com.leu.radu.flowerorder.utils.Outcome
import com.leu.radu.flowerorder.utils.observeNonNull
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.order_details_dialog.view.*
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

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
        viewAdapter = OrderListAdapter(arrayListOf(), this::onOrderClick)

        recyclerView = findViewById<RecyclerView>(R.id.mainRecyclerView).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    private fun onOrderClick(order: Order) {
        LayoutInflater.from(this).inflate(
            R.layout.order_details_dialog,
            mainActivityContainer,
            false
        ).also {
            initializeDetailsDialog(it, order)
            val dialog = AlertDialog.Builder(this)
                .setView(it)
                .create()

            it.detailsCloseImageView.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }
    }

    private fun initializeDetailsDialog(view: View, order: Order) {
        view.detailsOrderContentsTextView.text = getString(R.string.order_contents, order.quantity, order.flower)
        view.detailsAddressTextView.text = order.address
        view.detailsRecipientTextView.text = order.recipientName
        view.detailsOrderIdTextView.text = order.id.toString()
        view.detailsReceiptNumberTextView.text = order.receiptNumber
        view.detailsDeliveryTimeTextView.text = order.deliveryTime
        view.detailsPaidInAdvanceTextView.text = if (order.paidInAdvance) "Yes" else "No"
        if (order.fulfilled) {
            view.detailsOrderStatusTextView.apply {
                text = getString(R.string.order_fulfilled)
                background = getDrawable(R.color.colorPrimary)
            }
        } else {
            view.detailsOrderStatusTextView.apply {
                text = getString(R.string.order_pending)
                background = getDrawable(R.color.order_pending)
            }
        }
    }

    private fun updateOrdersList(orders: List<Order>) {
        viewAdapter.updateOrders(orders)
    }

}
