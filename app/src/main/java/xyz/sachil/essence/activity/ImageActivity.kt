package xyz.sachil.essence.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Application
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.coroutines.*
import xyz.sachil.essence.R
import xyz.sachil.essence.databinding.ActivityImageBinding
import xyz.sachil.essence.model.net.GlideApp
import xyz.sachil.essence.widget.ScalableImageView

@Suppress("DEPRECATION")
class ImageActivity : AppCompatActivity() {
    companion object {
        private val TAG = "ImageActivity"
    }

    private lateinit var viewBinding: ActivityImageBinding
    private lateinit var autoHideSystemBarJob: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        showImage()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initViews() {
        showSystemBars()
        viewBinding = ActivityImageBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        setSupportActionBar(viewBinding.toolBar)
        val title = intent.getStringExtra("title")
        supportActionBar?.title = title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewBinding.image.setOnClickListener {
            if (window.decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
                hideSystemBars()
                if(autoHideSystemBarJob.isActive){
                    autoHideSystemBarJob.cancel()
                }
            } else {
                showSystemBars()
                autoHideSystemBars()
            }
        }
        registerSystemBarVisibilityChangedListener()
        autoHideSystemBars()
    }

    private fun showImage() {
        val imageUrl = intent.getStringExtra("image")
        if (imageUrl != null) {
            GlideApp.with(this)
                .asBitmap()
                .load(imageUrl)
                .into(object : CustomViewTarget<ScalableImageView, Bitmap>(viewBinding.image) {
                    override fun onLoadFailed(errorDrawable: Drawable?) {

                    }

                    override fun onResourceCleared(placeholder: Drawable?) {

                    }

                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        view.setImage(resource, false)
                    }
                })
        }
    }

    private fun Toolbar.show() {
        if (this.alpha > 0) {
            return
        }
        val alphaAnimator = ObjectAnimator.ofFloat(this, "alpha", 0F, 1.0F)
        val translationAnimator = ObjectAnimator.ofFloat(
            viewBinding.toolBar,
            "translationY",
            this.translationY, 0F
        )
        val animatorSet = AnimatorSet()
        animatorSet.duration = 300
        animatorSet.playTogether(alphaAnimator, translationAnimator)
        animatorSet.start()
    }

    private fun Toolbar.hide() {
        if (this.alpha > 0F) {
            val alphaAnimator = ObjectAnimator.ofFloat(this, "alpha", 1.0F, 0F)
            val translationAnimator = ObjectAnimator.ofFloat(
                viewBinding.toolBar,
                "translationY",
                this.translationY - this.height
            )
            val animatorSet = AnimatorSet()
            animatorSet.duration = 300
            animatorSet.playTogether(alphaAnimator, translationAnimator)
            animatorSet.start()
        }
    }

    private fun autoHideSystemBars() {
        autoHideSystemBarJob = lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                delay(2 * 1000)
            }
            hideSystemBars()
        }
    }


    private fun showSystemBars() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }

    private fun hideSystemBars() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    private fun registerSystemBarVisibilityChangedListener() {
        window.decorView.setOnSystemUiVisibilityChangeListener {
            if (it and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
                //system bar is visible
                viewBinding.toolBar.show()
            } else {
                //system bar is not visible
                viewBinding.toolBar.hide()
            }
        }
    }
}