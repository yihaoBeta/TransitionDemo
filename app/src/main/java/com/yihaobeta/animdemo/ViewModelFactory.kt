package com.yihaobeta.animdemo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

object ViewModelFactory {
    fun buildFlowersViewModel():FlowersVMFactory{
        return FlowersVMFactory()
    }
}

class FlowersVMFactory :ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FlowerViewModel.getInstance() as T
    }
}