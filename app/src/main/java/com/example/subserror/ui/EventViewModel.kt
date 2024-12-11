package com.example.subserror.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.subserror.data.EventRepository
import com.example.subserror.data.Result
import com.example.subserror.data.local.entity.EventEntity
import kotlinx.coroutines.launch

class EventViewModel(private val eventRepository: EventRepository) : ViewModel() {

    fun getDetail(id: String) = eventRepository.getDetailEvent(id)
    val activeEvents: LiveData<com.example.subserror.data.Result<List<EventEntity>>> = eventRepository.getEvents(true)
    val inactiveEvents: LiveData<Result<List<EventEntity>>> = eventRepository.getEvents(false)

    fun saveEvent(eventEntity: EventEntity) {
        viewModelScope.launch {
            eventRepository.setEventFavourite(eventEntity, true)
        }
    }

    fun deleteEvent(eventEntity: EventEntity) {
        viewModelScope.launch {
            eventRepository.setEventFavourite(eventEntity, false)
        }
    }

    fun getFavouriteEvent(): LiveData<List<EventEntity>> = eventRepository.getFavouriteEventList()

}