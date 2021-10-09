package me.htookyaw.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dagger.hilt.android.qualifiers.ApplicationContext

/**
 * Created by A.K.HTOO on 02/07/2020,July,2020.
 */
@Database(entities = [MovieRow::class], version = 1, exportSchema = false)
@TypeConverters(value = [Converters::class])
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {

        fun create(@ApplicationContext context: Context): MovieDatabase {
            return Room.databaseBuilder(
                context,
                MovieDatabase::class.java, "database"
            ).build()
        }
    }
}
