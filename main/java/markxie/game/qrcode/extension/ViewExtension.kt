package markxie.game.qrcode.extension

import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.LinearInterpolator

fun View.setBlinkAnim() {
    val alphaAnimation = AlphaAnimation(1.0f, 0.0f)
    alphaAnimation.run {
        duration = 1000
        fillBefore = true
        interpolator = LinearInterpolator()
        repeatCount = Animation.INFINITE
        repeatMode = Animation.REVERSE
    }
    startAnimation(alphaAnimation)
}