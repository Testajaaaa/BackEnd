package com.example.subserror.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.subserror.data.local.entity.EventEntity

@Dao
interface FavouriteDao {

    @Query("SELECT * FROM event_table WHERE isActive = :isActive")
    fun getEventsByStatus(isActive: Boolean): LiveData<List<EventEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNews(event: List<EventEntity>)

    @Query("SELECT * FROM event_table WHERE isActive = 1")
    fun getActiveEvent(): LiveData<List<EventEntity>>

    @Query("SELECT * FROM event_table WHERE isActive = 0")
    fun getInactiveEvent(): LiveData<List<EventEntity>>

    @Query("SELECT * FROM event_table WHERE id = :id")
    fun getDetail(id: String): LiveData<EventEntity>

    @Query("SELECT EXISTS(SELECT * FROM event_table WHERE name = :event AND isBookmarked = 1)")
    suspend fun isEventBookmarked(event: String?): Boolean

    @Query("SELECT * FROM event_table ORDER BY id DESC")
    fun getNews(): LiveData<List<EventEntity>>

    @Query("SELECT * FROM event_table where isBookmarked = 1")
    fun getBookmarkedNews(): LiveData<List<EventEntity>>

    @Update
    suspend fun updateEvent(event: EventEntity)

}