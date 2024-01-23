package com.seosh817.moviehub.core.database.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("DROP TABLE IF EXISTS movies_new")
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS movies_new (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "movie_id INTEGER NOT NULL, " +
                "adult INTEGER, " +
                "back_drop_path TEXT, " +
                "genre_ids TEXT, " +
                "original_language TEXT, " +
                "original_title TEXT, " +
                "overview TEXT, " +
                "popularity REAL, " +
                "poster_path TEXT, " +
                "release_date TEXT, " +
                "title TEXT, " +
                "video INTEGER, " +
                "vote_average REAL, " +
                "vote_count INTEGER, " +
                "type TEXT NOT NULL, " +
                "page INTEGER NOT NULL" +
                ")"
        )

        db.execSQL("INSERT INTO movies_new (" +
            "movie_id, adult, back_drop_path, genre_ids, original_language, original_title, overview, popularity, poster_path, release_date, title, video, vote_average, vote_count, type, page) " +
            "SELECT movie_id, adult, back_drop_path, genre_ids, original_language, original_title, overview, popularity, poster_path, release_date, title, video, vote_average, vote_count, type, page " +
            "FROM movies")

        db.execSQL("DROP TABLE movies")

        db.execSQL("ALTER TABLE movies_new RENAME TO movies")
//
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS remote_keys_new (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "type TEXT NOT NULL, " +
                "movie_id INTEGER NOT NULL, " +
                "prev_key INTEGER, " +
                "next_key INTEGER, " +
                "created_at INTEGER NOT NULL" +
                ")"
        )

        db.execSQL("INSERT INTO remote_keys_new (type, movie_id, prev_key, next_key, created_at) " +
            "SELECT type, movie_id, prev_key, next_key, created_at FROM remote_keys")

        db.execSQL("DROP TABLE remote_keys")

        db.execSQL("ALTER TABLE remote_keys_new RENAME TO remote_keys")

    }
}