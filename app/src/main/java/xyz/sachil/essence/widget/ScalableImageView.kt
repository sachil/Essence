package xyz.sachil.essence.widget

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.OverScroller
import kotlin.math.max
import kotlin.math.min

class ScalableImageView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    View(context, attrs, defStyleAttr) {

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null)

    companion object {
        private const val TAG = "ScalableImageView"
        private const val ZOOM_IN_FRACTION = 2.5F
        private const val EQUAL_FACTOR = 0.00001F
        private const val SCALE_ANIMATION_DURATION = 300L
    }

    private var imageWidth = 0
    private var imageHeight = 0
    private var smallestScale = 0F
    private var largestScale = 0F
    private var largestImageWidth = 0
    private var largestImageHeight = 0
    private var translateY = 0F
    private var translateX = 0F
    private lateinit var image: Bitmap
    private var isPlaceHolder = false
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    //用于辅助执行fling动画
    private val overScroller = OverScroller(context)
    //用于执行缩放动画
    private lateinit var scaleAnimator: ObjectAnimator
    //用于检测双击、滑动和fling手势
    private val gestureDetector = GestureDetector(context, GestureListener())
    //用于检测缩放手势
    private val scaleGestureDetector = ScaleGestureDetector(context, ScaleGestureListener())
    private val filingAnimationRunner = FlingAnimationRunner()

    var currentScale = 0.0F
        set(value) {
            field = value
            invalidate()
        }

    fun setImage(image: Bitmap, isPlaceHolder: Boolean) {
        this.image = image
        this.isPlaceHolder = isPlaceHolder
        imageWidth = image.width
        imageHeight = image.height
        initializeScale()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var result = scaleGestureDetector.onTouchEvent(event)
        if (!scaleGestureDetector.isInProgress) {
            result = gestureDetector.onTouchEvent(event)
        }
        return result
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (::image.isInitialized) {
            val scaleFraction = (currentScale - smallestScale) / (largestScale - smallestScale)
            canvas.translate(translateX * scaleFraction, translateY * scaleFraction)
            canvas.scale(currentScale, currentScale, width / 2.0f, height / 2.0f)
            canvas.drawBitmap(
                image,
                (width - imageWidth) / 2.0f,
                (height - imageHeight) / 2.0f,
                paint
            )
        }
    }

    private fun initializeScale() {
        val imageAspectRatio = imageWidth.toFloat() / imageHeight
        val viewAspectRatio = width.toFloat() / height

        if (imageAspectRatio > viewAspectRatio) {
            smallestScale = width.toFloat() / imageWidth
            largestScale = height.toFloat() / imageHeight
        } else {
            smallestScale = height.toFloat() / imageHeight
            largestScale = width.toFloat() / imageWidth
        }
        //为了可以上下左右拖动，最大缩放比例再大一些
        largestScale *= ZOOM_IN_FRACTION
        //初始化当前缩放比例
        currentScale = smallestScale
        largestImageWidth = (imageWidth * largestScale).toInt()
        largestImageHeight = (imageHeight * largestScale).toInt()
        //初始化缩放动画
        scaleAnimator = ObjectAnimator.ofFloat(
            this@ScalableImageView,
            "currentScale",
            smallestScale,
            largestScale
        ).setDuration(SCALE_ANIMATION_DURATION)

    }

    private fun fixTranslate() {
        //这儿使用最大缩放比例的图片大小，是因为在onDraw()方法中，移动偏移量是乘了缩放系数的。
        translateX = min((largestImageWidth - width) / 2F, translateX)
        translateX = max(-(largestImageWidth - width) / 2F, translateX)
        translateY = min((largestImageHeight - height) / 2F, translateY)
        translateY = max(-(largestImageHeight - height) / 2F, translateY)
    }

    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {

        override fun onDown(e: MotionEvent?): Boolean = true

        override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
            return this@ScalableImageView.callOnClick()
        }


        override fun onDoubleTap(e: MotionEvent): Boolean {
            if (!::scaleAnimator.isInitialized) {
                return false
            }
            scaleAnimator.setFloatValues(smallestScale, largestScale)
            //如果当前已经缩放到最大
            if (largestScale - currentScale <= EQUAL_FACTOR) {
                scaleAnimator.reverse()
            } else {
                if (currentScale - smallestScale <= EQUAL_FACTOR) {
                    //从手指焦点处开始缩放
                    val offsetX = e.x - width / 2F
                    val offsetY = e.y - height / 2F
                    translateX = offsetX - offsetX * largestScale / smallestScale
                    translateY = offsetY - offsetY * largestScale / smallestScale
                    fixTranslate()
                } else {
                    scaleAnimator.setFloatValues(currentScale, largestScale)
                }
                scaleAnimator.start()
            }
            return false
        }

        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent?,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            val currentImageWidth = imageWidth * currentScale
            val currentImageHeight = imageHeight * currentScale
            //当image的尺寸大于view的尺寸时，才支持scroll
            if (currentImageWidth <= width && currentImageHeight <= height) {
                return false
            }
            if (currentImageWidth > width) {
                translateX -= distanceX
                //当image的高度未超过view的高度时，不支持上下scroll
                if (currentImageHeight <= height) {
                    translateY = 0f
                }
            }
            if (currentImageHeight > height) {
                translateY -= distanceY
                //当image的宽度未超过view的高度时，不支持左右scroll
                if (currentImageWidth <= width) {
                    translateX = 0f
                }
            }
            fixTranslate()
            invalidate()
            return false
        }

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent?,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            val currentImageWidth: Float = imageWidth * currentScale
            val currentImageHeight: Float = imageHeight * currentScale
            //只有当image的尺寸大于view的尺寸时才能fling
            if (currentImageWidth <= width && currentImageHeight <= height) {
                return false
            }

            var minX = 0
            var maxX = 0
            var minY = 0
            var maxY = 0

            //计算
            if (currentImageWidth > width) {
                minX = (-(largestImageWidth - width) / 2F).toInt()
                maxX = ((largestImageWidth - width) / 2F).toInt()
            }

            if (currentImageHeight > height) {
                minY = (-(largestImageHeight - height) / 2F).toInt()
                maxY = ((largestImageHeight - height) / 2F).toInt()
            }

            overScroller.fling(
                translateX.toInt(),
                translateY.toInt(),
                velocityX.toInt(),
                velocityY.toInt(),
                minX,
                maxX,
                minY,
                maxY
            )
            //启动fling动画
            postOnAnimation(filingAnimationRunner)
            return false
        }
    }

    private inner class ScaleGestureListener : ScaleGestureDetector.OnScaleGestureListener {
        private var beginScale = 0F

        override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
            beginScale = currentScale
            return true
        }

        override fun onScale(detector: ScaleGestureDetector): Boolean {
            currentScale = beginScale * detector.scaleFactor
            if (currentScale * 2 <= smallestScale && ::scaleAnimator.isInitialized) {
                scaleAnimator.setFloatValues(currentScale, smallestScale)
                scaleAnimator.start()
            } else {
                //从手指焦点处开始缩放
                val offsetX = detector.focusX - width / 2F
                val offsetY = detector.focusY - height / 2F
                translateX = offsetX - offsetX * currentScale / smallestScale
                translateY = offsetY - offsetY * currentScale / smallestScale

                fixTranslate()
                invalidate()
            }
            return false
        }

        override fun onScaleEnd(detector: ScaleGestureDetector?) {
            if (::scaleAnimator.isInitialized) {
                //当缩放比例大于最大缩放比例时，恢复到最大缩放比例
                if (currentScale > largestScale) {
                    scaleAnimator.setFloatValues(currentScale, largestScale)
                    scaleAnimator.start()
                } else if (currentScale < smallestScale) {
                    //当缩放比例小于最小缩放比例时，恢复到最小缩放比例
                    scaleAnimator.setFloatValues(currentScale, smallestScale)
                    scaleAnimator.start()
                }
            }
        }
    }

    private inner class FlingAnimationRunner : Runnable {
        override fun run() {
            //递归调用不断的执行fling动画
            if (overScroller.computeScrollOffset()) {
                translateX = overScroller.currX.toFloat()
                translateY = overScroller.currY.toFloat()
                invalidate()
                postOnAnimation(this)
            }
        }
    }

}