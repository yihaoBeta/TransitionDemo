package com.yihaobeta.animdemo

import com.google.gson.Gson

class Repository private constructor() {

    private val gson: Gson by lazy {
        Gson()
    }

    companion object {
        @Volatile
        private var instance: Repository? = null

        fun getInstance(): Repository {
            return instance ?: synchronized(this) {
                instance ?: Repository().also {
                    instance = it
                }
            }

        }
    }

    fun getFlowersData(): FlowersBean {
        val json = AppContext.assets.open("flowers.json").reader().readText()
        return Gson().fromJson(json, FlowersBean::class.java)
    }
}