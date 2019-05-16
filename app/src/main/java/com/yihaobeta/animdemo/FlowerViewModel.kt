package com.yihaobeta.animdemo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FlowerViewModel(val repository: Repository) : ViewModel() {
    private val flowers = MutableLiveData<FlowersBean>()

    init {
        flowers.value = repository.getFlowersData()
    }
    fun flowers(): LiveData<FlowersBean> = flowers
}