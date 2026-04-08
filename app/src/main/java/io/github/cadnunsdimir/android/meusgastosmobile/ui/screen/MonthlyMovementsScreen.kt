package io.github.cadnunsdimir.android.meusgastosmobile.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import io.github.cadnunsdimir.android.meusgastosmobile.ui.components.Button
import io.github.cadnunsdimir.android.meusgastosmobile.ui.components.MonthSelector
import io.github.cadnunsdimir.android.meusgastosmobile.ui.components.MovementCard
import io.github.cadnunsdimir.android.meusgastosmobile.ui.components.MovementFormModal
import io.github.cadnunsdimir.android.meusgastosmobile.viewmodels.MonthlyAccountMovementsViewModel

@Composable
fun MonthlyMovementsScreen(
    viewModel: MonthlyAccountMovementsViewModel,
    navController: NavHostController
) {
    val movements = viewModel.movements.collectAsStateWithLifecycle()
    val currentMonth = viewModel.selectedDate.collectAsStateWithLifecycle()
    var showSheet by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Row(modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)) {
            Text("Movimentações",
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.titleLarge)
            MonthSelector(
                month = currentMonth.value.month.value,
                year = currentMonth.value.year,
                onSelect = { month, year ->
                    viewModel.changeMonth(month, year)
                })
        }

        Button("Contas", Modifier.fillMaxWidth()) {
            navController.navigate("accounts")
        }

        Button("Registrar Movimentação", Modifier.fillMaxWidth()) {
            showSheet = true
        }

        MovementFormModal(viewModel, showSheet) {
            showSheet = false
        }

        LazyColumn {
            items(movements.value) { movement ->
                MovementCard(movement)
            }
        }
    }

}