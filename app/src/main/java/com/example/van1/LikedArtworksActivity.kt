package com.example.van1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button

class LikedArtworksActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_liked_artworks)

        dbHelper = DatabaseHelper(this)
        
        // Get liked artworks from database
        val likedArtworks = dbHelper.getAllLikedArtworks()
        
        // Set up RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.likedArtworksRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ArtworkAdapter(likedArtworks)

        // Set up back button
        findViewById<Button>(R.id.backToMainButton).setOnClickListener {
            finish()
        }
    }
} 