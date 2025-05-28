package com.example.van1

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "VanGoghArtDB"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "liked_artworks"
        private const val COLUMN_ID = "id"
        private const val COLUMN_ARTWORK_NAME = "artwork_name"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_ARTWORK_NAME TEXT UNIQUE
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addLikedArtwork(artworkName: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_ARTWORK_NAME, artworkName)
        
        return try {
            db.insertOrThrow(TABLE_NAME, null, values)
            true
        } catch (e: Exception) {
            false
        } finally {
            db.close()
        }
    }

    fun removeLikedArtwork(artworkName: String): Boolean {
        val db = this.writableDatabase
        return try {
            db.delete(TABLE_NAME, "$COLUMN_ARTWORK_NAME = ?", arrayOf(artworkName)) > 0
        } finally {
            db.close()
        }
    }

    fun isArtworkLiked(artworkName: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_NAME,
            arrayOf(COLUMN_ARTWORK_NAME),
            "$COLUMN_ARTWORK_NAME = ?",
            arrayOf(artworkName),
            null,
            null,
            null
        )
        
        return cursor.use { it.count > 0 }
    }

    fun getAllLikedArtworks(): List<String> {
        val artworks = mutableListOf<String>()
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_NAME,
            arrayOf(COLUMN_ARTWORK_NAME),
            null,
            null,
            null,
            null,
            "$COLUMN_ARTWORK_NAME ASC"
        )

        cursor.use {
            while (it.moveToNext()) {
                artworks.add(it.getString(it.getColumnIndexOrThrow(COLUMN_ARTWORK_NAME)))
            }
        }
        return artworks
    }
} 