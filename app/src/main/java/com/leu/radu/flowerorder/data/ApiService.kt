package com.leu.radu.flowerorder.data

import io.reactivex.Flowable
import retrofit2.http.GET

interface ApiService {

    @GET
    fun getOrders(): Flowable<OrderResponse>
}