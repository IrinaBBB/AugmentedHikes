package ru.irinavb.augmentedhikes.utils

import android.graphics.Color

object Constants {

    // Db
    const val HIKES_DATABASE_NAME = "hikes_db"

    // Shared Preferences
    const val OSM_PREFERENCES = "osm_pref"

    // Permissions
    const val REQUEST_CODE_LOCATION_PERMISSION = 0
    const val REQUEST_CODE_BACKGROUND_LOCATION_PERMISSION = 1

    // Service
    const val ACTION_START_OR_RESUME_SERVICE = "ACTION_START_OR_RESUME_SERVICE"
    const val ACTION_PAUSE_SERVICE = "ACTION_PAUSE_SERVICE"
    const val ACTION_STOP_SERVICE = "ACTION_STOP_SERVICE"
    const val ACTION_SHOW_TRACKING_FRAGMENT = "ACTION_SHOW_TRACKING_FRAGMENT"

    // Update Intervals
    const val LOCATION_UPDATE_INTERVAL = 5000L
    const val FASTEST_LOCATION_INTERVAL = 2000L

    // Notification
    const val NOTIFICATION_CHANNEL_ID = "tracking_channel"
    const val NOTIFICATION_CHANNEL_NAME = "Tracking"
    const val NOTIFICATION_ID = 1 // should not be 0

    // Polyline Options
    const val POLYLINE_COLOR = Color.RED
    const val POLYLINE_WIDTH = 8f

    const val MAP_ZOOM = 15f
}
