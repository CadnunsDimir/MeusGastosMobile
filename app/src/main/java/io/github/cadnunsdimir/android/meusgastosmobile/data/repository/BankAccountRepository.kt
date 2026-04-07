package io.github.cadnunsdimir.android.meusgastosmobile.data.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import io.github.cadnunsdimir.android.meusgastosmobile.data.entity.BankAccount
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface BankAccountRepository {
    @Insert
    suspend fun insert(bankAccount: BankAccount): Long

    @Update
    fun update(account: BankAccount)

    @Query("SELECT * FROM BankAccount")
    fun getAll(): Flow<List<BankAccount>>

    @Query("SELECT * FROM BankAccount WHERE id = :bankAccountId")
    fun getById(bankAccountId: UUID): Flow<BankAccount>

    @Query("DELETE FROM BankAccount WHERE id = :id")
    fun remove(id: UUID)
}
