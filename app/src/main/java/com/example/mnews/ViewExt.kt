package com.example.mnews

import android.animation.ObjectAnimator
import android.view.View

fun View.loadingAnimation() {
    val fadeAnim = ObjectAnimator.ofFloat(this, "alpha", 1f, 0.15f)
    fadeAnim.duration = 500
    fadeAnim.repeatCount = ObjectAnimator.INFINITE
    fadeAnim.repeatMode = ObjectAnimator.REVERSE
    fadeAnim.start()
    tag = fadeAnim
}