package com.catnip.appfoodyl.datastore

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

val Context.appDataStore by preferencesDataStore(
    name = "App Food YL Datastore"
)