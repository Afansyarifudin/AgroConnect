package com.example.agroconnect.auth

import android.graphics.Paint
import android.graphics.Typeface
import android.text.TextPaint
import android.text.style.MetricAffectingSpan

class CustomTypefaceSpan(private val typeface: Typeface?) : MetricAffectingSpan() {

    override fun updateMeasureState(paint: TextPaint) {
        paint.typeface = typeface
        paint.flags = paint.flags or Paint.SUBPIXEL_TEXT_FLAG
    }

    override fun updateDrawState(paint: TextPaint) {
        paint.typeface = typeface
        paint.flags = paint.flags or Paint.SUBPIXEL_TEXT_FLAG
    }
}
