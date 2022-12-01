package ru.irinavb.augmentedhikes.services

import android.content.Intent
import android.util.Log
import androidx.lifecycle.LifecycleService
import ru.irinavb.augmentedhikes.utils.Constants.ACTION_PAUSE_SERVICE
import ru.irinavb.augmentedhikes.utils.Constants.ACTION_START_OR_RESUME_SERVICE
import ru.irinavb.augmentedhikes.utils.Constants.ACTION_STOP_SERVICE

private const val TAG = "TrackingService"

class TrackingService : LifecycleService() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when (it.action) {
                ACTION_START_OR_RESUME_SERVICE -> {
                    Log.d(TAG, "onStartCommand: started or resumed service")
                }
                ACTION_PAUSE_SERVICE -> {
                    Log.d(TAG, "onStartCommand: paused service")
                }
                ACTION_STOP_SERVICE -> {
                    Log.d(TAG, "onStartCommand: stopped service")
                }
                else -> {}
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }
}