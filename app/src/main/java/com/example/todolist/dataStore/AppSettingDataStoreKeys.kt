package com.example.todolist.dataStore

import androidx.datastore.preferences.core.booleanPreferencesKey

object AppSettingDataStoreKeys {
    val IS_NOTIFICATION_ON = booleanPreferencesKey("is_notification_on")

}
