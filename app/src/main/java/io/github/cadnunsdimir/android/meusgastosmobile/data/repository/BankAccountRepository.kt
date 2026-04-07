package io.github.cadnunsdimir.android.meusgastosmobile.data.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

import io.github.cadnunsdimir.android.meusgastosmobile.data.entity.BankAccount
import kotlinx.coroutines.flow.Flow

@Dao
interface BankAccountRepository {
    @Insert
    suspend fun insert(bankAccount: BankAccount): Long

    @Query("SELECT * FROM BankAccount")
    fun getAll(): Flow<List<BankAccount>>

    @Query("SELECT * FROM BankAccount WHERE id = :bankAccountId")
    fun getById(bankAccountId: Int): Flow<BankAccount>

}
