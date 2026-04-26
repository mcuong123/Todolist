package com.example.todolist.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class AppSettingImp(private val context: Context) : AppSetting {


    private val Context.dataStoreAppSetting: DataStore<Preferences> by preferencesDataStore(name = "app-setting-pref")


    override val appSettingFlow: Flow<AppSettingData>
        get() = context.dataStoreAppSetting.data.map { preferences ->
            AppSettingData(
                isNotificationOn = preferences[AppSettingDataStoreKeys.IS_NOTIFICATION_ON] ?: false
            )

        }

    override suspend fun getIsNotificationOn(): Boolean = withContext(Dispatchers.IO) {
        context.dataStoreAppSetting.data.map { preferences ->

            preferences[AppSettingDataStoreKeys.IS_NOTIFICATION_ON] ?: false

        }.first()

    }

    override suspend fun setIsNotificationOn(isNotificationOn: Boolean) =
        withContext(Dispatchers.IO) {
            context.dataStoreAppSetting.edit { preferences ->
                preferences[AppSettingDataStoreKeys.IS_NOTIFICATION_ON] = isNotificationOn
            }
            Unit
        }
}