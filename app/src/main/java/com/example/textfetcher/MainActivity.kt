package com.paulrich.textfetcher

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class MainActivity : AppCompatActivity() {
    private lateinit var contentText: TypewriterTextView
    private lateinit var downloadButton: Button
    private lateinit var shareButton: Button
    private lateinit var aboutButton: Button
    private lateinit var satelliteButton: Button // New button for Satellite Position Activity

    private val textUrl = "https://r1ch4rds0n.com/hf.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        contentText = findViewById(R.id.contentText)
        downloadButton = findViewById(R.id.downloadButton)
        shareButton = findViewById(R.id.shareButton)
        aboutButton = findViewById(R.id.aboutButton)
        satelliteButton = findViewById(R.id.navigate_to_satellite_page) // Initialize new button

        // Set click listeners
        downloadButton.setOnClickListener {
            downloadFile()
        }

        shareButton.setOnClickListener {
            shareContent()
        }

        aboutButton.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }

       // Automatically download when app starts
        downloadFile()
    }

    private fun downloadFile() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val content = URL(textUrl).readText()
                withContext(Dispatchers.Main) {
                    contentText.setTypewriterText(content, delay = 3) // Use typewriter effect
                    Toast.makeText(this@MainActivity, "Download successful", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    contentText.setTypewriterText("Error loading content. Please try again.", delay = 3)
                }
            }
        }
    }

    private fun shareContent() {
        val content = contentText.text.toString()
        if (content.isNotEmpty()) {
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, content)
            }
            startActivity(Intent.createChooser(shareIntent, "Share via"))
        } else {
            Toast.makeText(this, "No content to share", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        contentText.stopTypewriter() // Clean up the typewriter animation
    }
}
