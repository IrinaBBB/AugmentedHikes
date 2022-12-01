package ru.irinavb.augmentedhikes.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.irinavb.augmentedhikes.db.entities.Hike

@Dao
interface HikeDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHike(hike: Hike)

    @Delete
    suspend fun deleteHike(hike: Hike)

    @Query("SELECT * FROM hikes_table ORDER BY timestamp DESC")
    fun getAllHikesSortedByDate(): LiveData<List<Hike>>

    @Query("SELECT * FROM hikes_table ORDER BY timeInMillis DESC")
    fun getAllHikesSortedByTimeInMillis(): LiveData<List<Hike>>

    @Query("SELECT * FROM hikes_table ORDER BY caloriesBurned DESC")
    fun getAllHikesSortedByCaloriesBurned(): LiveData<List<Hike>>

    @Query("SELECT * FROM hikes_table ORDER BY avgSpeedInKMH DESC")
    fun getAllHikesSortedByAvgSpeed(): LiveData<List<Hike>>

    @Query("SELECT * FROM hikes_table ORDER BY distanceInMeters DESC")
    fun getAllHikesSortedByDistance(): LiveData<List<Hike>>

    @Query("SELECT SUM(timeInMillis) FROM hikes_table")
    fun getTotalTimeInMillis(): LiveData<Long>

    @Query("SELECT SUM(caloriesBurned) FROM hikes_table")
    fun getTotalCaloriesBurnt(): LiveData<Int>

    @Query("SELECT SUM(distanceInMeters) FROM hikes_table")
    fun getTotalDistance(): LiveData<Int>

    @Query("SELECT AVG(avgSpeedInKMH) FROM hikes_table")
    fun getTotalAvgSpeed(): LiveData<Float>
}