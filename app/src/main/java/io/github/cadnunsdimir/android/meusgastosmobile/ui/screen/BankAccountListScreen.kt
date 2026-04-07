package io.github.cadnunsdimir.android.meusgastosmobile.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import io.github.cadnunsdimir.android.meusgastosmobile.ui.components.BankAccountForm
import io.github.cadnunsdimir.android.meusgastosmobile.viewmodels.BankAccountsViewModel
import io.github.cadnunsdimir.android.meusgastosmobile.viewmodels.Formatters

@Composable
fun BankAccountListScreen(
    viewModel: BankAccountsViewModel = viewModel(),
    navController: NavHostController
) {
    val accounts = viewModel.accounts.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        BankAccountForm(viewModel, navController)

        Spacer(modifier = Modifier.height(16.dp))

        Text("Contas já cadastradas", style = MaterialTheme.typography.titleLarge)
        LazyColumn() {
            items(accounts.value) { account ->
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            viewModel.removeAccount(account.id)
                        }
                ) {
                    Text("Id: ${account.id}", style = MaterialTheme.typography.titleMedium)
                    Text("Nome: ${account.name}", style = MaterialTheme.typography.titleMedium)
                    Text("Saldo Atual: ${Formatters.formatBRL(account.currentBalance)}", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}
