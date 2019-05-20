package com.leu.radu.flowerorder.screen

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.leu.radu.flowerorder.data.ApiService
import com.leu.radu.flowerorder.data.Order
import com.leu.radu.flowerorder.data.OrderResponse
import com.leu.radu.flowerorder.utils.Outcome

class MainViewModel(private val apiService: ApiService): ViewModel() {

    private val ordersLiveData: MutableLiveData<Outcome<List<Order>>> = MutableLiveData()

    fun getOrdersLiveData() = ordersLiveData as LiveData<Outcome<List<Order>>>

    fun fetchOrders() {
//        apiService.getOrders()
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(this::onFetchOrderSuccess, this::onFetchOrderError)
//            .apply { addDisposable(this) }
    }

    private fun onFetchOrderSuccess(response: OrderResponse) {
        ordersLiveData.postValue(Outcome.success(response.orders))
    }

    private fun onFetchOrderError(throwable: Throwable) {
        //TODO: handle errors
    }
}