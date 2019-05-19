package com.yihaobeta.animdemo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FlowerViewModel private constructor(): ViewModel() {
    private val repository:Repository by lazy {
        Repository.getInstance()
    }
    companion object{
        @Volatile
        var INSTANCE:FlowerViewModel?=null
        fun getInstance():FlowerViewModel{
            return INSTANCE ?: synchronized(this){
                INSTANCE?:FlowerViewModel().also {
                    INSTANCE = it
                }
            }
        }
    }
    private val flowers = MutableLiveData<FlowersBean>()
    private val curDetailFlower = MutableLiveData<FlowerBean>()
    init {
        flowers.value = repository.getFlowersData()
    }
    fun flowers(): LiveData<FlowersBean> = flowers
    fun setCurDetail(position:Int){
        curDetailFlower.value = flowers.value?.flowers?.get(position)
    }
    fun detail():LiveData<FlowerBean> = curDetailFlower
}