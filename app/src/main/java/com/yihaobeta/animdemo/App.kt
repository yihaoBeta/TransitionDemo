package com.yihaobeta.animdemo

import android.app.Application
import android.content.ContextWrapper

class App :Application(){
    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}

lateinit var INSTANCE:App
object AppContext : ContextWrapper(INSTANCE)