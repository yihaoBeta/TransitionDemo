package com.yihaobeta.animdemo

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
import androidx.transition.*
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity(), View.OnClickListener {

    private var selectedSeasonImageView: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        supportActionBar?.title = "开花季节"
        initViews()
    }

    private fun initViews() {
        arrayOf(springFrame, summerFrame, autumnFrame, winterFrame).forEach {
            for (i in 0..it.childCount) {
                if (it.getChildAt(i) is ImageView) {
                    Glide.with(this).load(R.mipmap.cover)
                        .into(it.getChildAt(i) as ImageView)
                    break
                }
            }

            it.setOnClickListener(this)
        }
        goButton.setOnClickListener {
            val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(this, Pair.create(selectedSeasonImageView, "shared_image"))
                .toBundle()
            startActivity(Intent(this, MainActivity::class.java), bundle)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.springFrame -> selectedSeasonImageView = springIv
            R.id.summerFrame -> selectedSeasonImageView = summerIv
            R.id.autumnFrame -> selectedSeasonImageView = autumnIv
            R.id.winterFrame -> selectedSeasonImageView = winterIv
        }
        TransitionManager.beginDelayedTransition(
            imageContainer,
            TransitionInflater.from(this).inflateTransition(R.transition.set)
        )
        isSelected = !isSelected
        TransitionManager.beginDelayedTransition(contentContainer, makeTransition(isSelected))
        changeScene(v)
    }

    private var isSelected = false
    private fun makeTransition(modeIn: Boolean): TransitionSet {
        val slide = Slide()
        slide.mode = if (modeIn) Slide.MODE_IN else Slide.MODE_OUT
        slide.slideEdge = if (modeIn) Gravity.LEFT else Gravity.RIGHT
        slide.interpolator = if (modeIn) DecelerateInterpolator() else AccelerateInterpolator()
        return TransitionSet().addTransition(slide).addTransition(Fade())
    }

    private fun changeScene(v: View) {
        v.scaleX = if (v.scaleX > 1) 1f else 2f
        v.scaleY = if (v.scaleY > 1) 1f else 2f
        changePosition(v)
        changeVisibility(summerFrame, springFrame, autumnFrame, winterFrame, contentContainer)
        v.visibility = View.VISIBLE
    }

    private var isTrans = false
    private val originPos = PointF()
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

    private fun changeVisibility(vararg views: View) {
        views.forEach {
            it.visibility = if (it.visibility == View.VISIBLE) View.INVISIBLE else View.VISIBLE
        }
    }
}