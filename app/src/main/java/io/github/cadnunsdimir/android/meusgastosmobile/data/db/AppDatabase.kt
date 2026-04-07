package io.github.cadnunsdimir.android.meusgastosmobile.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.github.cadnunsdimir.android.meusgastosmobile.data.db.converter.BigDecimalConverter
import io.github.cadnunsdimir.android.meusgastosmobile.data.db.converter.LocalDateConverter
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
@TypeConverters(value =
    [ BigDecimalConverter::class, LocalDateConverter::class ])
abstract class AppDatabase : RoomDatabase(){
    abstract fun bankAccount(): BankAccountRepository
    abstract fun accountMovement(): AccountMovementRepository
    abstract fun movementCategory(): MovementCategoryRepository

}