package com.yihaobeta.animdemo.views

import android.content.Intent
import android.graphics.PointF
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.transition.*
import com.yihaobeta.animdemo.FlowerSeasonBean
import com.yihaobeta.animdemo.R
import com.yihaobeta.animdemo.viewmodel.FlowerViewModel
import com.yihaobeta.animdemo.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var viewModel: FlowerViewModel
    private val seasonMap = mutableMapOf<String, FlowerSeasonBean>()
    private var selectedSeasonImageView: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory.buildFlowersViewModel()
        ).get(FlowerViewModel::class.java)
        supportActionBar?.title = "花期"
        initViews()
        observerData(viewModel)
    }

    private fun observerData(viewModel: FlowerViewModel) {
        viewModel.flowers().observe(this, Observer {
            it.season.forEach { season ->
                seasonMap[season.name] = season
            }
        })
    }

    private fun initViews() {
        //设置按键监听
        arrayOf(springFrame, summerFrame, autumnFrame, winterFrame).forEach {
            it.setOnClickListener(this)
        }
        goButton.setOnClickListener {
            viewModel.setSeason(seasonMap[curSeasonName]!!)

            //实现shared效果的转场动画
            val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                Pair.create(selectedSeasonImageView, "shared_image")
            )
                .toBundle()
            startActivity(Intent(this, FloweringSeasonActivity::class.java), bundle)
        }
    }

    private var curSeasonName: String = ""
    override fun onClick(v: View) {

        //获取当前点击的季节信息
        when (v.id) {
            R.id.springFrame -> {
                selectedSeasonImageView = springIv
                curSeasonName = "spring"
            }
            R.id.summerFrame -> {
                selectedSeasonImageView = summerIv
                curSeasonName = "summer"
            }
            R.id.autumnFrame -> {
                selectedSeasonImageView = autumnIv
                curSeasonName = "autumn"
            }
            R.id.winterFrame -> {
                selectedSeasonImageView = winterIv
                curSeasonName = "winter"
            }
        }

        descriptionTv.text = seasonMap[curSeasonName]?.description

        //开始动画效果
        TransitionManager.beginDelayedTransition(
            imageContainer,
            TransitionInflater.from(this).inflateTransition(R.transition.set)
        )
        isSelected = !isSelected
        TransitionManager.beginDelayedTransition(contentContainer, makeTransition(isSelected))
        changeScene(v)
    }

    private var isSelected = false

    /**
     * 构建底部介绍信息及Button的动画效果
     */
    private fun makeTransition(modeIn: Boolean): TransitionSet {
        val slide = Slide()
        slide.mode = if (modeIn) Slide.MODE_IN else Slide.MODE_OUT
        slide.slideEdge = if (modeIn) Gravity.LEFT else Gravity.RIGHT
        slide.interpolator = if (modeIn) DecelerateInterpolator() else AccelerateInterpolator()
        return TransitionSet().addTransition(slide).addTransition(Fade())
    }

    //改变场景，以便Transition生效
    private fun changeScene(v: View) {
        //缩放效果
        v.scaleX = if (v.scaleX > 1) 1f else 2f
        v.scaleY = if (v.scaleY > 1) 1f else 2f
        changePosition(v)
        changeVisibility(summerFrame, springFrame, autumnFrame, winterFrame, contentContainer)
        v.visibility = View.VISIBLE
    }

    private var isTrans = false
    private val originPos = PointF()
    /**
     * 改变位置，移动到中心
     */
    private fun changePosition(v: View) {
        isTrans = !isTrans
        if (isTrans) {
            originPos.x = v.x
            originPos.y = v.y
            val rootWidth = imageContainer.width
            v.x = (rootWidth.toFloat() / 2f) - v.width / 2
            v.y = (rootWidth.toFloat() / 2f) - v.height / 2
        } else {
            v.x = originPos.x
            v.y = originPos.y
        }
    }

    /**
     * 改变可见状态
     */
    private fun changeVisibility(vararg views: View) {
        views.forEach {
            it.visibility = if (it.visibility == View.VISIBLE) View.INVISIBLE else View.VISIBLE
        }
    }
}