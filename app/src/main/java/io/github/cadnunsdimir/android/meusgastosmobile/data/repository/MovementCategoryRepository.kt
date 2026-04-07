package io.github.cadnunsdimir.android.meusgastosmobile.data.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.github.cadnunsdimir.android.meusgastosmobile.data.entity.MovementCategory
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface MovementCategoryRepository {
    @Insert
    suspend fun insert(entity: MovementCategory): Long

    @Query("SELECT * FROM MovementCategory")
    fun getAll(): Flow<List<MovementCategory>>

    @Query("SELECT id FROM MovementCategory WHERE name = :name")
    fun getIdByName(name: String): Flow<UUID>
}