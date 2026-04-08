package io.github.cadnunsdimir.android.meusgastosmobile.data.repository

import androidx.room.Dao
import io.github.cadnunsdimir.android.meusgastosmobile.data.db.AppDatabase
import io.github.cadnunsdimir.android.meusgastosmobile.data.entity.AccountMovement
import io.github.cadnunsdimir.android.meusgastosmobile.data.entity.BankAccount

@Dao
abstract class TransactionRepository(
    private val db: AppDatabase,
) {
    fun insertMovementAndUpdateAccount(
        movement: AccountMovement,
        account: BankAccount
    ) {
        val accountMovementRepository = db.accountMovement()
        val bankAccountRepository = db.bankAccount()

        db.runInTransaction {
            accountMovementRepository.insert(movement)
            bankAccountRepository.update(account)
        }
    }
}