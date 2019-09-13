package com.anwesh.uiprojects.multilinecolorscreenview

/**
 * Created by anweshmishra on 13/09/19.
 */

import android.view.View
import android.view.MotionEvent
import android.app.Activity
import android.content.Context
import android.graphics.Paint
import android.graphics.Canvas
import android.graphics.Color

val colors : Array<String> = arrayOf("#9C27B0", "#2196F3", "#00C853", "#1A237E", "#f44336")
val lines : Int = 5
val scGap : Float = 0.01f / lines
val strokeFactor : Int = 40

fun Int.inverse() : Float = 1f / this
fun Float.maxScale(i : Int, n : Int) : Float = Math.max(0f, this - i * n.inverse())
fun Float.divideScale(i : Int, n : Int) : Float = Math.min(n.inverse(), maxScale(i, n)) * n

fun Canvas.drawLineColorScreen(i : Int, hGap : Float, w : Float, sc1 : Float, sc2 : Float, shouldDraw : Boolean, paint : Paint) {
    val sc1i : Float = sc1.divideScale(0, 2)
    val sc2i : Float = sc2.divideScale(1, 2)
    var size : Float = w * sc2i
    if (shouldDraw) {
        size = w
    }
    save()
    translate(0f, hGap * (i + 1))
    drawLine(w * sc1i, 0f, size, 0f, paint)
    restore()
}

fun Canvas.drawMultiLineColorScreen(hGap : Float, w : Float, sc1 : Float, sc2 : Float, shouldDraw : Boolean, paint : Paint) {
    for (j in 0..(lines - 1)) {
        drawLineColorScreen(j, hGap, w, sc1, sc2, shouldDraw, paint)
    }
}

fun Canvas.drawMLCSNode(i : Int, scale : Float, sc : Float, currI : Int, paint : Paint) {
    val w : Float = width.toFloat()
    val h : Float = height.toFloat()
    val gap : Float = h / (colors.size + 1)
    paint.color = Color.parseColor(colors[i])
    paint.strokeWidth = Math.min(w, h) / strokeFactor
    paint.strokeCap = Paint.Cap.ROUND
    save()
    drawMultiLineColorScreen(gap, w, scale, sc, currI == i, paint)
    restore()
}

class MultiLineColorScreenView(ctx : Context) : View(ctx) {

    private val paint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas : Canvas) {

    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }

    data class State(var scale : Float = 0f, var dir : Float = 0f, var prevScale : Float = 0f) {

        fun update(cb : (Float) -> Unit) {
            scale += dir * scGap
            if (Math.abs(scale - prevScale) > 1) {
                scale = prevScale + dir
                dir = 0f
                prevScale = scale
                cb(prevScale)
            }
        }

        fun startUpdating(cb : () -> Unit) {
            if (dir == 0f) {
                dir = 1f - 2 * prevScale
                cb()
            }
        }
    }
}