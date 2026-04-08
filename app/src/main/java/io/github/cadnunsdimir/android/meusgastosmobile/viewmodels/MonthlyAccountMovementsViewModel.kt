package io.github.cadnunsdimir.android.meusgastosmobile.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import io.github.cadnunsdimir.android.meusgastosmobile.data.db.DatabaseProvider
import io.github.cadnunsdimir.android.meusgastosmobile.data.entity.AccountMovement
import io.github.cadnunsdimir.android.meusgastosmobile.data.entity.MovementCategory
import io.github.cadnunsdimir.android.meusgastosmobile.ui.dto.MovementState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

class MonthlyAccountMovementsViewModel (application: Application): AndroidViewModel(application){
    private val _selectedDate = MutableStateFlow(
        LocalDate.now().withDayOfMonth(1)
    )

    val selectedDate: StateFlow<LocalDate> = _selectedDate

    //    val selectedMonth: LocalDate = LocalDate.now()
    private val db = DatabaseProvider.getDatabase(application)
    private val categoryRepository = db.movementCategory()
    private val accountMovementRepository = db.accountMovement()
    private val bankAccountRepository = db.bankAccount()
    private val transaction = db.transaction()
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
    @OptIn(ExperimentalCoroutinesApi::class)
    val movements: StateFlow<List<AccountMovement>> =
        selectedDate
            .flatMapLatest { date ->

                val beginDate = date.withDayOfMonth(1)
                val endDate = date.plusMonths(1)

                accountMovementRepository
                    .getAllBetweenDates(beginDate, endDate)
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )
    private val _formState = MutableStateFlow(MovementState())
    val formState: StateFlow<MovementState> = _formState

    fun addMovement(movementState: MovementState) {
        if(!formIsValid(movementState)) return
        val account = accounts.value.find { it.name == movementState.accountName }
        val category = categories.value.find { it.name == movementState.category }
        val movement = AccountMovement (
            date = LocalDate.of(_selectedDate.value.year, _selectedDate.value.month, movementState.dayOfMonth.toInt()),
            value = Formatters.fromBRLToDecimal(movementState.value),
            description = movementState.description.trim(),
            accountId = account?.id!!,
            categoryId = category?.id
        )
        account.currentBalance = account.currentBalance.add(movement.value)

        viewModelScope.launch(Dispatchers.IO)  {
            transaction.insertMovementAndUpdateAccount(movement, account)
            withContext(Dispatchers.Main) {
                clearForm()
            }
        }
    }

    private fun formIsValid(movementState: MovementState) =
        movementState.dayOfMonth.isNotBlank() &&
        movementState.accountName.isNotBlank() &&
        movementState.value.isNotBlank() &&
        movementState.description.isNotBlank()

    fun addCategory(name: String){
        viewModelScope.launch(Dispatchers.IO)  {
            val category = MovementCategory(name = name)
            categoryRepository.insert(category)
        }
    }

    fun onDayOfMonthChange(day: String) {
        _formState.value = _formState.value.copy(dayOfMonth = day)
    }

    fun onValueChange(value: String) {
        _formState.value = _formState.value.copy(value = value)
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

    fun changeMonth(month: Int, year: Int) {
        _selectedDate.value = LocalDate.of(year, month, 1)
    }
}