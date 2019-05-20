package com.yihaobeta.animdemo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * 构建ViewModel的工厂类
 */
object ViewModelFactory {
    fun buildFlowersViewModel(): FlowersVMFactory {
        return FlowersVMFactory()
    }
}

class FlowersVMFactory : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FlowerViewModel.getInstance() as T
    }
}