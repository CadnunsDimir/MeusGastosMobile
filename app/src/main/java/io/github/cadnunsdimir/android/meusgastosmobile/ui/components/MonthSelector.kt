package io.github.cadnunsdimir.android.meusgastosmobile.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier



@Composable
fun MonthSelector(modifier: Modifier = Modifier, month: Int, year: Int, onSelect: (month: Int, year: Int)-> Unit){

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        IconButton(onClick = {
            var newMonth = month - 1
            var newYear = year
            if (newMonth == 0) {
                newYear -= 1
                newMonth = 12
            }
            onSelect(newMonth, newYear)
        }) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
        }

        Text("${month} / ${year}")

        IconButton(onClick = {
            var newMonth = month + 1
            var newYear = year
            if (newMonth == 13) {
                newYear += 1
                newMonth = 1
            }
            onSelect(newMonth, newYear)
        }) {
            Icon(Icons.AutoMirrored.Filled.ArrowForward, null)
        }
    }
}