package com.yihaobeta.animdemo

data class FlowersBean(val title: String, val subtitle: String, val description: String, val flowers: List<FlowerBean>)
data class FlowerBean(val name: String, val classify: String, val picurl: String, val description: String)