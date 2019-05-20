package com.leu.radu.flowerorder.screen

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.leu.radu.flowerorder.R

class OrderListAdapter(private var data: List<String>) :
    RecyclerView.Adapter<OrderListAdapter.OrderViewHolder>() {

    fun updateOrders(orders: List<String>) {
        data = orders
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.textView.text = data[position]
    }

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): OrderViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_mock_order, parent, false) as TextView
        return OrderViewHolder(textView)
    }

    class OrderViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

}