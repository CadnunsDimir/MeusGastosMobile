package io.github.cadnunsdimir.android.meusgastosmobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.cadnunsdimir.android.meusgastosmobile.ui.screen.BankAccountListScreen
import io.github.cadnunsdimir.android.meusgastosmobile.ui.screen.MonthlyMovementsScreen
import io.github.cadnunsdimir.android.meusgastosmobile.ui.screen.MovementFormScreen
import io.github.cadnunsdimir.android.meusgastosmobile.ui.theme.MeusGastosMobileTheme
import io.github.cadnunsdimir.android.meusgastosmobile.viewmodels.MonthlyAccountMovementsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MeusGastosMobileTheme {
                Surface(
                    modifier = Modifier.padding(top = 36.dp)
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        AppNavigation()
                    }
                }
            }
        }
    }

    @Composable
    private fun AppNavigation() {
        val navController = rememberNavController()
        val viewModel: MonthlyAccountMovementsViewModel = viewModel()

        NavHost(
            navController = navController,
            startDestination = "monthly_movements"
        ) {
            composable("monthly_movements") {
                MonthlyMovementsScreen(viewModel = viewModel, navController = navController)
            }
            composable("accounts") {
                BankAccountListScreen(navController = navController)
            }
            composable("movements/add") { backStackEntry ->
                MovementFormScreen(navController = navController, viewModel = viewModel)
            }
        }
    }




}