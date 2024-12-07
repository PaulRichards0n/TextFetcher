package com.paulrich.textfetcher

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class TypewriterTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private var text = ""
    private var index = 0
    private var delay: Long = 50 // Default delay between characters in milliseconds

    private val handler = Handler(Looper.getMainLooper())
    private val characterAdder = object : Runnable {
        override fun run() {
            text?.let {
                if (index <= it.length) {
                    setText(it.substring(0, index++))
                    handler.postDelayed(this, delay)
                }
            }
        }
    }

    fun setTypewriterText(text: String, delay: Long = 50) {
        this.text = text
        this.delay = delay
        index = 0

        // Remove any existing callbacks
        handler.removeCallbacks(characterAdder)
        // Start the animation
        handler.postDelayed(characterAdder, delay)
    }

    fun stopTypewriter() {
        handler.removeCallbacks(characterAdder)
    }
}