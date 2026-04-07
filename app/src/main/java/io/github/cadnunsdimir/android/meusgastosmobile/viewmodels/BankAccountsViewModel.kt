package io.github.cadnunsdimir.android.meusgastosmobile.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import io.github.cadnunsdimir.android.meusgastosmobile.data.db.DatabaseProvider
import io.github.cadnunsdimir.android.meusgastosmobile.data.entity.BankAccount
import io.github.cadnunsdimir.android.meusgastosmobile.ui.dto.AccountDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.math.BigDecimal

class BankAccountsViewModel (application: Application): AndroidViewModel(application){
    val db = DatabaseProvider.getDatabase(application)
    val bankAccountRepository = db.bankAccount()

    val accounts = bankAccountRepository.getAll().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    private val _formState = MutableStateFlow(AccountDto())
    val formState: StateFlow<AccountDto> = _formState

    fun onNameChange(name: String) {
        _formState.value = _formState.value.copy(name = name)
    }

    fun onInitialBalanceChange(initialBalance: String) {
        _formState.value = _formState.value.copy(initialBalance = initialBalance)
    }

    fun addAccount(accountDto: AccountDto){
        viewModelScope.launch {
            val balance = BigDecimal(accountDto.initialBalance)
            val account = BankAccount(
                name = accountDto.name,
                currentBalance =  balance,
                initialBalance = balance)
            bankAccountRepository.insert(account)
        }
    }
}