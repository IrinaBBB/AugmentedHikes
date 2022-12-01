package ru.irinavb.augmentedhikes.repositories

import ru.irinavb.augmentedhikes.db.dao.HikeDAO
import ru.irinavb.augmentedhikes.db.entities.Hike
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val hikeDAO: HikeDAO,
) {
    suspend fun insertHike(hike: Hike) = hikeDAO.insertHike(hike)

    suspend fun deleteHike(hike: Hike) = hikeDAO.deleteHike(hike)

    fun getAllHikesSortedByDate() = hikeDAO.getAllHikesSortedByDate()
    fun getAllHikesSortedByCaloriesBurned() = hikeDAO.getAllHikesSortedByCaloriesBurned()
    fun getAllHikesSortedByAvgSpeed() = hikeDAO.getAllHikesSortedByAvgSpeed()
    fun getAllHikesSortedByDistance() = hikeDAO.getAllHikesSortedByDistance()
    fun getAllHikesSortedByMillis() = hikeDAO.getAllHikesSortedByTimeInMillis()

    fun getTotalTimeInMillis() = hikeDAO.getTotalTimeInMillis()
    fun getTotalAvgSpeed() = hikeDAO.getTotalAvgSpeed()
    fun getTotalCaloriesBurnt() = hikeDAO.getTotalCaloriesBurnt()
    fun getTotalDistance() = hikeDAO.getTotalDistance()

}