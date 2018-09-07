package com.alfredobejarano.drawview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View


/**
 * Custom class that allows the user draw lines with their fingers.
 *
 * @author Alfredo Bejarano
 * @version 1.0
 * @since 07/09/2018 - 01:44 PM
 */
class DrawView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    /**
     * Property defining the x position of a move touch event.
     */
    private var mX = 0f
    /**
     * Property defining the y position of a move touch event.
     */
    private var mY = 0f
    /**
     * Property defining the path of the finger draw.
     */
    private var mPath = Path()
    /**
     * Property defining the paint (color) of the finger draw.
     */
    private var mPaint = Paint()
    /**
     * Property defining the drawable are for the finger.
     */
    private var mCanvas = Canvas()
    /**
     * Property defining the movement of the circle indicator
     */
    private var mCirclePath = Path()
    /**
     * Property defining the color of the circle indicator stroke
     */
    private var mCirclePaint = Paint()
    /**
     * Property defining a Bitmap that the drawing will convert to.
     */
    private var mBitmap: Bitmap? = null
    /**
     * Property defining the color of the generated Bitmap by the draw.
     */
    private var mBitmapPaint = Paint(Paint.DITHER_FLAG)

    companion object {
        /**
         * Constant defining the threshold of finger displacement for performing a trace.
         */
        private const val TOUCH_TOLERANCE = 4
        /**
         * Constant defining the radius of the finger indicator.
         */
        private const val CIRCLE_INDICATOR_RADIUS = 30f
    }

    init {
        setupDrawPaint()
        setupCircleIndicatorPaint()
    }

    /**
     * Initializes the properties for the circle indicator shape.
     */
    private fun setupCircleIndicatorPaint() {
        mCirclePaint.strokeWidth = 4f
        mCirclePaint.isAntiAlias = true
        mCirclePaint.color = Color.BLACK
        mCirclePaint.style = Paint.Style.STROKE
        mCirclePaint.strokeJoin = Paint.Join.MITER
    }

    /**
     * Initializes the properties of the draw trace such as the color.
     */
    private fun setupDrawPaint() {
        mPaint = Paint()
        mPaint.isAntiAlias = true
        mPaint.isDither = true
        mPaint.color = Color.BLACK
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeJoin = Paint.Join.ROUND
        mPaint.strokeCap = Paint.Cap.ROUND
        mPaint.strokeWidth = 12f
    }

    /**
     * Creates a new Bitmap when the canvas size changes.
     */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        mBitmap?.let {
            mCanvas = Canvas(it)
        }
    }

    /**
     * Draws the bitmap, trace and indicator into the canvas
     * every time this view gets a Draw event.
     *
     */
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        // If the Bitmap and canvas are not null, draw it into the canvas.
        mBitmap?.let {
            canvas?.drawBitmap(it, 0f, 0f, mBitmapPaint)
        }
        // If the canvas is not null, draw the path and the circle indicator in the canvas.
        canvas?.let {
            it.drawPath(mPath, mPaint)
            it.drawPath(mCirclePath, mCirclePaint)
        }
    }

    /**
     * Defines the starting of the drawing into the canvas.
     * @param x New position of the finger in the canvas horizontally.
     * @param y New position of the finger in the canvas vertically.
     */
    private fun onTouchStart(x: Float, y: Float) {
        // Reset all previous draw paths.
        mPath.reset()
        // Move the path to the new position of the finger.
        mPath.moveTo(x, y)
        // report the move position
        mX = x
        mY = y
    }

    /**
     * Detects when the user has moved his finger across the canvas to perform a Draw.
     */
    private fun onTouchMoved(x: Float, y: Float) {
        // Get the absolute value of the difference between the current x and the moved x position.
        val dx = Math.abs(x - mX)
        // Get the absolute value of the difference between the current y and the moved y position.
        val dy = Math.abs(y - mY)
        // If any of the difference of the positions is larger that the threshold, perform a draw.
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            // Move the path to an exact quadrant to perform the draw.
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2)
        }
        // Report the new movement position.
        mX = x
        mY = y
        // Reset the indicator path.
        mCirclePath.reset()
        // Draw the indicator.
        mCirclePath.addCircle(mX, mY, CIRCLE_INDICATOR_RADIUS, Path.Direction.CW)
    }

    /**
     * Detects when the user has moved his finger from the canvas.
     */
    private fun onTouchUp() {
        // Move the path to the last moved position.
        mPath.lineTo(mX, mY)
        // Reset the indicator path.
        mCirclePath.reset()
        // Draw the result of the user finger movement.
        mCanvas.drawPath(mPath, mPaint)
        // Reset the draw path position.
        mPath.reset()
    }

    /**
     * Detects the type of motion that the user did with the finger onto the view
     */
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        // Get the coordinates of the touch event.
        val x = event?.x ?: 0f
        val y = event?.y ?: 0f
        // Detect which kind of event was performed into the view.
        when (event?.action) {
            // If the user has stopped pressing the screen.
            MotionEvent.ACTION_UP -> onTouchUp()
            // If the user started pressing the screen.
            MotionEvent.ACTION_DOWN -> onTouchStart(x, y)
            // If the user moves his finger across the screen while pressing it.
            MotionEvent.ACTION_MOVE -> onTouchMoved(x, y)
        }
        // Redraw this view.
        invalidate()
        // Return true, a touch event has been performed.
        return true
    }

    /**
     * Deletes any drawing made by the user.
     */
    fun clearCanvas() {
        // Resize this view to its current size.
        onSizeChanged(width, height, width, height)
        // Re-draw the view.
        invalidate()
    }
}