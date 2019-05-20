package com.yihaobeta.animdemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
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

        val root = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        setSupportActionBar(toolbar)
        recyclerView.adapter = adapter

        playLayoutAnimation(recyclerView,AnimationUtils.loadAnimation(this,R.anim.layout_item_anim),false)

        adapter.setOnItemClickListener(object : FlowerListAdapter.ItemClickListener {
            override fun onClick(view: View) {
                val position = recyclerView.getChildAdapterPosition(view)
                viewModel?.setCurDetail(position)
                val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(this@MainActivity,
                    Pair.create(view.findViewById(R.id.item_image),"shared_cover"),
                    Pair.create(view.findViewById(R.id.item_name),"shared_title")
                ).toBundle()
                startActivity(Intent(this@MainActivity,DetailActivity::class.java),bundle)
            }
        })
        viewModel = ViewModelProvider(this, ViewModelFactory.buildFlowersViewModel()).get(FlowerViewModel::class.java)
        viewModel?.flowers()?.observe(this, Observer {
            Log.d("TAG", "title :${it.title}")
            root.flowers = it
            supportActionBar?.title = it.title
            adapter.setData(it.flowers)
            recyclerView.scheduleLayoutAnimation()
        })
    }


    /**
     * 播放RecyclerView动画
     *
     * @param animation
     * @param isReverse
     */
    private fun playLayoutAnimation(target:RecyclerView,animation: Animation, isReverse: Boolean) {
        val controller = LayoutAnimationController(animation)
        controller.delay = 0.2f
        controller.order =
            if (isReverse) LayoutAnimationController.ORDER_REVERSE else LayoutAnimationController.ORDER_NORMAL

        target.layoutAnimation = controller
        target.adapter?.notifyDataSetChanged()
        target.scheduleLayoutAnimation()
    }
}
