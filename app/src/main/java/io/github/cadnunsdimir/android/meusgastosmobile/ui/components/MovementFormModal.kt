package io.github.cadnunsdimir.android.meusgastosmobile.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
    val focusManager = LocalFocusManager.current
    val sheetState = rememberModalBottomSheetState()
    val form = viewModel.formState.collectAsStateWithLifecycle()
    val categories = viewModel.categories.collectAsStateWithLifecycle()
    val accounts = viewModel.accounts.collectAsStateWithLifecycle()
    var showCategoryForm by remember { mutableStateOf(false) }

    val accountList = accounts.value.map { it.name }
    val categoriesList = categories.value.map { it.name }

    val onSubmit: () -> Unit = {
        viewModel.addMovement(form.value)
        onDismissRequest()
    }

    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth()
                    .padding(16.dp)
            ){

                Text("Cadastrar Movimentação", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = form.value.dayOfMonth,
                    onValueChange = viewModel::onDayOfMonthChange,
                    label = { Text("Dia") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number,
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
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))

                OutlinedTextField(
                    value = form.value.value,
                    onValueChange = viewModel::onValueChange,
                    label = { Text("Valor") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Decimal
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
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    if (!showCategoryForm) {
                        DropDownListField(
                            label = "Categoria",
                            selectedOption = form.value.category,
                            options = categoriesList,
                            onChange = {
                                viewModel.onCategoryChange(it)
                            },
                            modifier = Modifier.weight(7f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Button("+",
                            modifier = Modifier.weight(1f).fillMaxHeight()) {
                            showCategoryForm = true
                        }
                    } else {
                        OutlinedTextField(
                            value = form.value.category,
                            onValueChange = viewModel::onCategoryChange,
                            label = { Text("Nova Categoria") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    viewModel.addCategory(form.value.category)
                                    showCategoryForm = false
                                }
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))
                Button(
                    "Cadastrar",
                    modifier = Modifier.fillMaxWidth()
                ) {
                    onSubmit()
                }
            }
        }
    }
}