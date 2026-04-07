package io.github.cadnunsdimir.android.meusgastosmobile.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import io.github.cadnunsdimir.android.meusgastosmobile.data.db.DatabaseProvider
import io.github.cadnunsdimir.android.meusgastosmobile.data.entity.BankAccount
import io.github.cadnunsdimir.android.meusgastosmobile.ui.dto.AccountState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class BankAccountsViewModel (application: Application): AndroidViewModel(application){
    private val db = DatabaseProvider.getDatabase(application)
    private val bankAccountRepository = db.bankAccount()
    val accounts = bankAccountRepository.getAll().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    private val _formState = MutableStateFlow(AccountState())
    val formState: StateFlow<AccountState> = _formState

    fun onNameChange(name: String) {
        _formState.value = _formState.value.copy(name = name)
    }

    fun onInitialBalanceChange(initialBalance: String) {
        _formState.value = _formState.value.copy(initialBalance = initialBalance)
    }

    fun addAccount(accountDto: AccountState){
        viewModelScope.launch(Dispatchers.IO) {
            val balance = Formatters.fromBRLToDecimal(accountDto.initialBalance)
            val account = BankAccount(
                name = accountDto.name,
                currentBalance =  balance,
                initialBalance = balance)
            bankAccountRepository.insert(account)
            withContext(Dispatchers.Main) {
                clearForm()
            }
        }
    }


    private fun clearForm() {
        _formState.value =AccountState()
    }

    fun formIsValid(value: AccountState): Boolean {
        return value.name.isNotBlank() && value.initialBalance.isNotBlank()
    }

    fun removeAccount(id: UUID) {
        viewModelScope.launch(Dispatchers.IO) {
            bankAccountRepository.remove(id)
        }
    }
}