package io.github.cadnunsdimir.android.meusgastosmobile.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import io.github.cadnunsdimir.android.meusgastosmobile.viewmodels.BankAccountsViewModel

@Composable
fun BankAccountForm(
    viewModel: BankAccountsViewModel,
    navController: NavHostController
) {
    val form = viewModel.formState.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current
    val onSubmit: () -> Unit = {
        if(viewModel.formIsValid(form.value)) {
            viewModel.addAccount(form.value)
            focusManager.clearFocus()
        }
    }

    Text("Cadastrar Conta", style = MaterialTheme.typography.titleLarge)
    Spacer(modifier = Modifier.height(8.dp))

    OutlinedTextField(
        value = form.value.name,
        onValueChange = viewModel::onNameChange,
        label = { Text("Nome da Conta") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        )
    )
    Spacer(modifier = Modifier.height(4.dp))

    OutlinedTextField(
        value = form.value.initialBalance,
        onValueChange = viewModel::onInitialBalanceChange,
        label = { Text("Saldo inicial") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { onSubmit() }
        )
    )
    Spacer(modifier = Modifier.height(4.dp))

    Button(
        onClick = { onSubmit() },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Cadastrar")
    }
}
