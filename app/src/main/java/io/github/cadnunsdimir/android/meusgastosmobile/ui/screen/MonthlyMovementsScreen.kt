package io.github.cadnunsdimir.android.meusgastosmobile.ui.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import io.github.cadnunsdimir.android.meusgastosmobile.viewmodels.MonthlyAccountMovementsViewModel

@Composable
fun MonthlyMovementsScreen(
    viewModel: MonthlyAccountMovementsViewModel,
    navController: NavHostController
) {
    Button(
        onClick = {
            navController.navigate("accounts")
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Contas")
    }
}