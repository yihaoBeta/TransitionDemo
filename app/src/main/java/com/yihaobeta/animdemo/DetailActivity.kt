package com.yihaobeta.animdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.yihaobeta.animdemo.databinding.ActivityDetailBinding

class DetailActivity :AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = DataBindingUtil.setContentView<ActivityDetailBinding>(this,R.layout.activity_detail)
        val viewModel =
            ViewModelProvider(this, ViewModelFactory.buildFlowersViewModel()).get(FlowerViewModel::class.java)
        viewModel.detail().observe(this, Observer {
            root.flower = it
            root.detailCoverIv.post {
                Glide.with(this).load(it.picurl).into(root.detailCoverIv)
            }
        })
    }
}