package com.example.subserror.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.example.subserror.data.local.entity.EventEntity
import com.example.subserror.data.local.room.FavouriteDao
import com.example.subserror.data.remote.response.ListEventsItem
import com.example.subserror.data.remote.retrofit.ApiService
import com.example.subserror.utils.AppExecutors

class EventRepository private constructor(
    private val apiService: ApiService,
    private val favouriteDao: FavouriteDao,
    private val appExecutors: AppExecutors
) {

    fun getEvents(isActive: Boolean): LiveData<Result<List<EventEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val response = if (isActive) apiService.getUpcomingEvents() else apiService.getFinishedEvents()
            val events = response.listEvents
            val eventList = events?.map { event ->
                val isBookmarked = favouriteDao.isEventBookmarked(event.name)
                EventEntity(
                    event.id.toString(),
                    event.name!!,
                    event.mediaCover,
                    event.imageLogo,
                    event.description,
                    event.ownerName,
                    event.cityName,
                    event.quota,
                    event.registrants,
                    event.link,
                    event.beginTime,
                    isBookmarked,
                    isActive
                )
            }
            favouriteDao.insertNews(eventList!!)
        } catch (e: Exception) {
            Log.d("Event Repository", "getEvents: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }

        val localData: LiveData<Result<List<EventEntity>>> =
            favouriteDao.getEventsByStatus(isActive).map { Result.Success(it) }
        emitSource(localData)
    }


    fun getDetailEvent(id: String): LiveData<EventEntity>{
        return favouriteDao.getDetail(id)
    }

    suspend fun setEventFavourite(eventEntity: EventEntity, isFavourite: Boolean) {
        eventEntity.isBookmarked = isFavourite
        favouriteDao.updateEvent(eventEntity)
    }

    fun getFavouriteEventList(): LiveData<List<EventEntity>>{
        return favouriteDao.getBookmarkedNews()
    }


    companion object {
        @Volatile
        private var instance: EventRepository? = null
        fun getInstance(
            apiService: ApiService,
            favouriteDao: FavouriteDao,
            appExecutors: AppExecutors
        ): EventRepository =
            instance ?: synchronized(this) {
                instance ?: EventRepository(apiService, favouriteDao, appExecutors)
            }.also { instance = it }
    }

}