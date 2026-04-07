package io.github.cadnunsdimir.android.meusgastosmobile.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import io.github.cadnunsdimir.android.meusgastosmobile.data.entity.AccountMovement
import io.github.cadnunsdimir.android.meusgastosmobile.data.entity.BankAccount
import io.github.cadnunsdimir.android.meusgastosmobile.data.entity.MovementCategory
import io.github.cadnunsdimir.android.meusgastosmobile.data.repository.AccountMovementRepository
import io.github.cadnunsdimir.android.meusgastosmobile.data.repository.BankAccountRepository
import io.github.cadnunsdimir.android.meusgastosmobile.data.repository.MovementCategoryRepository

@Database(
    entities = [AccountMovement::class, BankAccount::class, MovementCategory::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun bankAccount(): BankAccountRepository
    abstract fun accountMovement(): AccountMovementRepository
    abstract fun movementCategory(): MovementCategoryRepository

}