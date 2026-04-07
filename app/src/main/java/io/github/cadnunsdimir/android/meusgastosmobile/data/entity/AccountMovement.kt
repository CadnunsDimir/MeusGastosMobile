package io.github.cadnunsdimir.android.meusgastosmobile.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = BankAccount::class,
            parentColumns = ["id"],
            childColumns = ["accountId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = MovementCategory::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class AccountMovement(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    val date: LocalDate,
    val value: BigDecimal,
    val accountId: UUID,
    val description: String,
    val categoryId: UUID?)
