package io.github.cadnunsdimir.android.meusgastosmobile.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import io.github.cadnunsdimir.android.meusgastosmobile.ui.components.Button
import io.github.cadnunsdimir.android.meusgastosmobile.ui.components.MovementFormModal
import io.github.cadnunsdimir.android.meusgastosmobile.viewmodels.Formatters
import io.github.cadnunsdimir.android.meusgastosmobile.viewmodels.MonthlyAccountMovementsViewModel
import java.math.BigDecimal

@Composable
fun MonthlyMovementsScreen(
    viewModel: MonthlyAccountMovementsViewModel,
    navController: NavHostController
) {
    val movements = viewModel.movements.collectAsStateWithLifecycle()
    var showSheet by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Movimentações", style = MaterialTheme.typography.titleLarge)

        Button("Contas") {
            navController.navigate("accounts")
        }

        Button("Registrar Movimentação") {
            showSheet = true
        }

        MovementFormModal(viewModel, showSheet) {
            showSheet = false
        }

        LazyColumn() {
            items(movements.value) { movement ->
                val color = if(movement.value < BigDecimal.ZERO) Color.Red else Color.Green
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            //editar
                        }.background(color)
                ) {
                    Text("${ movement.date } ${movement.description}", style = MaterialTheme.typography.titleMedium)
                    Text(Formatters.formatBRL(movement.value), style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }

}