package com.example.van1

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class ArtworkAdapter(
    private val artworks: List<String>,
    private val dbHelper: DatabaseHelper? = null
) : RecyclerView.Adapter<ArtworkAdapter.ArtworkViewHolder>() {

    class ArtworkViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: MaterialButton = view.findViewById(R.id.artworkTitle)
        val likeButton: MaterialButton = view.findViewById(R.id.likeButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtworkViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_artwork, parent, false)
        return ArtworkViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArtworkViewHolder, position: Int) {
        val artwork = artworks[position]
        holder.title.text = artwork
        
        // Set up click listener for artwork title
        holder.title.setOnClickListener { view ->
            val context = view.context
            val intent = Intent(context, ArtworkDetailActivity::class.java).apply {
                putExtra(ArtworkDetailActivity.EXTRA_ARTWORK_TITLE, artwork)
            }
            context.startActivity(intent)
        }

        // Handle like button if database helper is available
        dbHelper?.let { db ->
            // Update like button icon based on current state
            updateLikeButtonState(holder.likeButton, artwork, db)

            // Set up like button click listener
            holder.likeButton.setOnClickListener {
                val isCurrentlyLiked = db.isArtworkLiked(artwork)
                if (isCurrentlyLiked) {
                    db.removeLikedArtwork(artwork)
                } else {
                    db.addLikedArtwork(artwork)
                }
                // Update button state after database operation
                updateLikeButtonState(holder.likeButton, artwork, db)
            }
        } ?: run {
            // If no database helper, hide the like button
            holder.likeButton.visibility = View.GONE
        }
    }

    private fun updateLikeButtonState(button: MaterialButton, artwork: String, db: DatabaseHelper) {
        val isLiked = db.isArtworkLiked(artwork)
        button.setIconResource(
            if (isLiked) android.R.drawable.star_big_on
            else android.R.drawable.star_big_off
        )
    }

    override fun getItemCount() = artworks.size
}