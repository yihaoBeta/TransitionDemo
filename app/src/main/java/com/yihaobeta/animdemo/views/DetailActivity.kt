package com.yihaobeta.animdemo.views

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.yihaobeta.animdemo.R
import com.yihaobeta.animdemo.databinding.ActivityDetailBinding
import com.yihaobeta.animdemo.viewmodel.FlowerViewModel
import com.yihaobeta.animdemo.viewmodel.ViewModelFactory

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = DataBindingUtil.setContentView<ActivityDetailBinding>(
            this,
            R.layout.activity_detail
        )
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //设置TextView可滚动
        root.detailDescription.movementMethod = ScrollingMovementMethod.getInstance()
        val viewModel =
            ViewModelProvider(
                this,
                ViewModelFactory.buildFlowersViewModel()
            ).get(FlowerViewModel::class.java)

        /**
         * 观察详细信息
         */
        viewModel.detail().observe(this, Observer {
            root.flower = it
            root.coverIv.post {
                Glide.with(this).load(it.picurl).into(root.coverIv)
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        finishAfterTransition()
        return super.onSupportNavigateUp()
    }
}