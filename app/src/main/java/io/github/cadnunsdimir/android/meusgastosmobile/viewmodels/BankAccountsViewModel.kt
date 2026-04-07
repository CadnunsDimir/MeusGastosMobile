package io.github.cadnunsdimir.android.meusgastosmobile.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import io.github.cadnunsdimir.android.meusgastosmobile.data.db.DatabaseProvider
import io.github.cadnunsdimir.android.meusgastosmobile.data.entity.BankAccount
import io.github.cadnunsdimir.android.meusgastosmobile.ui.dto.AccountDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale
import java.util.UUID

class BankAccountsViewModel (application: Application): AndroidViewModel(application){
    val db = DatabaseProvider.getDatabase(application)
    val bankAccountRepository = db.bankAccount()
    val locale = Locale("pt", "BR")

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
        viewModelScope.launch(Dispatchers.IO) {
            val balance = fromBRLToDecimal(accountDto.initialBalance)
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

    private fun fromBRLToDecimal(value: String): BigDecimal{
        val format = NumberFormat.getInstance(locale)
        val number = format.parse(value)
        return BigDecimal(number?.toString())
    }

    private fun clearForm() {
        _formState.value =AccountDto()
    }

    fun formatBRL(value: BigDecimal): String {
        val brlCurrencyFormatter: NumberFormat = NumberFormat.getCurrencyInstance(locale)
        return brlCurrencyFormatter.format(value)
    }

    fun formIsValid(value: AccountDto): Boolean {
        return value.name.isNotBlank() && value.initialBalance.isNotBlank()
    }

    fun removeAccount(id: UUID) {
        viewModelScope.launch(Dispatchers.IO) {
            bankAccountRepository.remove(id)
        }
    }
}