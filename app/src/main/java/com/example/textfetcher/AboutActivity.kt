package com.paulrich.textfetcher

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        findViewById<Button>(R.id.backButton).setOnClickListener {
            finish()
        }
    }
}