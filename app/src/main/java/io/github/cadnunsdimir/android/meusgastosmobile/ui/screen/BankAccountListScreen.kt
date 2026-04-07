package io.github.cadnunsdimir.android.meusgastosmobile.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import io.github.cadnunsdimir.android.meusgastosmobile.viewmodels.BankAccountsViewModel

@Composable
fun BankAccountListScreen(
    viewModel: BankAccountsViewModel = viewModel(),
    navController: NavHostController
) {
    val accounts = viewModel.accounts.collectAsStateWithLifecycle()
    val form = viewModel.formState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.padding(16.dp).fillMaxSize()
    ) {
        Text("Cadastrar Conta", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = form.value.name,
            onValueChange = viewModel::onNameChange,
            label = { Text("Nome da Conta") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(4.dp))

        OutlinedTextField(
            value = form.value.initialBalance,
            onValueChange = viewModel::onInitialBalanceChange,
            label = { Text("Saldo inicial") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(4.dp))

        Button(
            onClick = {
                if(form.value.name.isNotBlank() && form.value.initialBalance.isNotBlank()) {
                    viewModel.addAccount(form.value)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cadastrar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Contas já cadastradas", style = MaterialTheme.typography.titleLarge)
        LazyColumn() {
            items(accounts.value) { account ->
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            //TODO: editar
                        }
                ) {
                    Text("Id: ${account.id}", style = MaterialTheme.typography.titleMedium)
                    Text("Nome: ${account.name}", style = MaterialTheme.typography.titleMedium)
                    Text("Saldo Atual: ${account.currentBalance}", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}
