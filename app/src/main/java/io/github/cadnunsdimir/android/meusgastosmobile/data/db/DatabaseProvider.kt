package io.github.cadnunsdimir.android.meusgastosmobile.data.db


import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room
                .databaseBuilder<AppDatabase>(
                    context,
                    "meus_gastos_mobile_db")
                .build()

            INSTANCE = instance
            instance
        }
    }
}