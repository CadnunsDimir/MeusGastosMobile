package io.github.cadnunsdimir.android.meusgastosmobile.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.util.UUID

@Entity
data class BankAccount(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val initialBalance: BigDecimal,
    var currentBalance: BigDecimal
)

