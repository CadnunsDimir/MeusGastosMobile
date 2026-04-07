package io.github.cadnunsdimir.android.meusgastosmobile.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import io.github.cadnunsdimir.android.meusgastosmobile.data.db.DatabaseProvider
import io.github.cadnunsdimir.android.meusgastosmobile.data.entity.AccountMovement
import io.github.cadnunsdimir.android.meusgastosmobile.data.entity.MovementCategory
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

class MonthlyAccountMovementsViewModel (application: Application): AndroidViewModel(application){
    val db = DatabaseProvider.getDatabase(application)
    val categoryRepository = db.movementCategory()
    val accountMovementRepository = db.accountMovement()

    val categories = categoryRepository.getAll().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    val movements = getMovements(LocalDate.now())

    private fun getMovements(currentDate: LocalDate) : StateFlow<List<AccountMovement>> {
        val beginDate = currentDate.withDayOfMonth(1)
        val endDate  = beginDate.withMonth(beginDate.month.value + 1)

        return accountMovementRepository.getAllBetweenDates(beginDate, endDate).stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )
    }

    fun addCategory(name: String){
        viewModelScope.launch {
            val category = MovementCategory(name = name)
            categoryRepository.insert(category)
        }
    }
}