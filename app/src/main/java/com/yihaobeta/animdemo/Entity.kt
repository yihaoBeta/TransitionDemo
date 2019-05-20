package com.yihaobeta.animdemo

/**
 * 鲜花实体类，包含所有季节的鲜花数据
 */
data class FlowersBean(
    val title: String,
    val subtitle: String,
    val description: String,
    val season: List<FlowerSeasonBean>
)

/**
 * 某个季节的所有鲜花数据
 */
data class FlowerSeasonBean(val name: String, val description: String, val flowers: List<FlowerBean>)

/**
 * 某一种鲜花的详细信息
 */
data class FlowerBean(val name: String, val classify: String, val picurl: String, val description: String)