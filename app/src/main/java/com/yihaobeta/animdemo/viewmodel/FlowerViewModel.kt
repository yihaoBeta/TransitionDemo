package com.yihaobeta.animdemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yihaobeta.animdemo.FlowerBean
import com.yihaobeta.animdemo.FlowerSeasonBean
import com.yihaobeta.animdemo.FlowersBean
import com.yihaobeta.animdemo.respository.Repository

/**
 * 全局的ViewModel 单例实现 为UI提供数据
 */
class FlowerViewModel private constructor() : ViewModel() {
    private val repository: Repository by lazy {
        Repository.getInstance()
    }

    companion object {
        @Volatile
        var INSTANCE: FlowerViewModel? = null

        fun getInstance(): FlowerViewModel {
            return INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: FlowerViewModel().also {
                        INSTANCE = it
                    }
            }
        }
    }

    /**
     * 特定季节花期的鲜花列表
     */
    private val flowers = MutableLiveData<FlowersBean>()
    /**
     * 当前选择的开花季节
     */
    private val season = MutableLiveData<FlowerSeasonBean>()
    /**
     * 当前选择的特定鲜花详情
     */
    private val curDetailFlower = MutableLiveData<FlowerBean>()

    init {
        flowers.value = repository.getFlowersData()
    }

    fun flowers(): LiveData<FlowersBean> = flowers
    fun setCurDetail(position: Int) {
        curDetailFlower.value = season.value?.flowers?.get(position)
    }

    fun setSeason(seasonBean: FlowerSeasonBean) {
        season.value = seasonBean
    }

    fun getSeason() = season
    fun detail(): LiveData<FlowerBean> = curDetailFlower
}