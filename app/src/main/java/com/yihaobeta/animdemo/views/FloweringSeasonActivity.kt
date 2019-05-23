package com.yihaobeta.animdemo.views

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
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
import com.yihaobeta.animdemo.R
import com.yihaobeta.animdemo.databinding.ActivityFloweringSeasonBinding
import com.yihaobeta.animdemo.viewmodel.FlowerViewModel
import com.yihaobeta.animdemo.viewmodel.ViewModelFactory
import com.yihaobeta.animdemo.views.adapter.FlowerListAdapter
import kotlinx.android.synthetic.main.activity_flowering_season.*


class FloweringSeasonActivity : AppCompatActivity() {

    private var viewModel: FlowerViewModel? = null
    private val adapter: FlowerListAdapter by lazy {
        FlowerListAdapter(mutableListOf())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flowering_season)
        DataBindingUtil.setContentView<ActivityFloweringSeasonBinding>(
            this,
            R.layout.activity_flowering_season
        )
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        recyclerView.adapter = adapter

        //设置Recyclerview中Item的动画，只实现了Appearing的动画效果，其他类似
        playLayoutAnimation(
            recyclerView, AnimationUtils.loadAnimation(
                this,
                R.anim.layout_item_anim
            ), false
        )

        adapter.setOnItemClickListener(object : FlowerListAdapter.ItemClickListener {
            override fun onClick(view: View) {
                val position = recyclerView.getChildAdapterPosition(view)
                viewModel?.setCurDetail(position)

                //实现shared效果的转场动画
                val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this@FloweringSeasonActivity,
                    Pair.create(view.findViewById(R.id.item_image), "shared_cover"),
                    Pair.create(view.findViewById(R.id.item_name), "shared_title")
                ).toBundle()
                startActivity(Intent(this@FloweringSeasonActivity, DetailActivity::class.java), bundle)
            }
        })

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory.buildFlowersViewModel()
        ).get(FlowerViewModel::class.java)

        /**
         * 观察season数据
         */
        viewModel?.getSeason()?.observe(this, Observer {
            when (it.name) {
                "spring" -> {
                    coverIv.setImageDrawable(getDrawable(R.mipmap.spring))
                    supportActionBar?.title = getString(R.string.season_spring)
                }
                "summer" -> {
                    coverIv.setImageDrawable(getDrawable(R.mipmap.summer))
                    supportActionBar?.title = getString(R.string.season_summer)
                }
                "autumn" -> {
                    coverIv.setImageDrawable(getDrawable(R.mipmap.autumn))
                    supportActionBar?.title = getString(R.string.season_autumn)
                }
                "winter" -> {
                    coverIv.setImageDrawable(getDrawable(R.mipmap.winter))
                    supportActionBar?.title = getString(R.string.season_winter)
                }
            }
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
    private fun playLayoutAnimation(target: RecyclerView, animation: Animation, isReverse: Boolean) {
        val controller = LayoutAnimationController(animation)
        controller.delay = 0.2f
        controller.order =
            if (isReverse) LayoutAnimationController.ORDER_REVERSE else LayoutAnimationController.ORDER_NORMAL

        target.layoutAnimation = controller
        target.adapter?.notifyDataSetChanged()
        target.scheduleLayoutAnimation()
    }

    override fun onSupportNavigateUp(): Boolean {
        finishAfterTransition()
        return super.onSupportNavigateUp()
    }
}
