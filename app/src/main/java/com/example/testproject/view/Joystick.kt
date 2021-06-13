package com.example.testproject.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.*
import kotlin.math.*


class Joystick @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : View(context, attrs, defStyle) {

    var centerX: Float = 0.0f
    var bigCenterX: Float = 0.0f
    var centerY: Float = 0.0f
    var bigCenterY: Float = 0.0f
    var radius: Float = 0.0f
    var bigRadius: Float = 0.0f
    var largeRadius: Float = 0.0f
    lateinit var a1 : PointF
    lateinit var a2 : PointF
    lateinit var a3 : PointF
    lateinit var a4 : PointF
    lateinit var b1 : PointF
    lateinit var b2 : PointF
    lateinit var b3 : PointF
    lateinit var b4 : PointF
    lateinit var c1 : PointF
    lateinit var c2 : PointF
    lateinit var c3 : PointF
    lateinit var c4 : PointF
    var isAlreadyClicked : Boolean = false

    private val paintSmallCircle = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.parseColor("#FF8A8A8A")
        isAntiAlias = true
    }
    private val paintBigCircle = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.BLACK
        isAntiAlias = true
    }
    private val paintLargeCircle = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.parseColor("#FF2C2A2A")
        isAntiAlias = true
    }
    private val paintTriangle = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.parseColor("#FF575757")
        isAntiAlias = true
    }

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

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        initializeFields()
        invalidate()
    }

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

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event == null)
            return true
        if (isAlreadyClicked || (event.x >= centerX - radius && event.x <= centerX + radius &&
            event.y >= centerY - radius && event.y <= centerY + radius)) {
            when (event.action) {
                MotionEvent.ACTION_MOVE -> move(event.x, event.y)
                MotionEvent.ACTION_UP -> returnToCenter()
            }
            val dist = sqrt(((bigCenterX - centerX) * (bigCenterX - centerX)) + ((bigCenterY - centerY) * (bigCenterY - centerY)))
            if (dist >= bigRadius) {
                centerX = (centerX - bigCenterX) * bigRadius / dist + bigCenterX
                centerY = (centerY - bigCenterY) * bigRadius / dist + bigCenterY
                invalidate()
            }
        }
        return true
    }

    fun move(x: Float, y: Float) {
        isAlreadyClicked = true
        centerX = x;
        centerY = y;
        invalidate()
    }

    fun returnToCenter() {
        isAlreadyClicked = false
        centerX = bigCenterX;
        centerY = bigCenterY;
        invalidate()
    }
}





//    fun getAngle(x: Float, y: Float) : Float{
//        var angle = Math.toDegrees(Math.atan2((bigCenterY - y).toDouble(),(x - bigCenterX).toDouble()))
//        if(angle < 0){
//            angle += 360;
//        }
//        return angle.toFloat();
//    }

//    private val paint = Paint().apply {
//        println("paint!!!!!!!!!!!!!!!!!!!")
//        style = Paint.Style.STROKE
//        color = Color.BLACK
//        isAntiAlias = true
//    }
//
//    private var radius: Float = 100f
//    private var center: PointF = PointF()
//
//    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
////        super.onSizeChanged(w, h, oldw, oldh)
//        println("111111111111111")
//    }
//
//
//    override fun onDraw(canvas: Canvas) {
//        center.x = 5.0f
//        center.y = 5.0f
//        println("draw!!!!!!!!!!!!!!!!!!!")
//        super.onDraw(canvas)
//        canvas.drawCircle(center.x, center.y, radius, paint)
//    }
//
//    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$")
//        return super.onTouchEvent(event)
//    }
