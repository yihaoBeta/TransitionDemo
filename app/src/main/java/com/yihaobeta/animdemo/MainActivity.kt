package com.yihaobeta.animdemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yihaobeta.animdemo.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var viewModel: FlowerViewModel? = null
    private val adapter: FlowerListAdapter by lazy {
        FlowerListAdapter(mutableListOf())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //window.enterTransition = android.transition.Slide()
            //android.transition.TransitionInflater.from(this).inflateTransition(R.transition.enter_transition)

        val root = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        setSupportActionBar(toolbar)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener(object : FlowerListAdapter.ItemClickListener {
            override fun onClick(view: View) {
                val position = recyclerView.getChildAdapterPosition(view)
                Log.d("TAG", "click:$position,name:${adapter.getFlowerByPosition(position).name}")
                viewModel?.setCurDetail(position)
                startActivity(Intent(this@MainActivity,WelcomeActivity::class.java))
            }
        })
        viewModel = ViewModelProvider(this, ViewModelFactory.buildFlowersViewModel()).get(FlowerViewModel::class.java)
        viewModel?.flowers()?.observe(this, Observer {
            Log.d("TAG", "title :${it.title}")
            root.flowers = it
            supportActionBar?.title = it.title
            adapter.setData(it.flowers)
        })
    }
}
