package com.example.testproject.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.*
import kotlin.math.*

// Joystick class - View type
class Joystick @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : View(context, attrs, defStyle) {
    // fields for the joystick's appearance
    private var centerX: Float = 0.0f
    private var bigCenterX: Float = 0.0f
    private var centerY: Float = 0.0f
    private var bigCenterY: Float = 0.0f
    private var radius: Float = 0.0f
    private var bigRadius: Float = 0.0f
    private var largeRadius: Float = 0.0f
    private lateinit var a1 : PointF
    private lateinit var a2 : PointF
    private lateinit var a3 : PointF
    private lateinit var a4 : PointF
    private lateinit var b1 : PointF
    private lateinit var b2 : PointF
    private lateinit var b3 : PointF
    private lateinit var b4 : PointF
    private lateinit var c1 : PointF
    private lateinit var c2 : PointF
    private lateinit var c3 : PointF
    private lateinit var c4 : PointF
    private var isAlreadyClicked : Boolean = false // flag - if the joystick is pressed now

    // centerX getter
    fun getCenterX() : Float {
        return centerX
    }

    // centerY getter
    fun getCenterY() : Float {
        return centerY
    }

    // bigRadius getter
    fun getBigRadius() : Float {
        return bigRadius
    }

    private val paintSmallCircle = Paint().apply { // painting for the smallest circle in joystick
        style = Paint.Style.FILL_AND_STROKE
        color = Color.parseColor("#FF8A8A8A")
        isAntiAlias = true
    }
    private val paintBigCircle = Paint().apply { // painting for the middle circle in joystick
        style = Paint.Style.FILL_AND_STROKE
        color = Color.BLACK
        isAntiAlias = true
    }
    private val paintLargeCircle = Paint().apply { // painting for the biggest circle in joystick
        style = Paint.Style.FILL_AND_STROKE
        color = Color.parseColor("#FF2C2A2A")
        isAntiAlias = true
    }
    private val paintTriangle = Paint().apply { // painting for the 4 triangles in joystick
        style = Paint.Style.FILL_AND_STROKE
        color = Color.parseColor("#FF575757")
        isAntiAlias = true
    }

    // override - draw the joystick on the canvas according to the fields
    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        canvas.drawCircle(bigCenterX, bigCenterY, largeRadius, paintLargeCircle)
        canvas.drawCircle(bigCenterX, bigCenterY, bigRadius, paintBigCircle)

        val leftTriangle = Path()
        leftTriangle.moveTo(a1.x, a1.y)
        leftTriangle.lineTo(b1.x, b1.y)
        leftTriangle.lineTo(c1.x, c1.y)
        leftTriangle.close()
        canvas.drawPath(leftTriangle, paintTriangle)

        val rightTriangle = Path()
        rightTriangle.moveTo(a2.x, a2.y)
        rightTriangle.lineTo(b2.x, b2.y)
        rightTriangle.lineTo(c2.x, c2.y)
        rightTriangle.close()
        canvas.drawPath(rightTriangle, paintTriangle)

        val upTriangle = Path()
        upTriangle.moveTo(a3.x, a3.y)
        upTriangle.lineTo(b3.x, b3.y)
        upTriangle.lineTo(c3.x, c3.y)
        upTriangle.close()
        canvas.drawPath(upTriangle, paintTriangle)

        val bottomTriangle = Path()
        bottomTriangle.moveTo(a4.x, a4.y)
        bottomTriangle.lineTo(b4.x, b4.y)
        bottomTriangle.lineTo(c4.x, c4.y)
        bottomTriangle.close()
        canvas.drawPath(bottomTriangle, paintTriangle)

        canvas.drawCircle(centerX, centerY, radius, paintSmallCircle)
    }

    // override - call super and than initialize all the fields
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        initializeFields()
        invalidate()
    }

    // initial the fields according to the width and height that allocated
    private fun initializeFields() {
        centerX = width / 2.0f
        bigCenterX = width / 2.0f
        centerY = height / 2.0f
        bigCenterY = height / 2.0f
        radius = if (width > height) height / 7.0f else width / 7.0f
        bigRadius = radius * 2.0f
        largeRadius = radius * 3.5f
        a1 = PointF(bigCenterX - largeRadius + (largeRadius / 6.0f), bigCenterY)
        b1 = PointF(bigCenterX - largeRadius + (largeRadius / 4.0f), bigCenterY + (largeRadius / 9.0f))
        c1 = PointF(bigCenterX - largeRadius + (largeRadius / 4.0f), bigCenterY - (largeRadius / 9.0f))
        a2 = PointF(bigCenterX + largeRadius - (largeRadius / 6.0f), bigCenterY)
        b2 = PointF(bigCenterX + largeRadius - (largeRadius / 4.0f), bigCenterY + (largeRadius / 9.0f))
        c2 = PointF(bigCenterX + largeRadius - (largeRadius / 4.0f), bigCenterY - (largeRadius / 9.0f))
        a3 = PointF(bigCenterX, bigCenterY - largeRadius + (largeRadius / 6.0f))
        b3 = PointF(bigCenterX + (largeRadius / 9.0f), bigCenterY - largeRadius + (largeRadius / 4.0f))
        c3 = PointF(bigCenterX - (largeRadius / 9.0f), bigCenterY - largeRadius + (largeRadius / 4.0f))
        a4 = PointF(bigCenterX, bigCenterY + largeRadius - (largeRadius / 6.0f))
        b4 = PointF(bigCenterX + (largeRadius / 9.0f), bigCenterY + largeRadius - (largeRadius / 4.0f))
        c4 = PointF(bigCenterX - (largeRadius / 9.0f), bigCenterY + largeRadius - (largeRadius / 4.0f))
    }

    // override - implementation for onTouchEvent
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event == null)
            return true
        if (isAlreadyClicked || (event.x >= centerX - radius && event.x <= centerX + radius &&
            event.y >= centerY - radius && event.y <= centerY + radius)) {
            // if isAlreadyClicked = true or if the user is pressing the smallest circle in joystick
            when (event.action) {
                MotionEvent.ACTION_MOVE -> move(event.x, event.y) // joystick is pressed
                MotionEvent.ACTION_UP -> returnToCenter() // joystick released
            }
            val dist = sqrt(((bigCenterX - centerX) * (bigCenterX - centerX)) + ((bigCenterY - centerY) * (bigCenterY - centerY)))
            if (dist >= bigRadius) { // set boundaries for the smallest circle in joystick
                centerX = (centerX - bigCenterX) * bigRadius / dist + bigCenterX
                centerY = (centerY - bigCenterY) * bigRadius / dist + bigCenterY
                invalidate()
            }
        }
        return true
    }

    // when joystick is pressed
    private fun move(x: Float, y: Float) {
        isAlreadyClicked = true // update flag
        centerX = x // update centerX to event.x
        centerY = y // update centerY to event.y
        invalidate()
    }

    // when joystick released
    private fun returnToCenter() {
        isAlreadyClicked = false // update flag
        centerX = bigCenterX // update centerX to bigCenterX (center of joystick)
        centerY = bigCenterY // update centerY to bigCenterY (center of joystick)
        invalidate()
    }
}
