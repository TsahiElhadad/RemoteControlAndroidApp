package com.example.testproject.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
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


    private val paint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.RED
        isAntiAlias = true
    }
    private val paint2 = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.BLUE
        isAntiAlias = true
    }


    override fun onDraw(canvas: Canvas) {
        canvas.drawCircle(bigCenterX, bigCenterY, bigRadius, paint2)
        canvas.drawCircle(centerX, centerY, radius, paint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        initializeFields()
    }

    fun initializeFields() {
        centerX = width / 2.0f
        bigCenterX = width / 2.0f
        centerY = height / 2.0f
        bigCenterY = height / 2.0f
        radius = if (width > height) height / 7.0f else width / 7.0f
        bigRadius = radius * 2.5f
        invalidate()
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event == null)
            return true
//        if (event.x >= centerX - radius && event.x <= centerX + radius &&
//            event.y >= centerY - radius && event.y <= centerY + radius) {
            when (event.action) {
                MotionEvent.ACTION_MOVE -> move(event.x, event.y)
                MotionEvent.ACTION_UP -> returnToCenter()
            }
            val dist = sqrt(((bigCenterX - centerX) * (bigCenterX - centerX)) + ((bigCenterY - centerY) * (bigCenterY - centerY)))
            if (dist >= bigRadius) {
                centerX = (centerX - bigCenterX) * bigRadius / dist + bigCenterX
                centerY = (centerY - bigCenterY) * bigRadius / dist + bigCenterY
                invalidate()
                return true
            }
        //}
        //else
            //returnToCenter()
        return true
    }

    fun move(x: Float, y: Float) {
        centerX = x;
        centerY = y;
        invalidate()
    }

    fun returnToCenter() {
        centerX = bigCenterX;
        centerY = bigCenterY;
        invalidate()
    }

    fun edgeCase(x: Float, y: Float) {
        var dist = sqrt(((bigCenterX - x) * (bigCenterX - x)) + ((bigCenterY - y) * (bigCenterY - y)))
        if(dist <= bigRadius) {
            centerX = x
            centerY = y
        }
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
