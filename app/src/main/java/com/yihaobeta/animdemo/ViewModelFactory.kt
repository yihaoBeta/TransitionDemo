package com.yihaobeta.animdemo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

object ViewModelFactory {
    fun buildFlowersViewModel():FlowersVMFactory{
        return FlowersVMFactory(Repository.getInstance())
    }
}

class FlowersVMFactory(private val repository: Repository):ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FlowerViewModel(repository) as T
    }
}