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
