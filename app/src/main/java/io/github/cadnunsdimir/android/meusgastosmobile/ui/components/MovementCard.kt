package io.github.cadnunsdimir.android.meusgastosmobile.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.cadnunsdimir.android.meusgastosmobile.data.entity.AccountMovement
import io.github.cadnunsdimir.android.meusgastosmobile.viewmodels.Formatters
import java.math.BigDecimal

@Composable
fun MovementCard(movement: AccountMovement) {
    val color = if(movement.value < BigDecimal.ZERO) Color.Red else Color.Green
    Card(
        modifier = Modifier.fillMaxWidth()
            .padding(top= 8.dp),
        border = BorderStroke(1.dp, color),
        onClick = {
            //editar
        }) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("${ movement.date } ${movement.description}", style = MaterialTheme.typography.titleLarge)
            Text(Formatters.formatBRL(movement.value), style = MaterialTheme.typography.bodyLarge)
        }
    }
}