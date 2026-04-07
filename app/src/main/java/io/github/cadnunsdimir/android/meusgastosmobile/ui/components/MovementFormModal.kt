package io.github.cadnunsdimir.android.meusgastosmobile.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.cadnunsdimir.android.meusgastosmobile.viewmodels.MonthlyAccountMovementsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovementFormModal(
    viewModel: MonthlyAccountMovementsViewModel,
    showSheet: Boolean,
    onDismissRequest: () -> Unit
) {
    val form = viewModel.formState.collectAsStateWithLifecycle()
    val categories = viewModel.categories.collectAsStateWithLifecycle()
    val accounts = viewModel.accounts.collectAsStateWithLifecycle()
    val accountList = accounts.value.map { it.name }
    val categoriesList = categories.value.map { it.name }
    val sheetState = rememberModalBottomSheetState()
    val focusManager = LocalFocusManager.current
    val onSubmit: () -> Unit = {
        //definir
    }

    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ){

                Text("Cadastrar Movimentação", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = "${form.value.dayOfMonth}",
                    onValueChange = viewModel::onDayOfMonthChange,
                    label = { Text("Dia") },
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
                    value = form.value.description,
                    onValueChange = viewModel::onDescriptionChange,
                    label = { Text("Descrição") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))

                DropDownListField(
                    label = "Conta",
                    selectedOption = form.value.accountName,
                    options = accountList,
                    onChange = {
                        viewModel.onAccountNameChange(it)
                    }
                )
                Spacer(modifier = Modifier.height(4.dp))
                DropDownListField(
                    label = "Categoria",
                    selectedOption = form.value.category,
                    options = categoriesList,
                    onChange = {
                        viewModel.onCategoryChange(it)
                    }
                )
                Spacer(modifier = Modifier.height(4.dp))
                Spacer(modifier = Modifier.height(4.dp))
                Button(
                    "Cadastrar"
                ) {
                    onSubmit()
                }
            }
        }
    }
}