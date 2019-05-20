package com.leu.radu.flowerorder.data

import com.google.gson.annotations.SerializedName

class Order(
    @SerializedName("id") val id: Int,
    @SerializedName("flower") val flower: String,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("address") val address: String,
    @SerializedName("recipientName") val recipientName: String
) {

    override fun toString() = "Order $id: $quantity $flower"
}