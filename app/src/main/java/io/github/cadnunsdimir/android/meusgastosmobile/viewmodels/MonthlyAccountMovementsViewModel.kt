package io.github.cadnunsdimir.android.meusgastosmobile.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import io.github.cadnunsdimir.android.meusgastosmobile.data.db.DatabaseProvider
import io.github.cadnunsdimir.android.meusgastosmobile.data.entity.AccountMovement
import io.github.cadnunsdimir.android.meusgastosmobile.data.entity.MovementCategory
import io.github.cadnunsdimir.android.meusgastosmobile.ui.dto.MovementState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

class MonthlyAccountMovementsViewModel (application: Application): AndroidViewModel(application){
    val selectedMonth = LocalDate.now()
    private val db = DatabaseProvider.getDatabase(application)
    private val categoryRepository = db.movementCategory()
    private val accountMovementRepository = db.accountMovement()
    private val bankAccountRepository = db.bankAccount()
    val accounts = bankAccountRepository.getAll().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )
    val categories = categoryRepository.getAll().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )
    val movements = getMovements(selectedMonth)
    private val _formState = MutableStateFlow(MovementState())
    val formState: StateFlow<MovementState> = _formState

    private fun getMovements(currentDate: LocalDate) : StateFlow<List<AccountMovement>> {
        val beginDate = currentDate.withDayOfMonth(1)
        val endDate  = beginDate.withMonth(beginDate.month.value + 1)

        return accountMovementRepository.getAllBetweenDates(beginDate, endDate).stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )
    }

    fun addMovement(movementState: MovementState) {
        val account = accounts.value.find { it.name == movementState.accountName }
        val category = categories.value.find { it.name == movementState.category }
        val movement = AccountMovement (
            date = LocalDate.of(selectedMonth.year, selectedMonth.month, movementState.dayOfMonth),
            value = Formatters.fromBRLToDecimal(movementState.value),
            description = movementState.description.trim(),
            accountId = account?.id!!,
            categoryId = category?.id
        )
        account.currentBalance = account.currentBalance.add(movement.value)
        viewModelScope.launch {
            accountMovementRepository.insert(movement)
            bankAccountRepository.update(account)
            withContext(Dispatchers.Main) {
                clearForm()
            }
        }
    }

    fun addCategory(name: String){
        viewModelScope.launch {
            val category = MovementCategory(name = name)
            categoryRepository.insert(category)
        }
    }

    fun onDayOfMonthChange(day: String) {
        _formState.value = _formState.value.copy(dayOfMonth = day.toInt())
    }

    fun onDescriptionChange(description: String) {
        _formState.value = _formState.value.copy(description = description)
    }

    fun onAccountNameChange(accountName: String) {
        _formState.value = _formState.value.copy(accountName = accountName)
    }

    fun onCategoryChange(category: String) {
        _formState.value = _formState.value.copy(category = category)
    }
    private fun clearForm() {
        _formState.value = MovementState()
    }
}