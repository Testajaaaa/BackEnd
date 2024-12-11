package com.example.subserror.utils

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutors {
    val diskIO : Executor = Executors.newSingleThreadExecutor()
    val networkI: Executor = Executors.newFixedThreadPool(3)
    val mainThread: Executor = MainThreadExceutor()

    private class MainThreadExceutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}