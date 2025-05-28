package com.example.van1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.widget.ImageView
import com.google.android.material.button.MaterialButton

class ArtworkDetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_ARTWORK_TITLE = "artwork_title"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artwork_detail)

        val artworkTitle = intent.getStringExtra(EXTRA_ARTWORK_TITLE) ?: return

        findViewById<TextView>(R.id.artworkDetailTitle).text = artworkTitle
        
        // Set the appropriate image based on the artwork title
        val imageResource = when (artworkTitle) {
            "The Starry Night (1889)" -> R.drawable.starry_night
            "Sunflowers (1888)" -> R.drawable.sunflowers
            "The Potato Eaters (1885)" -> R.drawable.potato_eaters
            "Wheatfield with Crows (1890)" -> R.drawable.wheatfield_crows
            "Bedroom in Arles (1888)" -> R.drawable.bedroom_arles
            "Café Terrace at Night (1888)" -> R.drawable.cafe_terrace
            "Almond Blossoms (1890)" -> R.drawable.almond_blossoms
            "Irises (1889)" -> R.drawable.irises
            else -> R.drawable.starry_night // default image
        }
        
        findViewById<ImageView>(R.id.artworkDetailImage).setImageResource(imageResource)

        // Set up back button
        findViewById<MaterialButton>(R.id.backButton).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Close this activity
        }
    }
} 