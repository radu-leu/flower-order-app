package com.leu.radu.flowerorder.screen

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leu.radu.flowerorder.R
import com.leu.radu.flowerorder.data.Order
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_order_card.view.*

class OrderListAdapter(private var data: List<Order>, private val orderClickListener: (Order) -> Unit) :
    RecyclerView.Adapter<OrderListAdapter.OrderViewHolder>() {

    fun updateOrders(orders: List<Order>) {
        data = orders
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bindData(data[position])
    }

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): OrderViewHolder {
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order_card, parent, false) as CardView
        return OrderViewHolder(cardView)
    }

    inner class OrderViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindData(order: Order) {
            val context = containerView.context
            containerView.orderAddressTextView.text = order.address
            containerView.orderRecipientTextView.text = order.recipientName
            containerView.orderContentsTextView.text =
                context.getString(R.string.order_contents, order.quantity, order.flower)
            containerView.orderDoneImageView.visibility =
                if (order.fulfilled) View.VISIBLE else View.GONE
            containerView.setOnClickListener {
                orderClickListener(order)
            }
        }
    }

}