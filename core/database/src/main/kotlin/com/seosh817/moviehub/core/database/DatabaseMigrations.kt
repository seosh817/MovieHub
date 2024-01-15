package com.seosh817.moviehub.core.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MigrationFrom1to2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS `remote_keys_temp` " +
                "(`next_key` INTEGER, " +
                "`label` TEXT NOT NULL, " +
                "`movie_id` INTEGER NOT NULL, " +
                "`prev_key` INTEGER, " +
                "PRIMARY KEY(`movie_id`))"
        )
        db.execSQL(
            "INSERT INTO `remote_keys_temp` (`movie_id`, `label`, `prev_key`, `next_key`) " +
                "SELECT `nextPage` AS `movie_id`, `key` AS `label`, NULL AS `prev_key`, NULL AS `next_key` " +
                "FROM `remote_keys` WHERE `nextPage` IS NOT NULL"
        )
        db.execSQL("DROP TABLE `remote_keys`")
        db.execSQL("ALTER TABLE `remote_keys_temp` RENAME TO `remote_keys`")
    }
}