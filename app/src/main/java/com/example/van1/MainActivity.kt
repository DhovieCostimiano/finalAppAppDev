package com.example.van1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DatabaseHelper(this)

        // Sample artworks data
        val artworks = listOf(
            "The Starry Night (1889)",
            "Sunflowers (1888)",
            "The Potato Eaters (1885)",
            "Wheatfield with Crows (1890)",
            "Bedroom in Arles (1888)",
            "Café Terrace at Night (1888)",
            "Almond Blossoms (1890)",
            "Irises (1889)"
        )

        val recyclerView = findViewById<RecyclerView>(R.id.artworksRecyclerView).apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = ArtworkAdapter(artworks, dbHelper)
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    LinearLayoutManager.VERTICAL
                )
            )
        }

        // Set up favorites button
        findViewById<Button>(R.id.viewFavoritesButton).setOnClickListener {
            val intent = Intent(this, LikedArtworksActivity::class.java)
            startActivity(intent)
        }
    }
}