package io.github.cadnunsdimir.android.meusgastosmobile.ui.dto

import java.time.LocalDate

data class MovementState (
    val dayOfMonth: String = LocalDate.now().dayOfMonth.toString(),
    val value: String = "",
    val accountName: String = "",
    val description: String = "",
    val category: String = ""
)
