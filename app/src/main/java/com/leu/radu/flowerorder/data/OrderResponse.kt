package com.leu.radu.flowerorder.data

import com.google.gson.annotations.SerializedName

class OrderResponse(
    @SerializedName("result") val orders: List<Order>
)