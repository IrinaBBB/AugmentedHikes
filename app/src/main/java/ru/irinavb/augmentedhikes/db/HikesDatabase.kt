package ru.irinavb.augmentedhikes.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.irinavb.augmentedhikes.db.converters.Converters
import ru.irinavb.augmentedhikes.db.dao.HikeDAO
import ru.irinavb.augmentedhikes.db.entities.Hike

@Database(entities = [Hike::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class HikesDatabase : RoomDatabase() {
    abstract fun getHikeDao(): HikeDAO
}