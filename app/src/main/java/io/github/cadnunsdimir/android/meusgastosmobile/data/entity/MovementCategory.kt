package io.github.cadnunsdimir.android.meusgastosmobile.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class MovementCategory(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    val name: String
)
