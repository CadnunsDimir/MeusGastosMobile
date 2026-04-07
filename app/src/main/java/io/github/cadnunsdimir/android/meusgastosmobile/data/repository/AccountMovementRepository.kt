package io.github.cadnunsdimir.android.meusgastosmobile.data.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.github.cadnunsdimir.android.meusgastosmobile.data.entity.AccountMovement
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface AccountMovementRepository {
    @Insert
    suspend fun insert(bankAccount: AccountMovement): Long

    @Query("SELECT * FROM AccountMovement where date >= :beginDate and date < :endDate")
    fun getAllBetweenDates(beginDate: LocalDate, endDate: LocalDate): Flow<List<AccountMovement>>
}
