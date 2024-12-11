package com.example.subserror.di

import android.content.Context
import com.example.subserror.data.EventRepository
import com.example.subserror.data.local.room.FavouriteDatabase
import com.example.subserror.data.remote.retrofit.ApiConfig
import com.example.subserror.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): EventRepository {
        val apiService = ApiConfig.getApiService()
        val database = FavouriteDatabase.getInstance(context)
        val dao = database.favouriteDao()
        val appExecutors = AppExecutors()
        return EventRepository.getInstance(apiService, dao, appExecutors)
    }
}