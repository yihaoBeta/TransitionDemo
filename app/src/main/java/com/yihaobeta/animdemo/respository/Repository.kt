package com.yihaobeta.animdemo.respository

import com.google.gson.Gson
import com.yihaobeta.animdemo.AppContext
import com.yihaobeta.animdemo.FlowersBean

class Repository private constructor() {

    private val gson: Gson by lazy {
        Gson()
    }

    companion object {
        @Volatile
        private var instance: Repository? = null

        fun getInstance(): Repository {
            return instance ?: synchronized(this) {
                instance
                    ?: Repository().also {
                        instance = it
                    }
            }

        }
    }

    fun getFlowersData(): FlowersBean {
        val json = AppContext.assets.open("flowers.json").reader().readText()
        return gson.fromJson(json, FlowersBean::class.java)
    }
}