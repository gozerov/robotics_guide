package ru.gozerov.presentation.screens.assembling.list.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.gozerov.domain.models.assembling.SimpleAssembling

@Composable
fun SearchAssemblingView(
    assemblings: List<SimpleAssembling>,
    onCardClick: (id: Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val list = listOf("Популярные", "Новые")
    var selected by remember { mutableStateOf(list[0]) }
    Column {
        Spacer(
            modifier = Modifier
                .height(16.dp)
                .fillMaxWidth()
                .background(Color.Transparent)
                .padding(10.dp)
                .clickable(
                    onClick = { expanded = !expanded }
                )
        )
        DropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            list.forEach { entry ->

                DropdownMenuItem(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        selected = entry
                        expanded = false
                    },
                    text = {
                        Text(
                            text = (entry),
                            modifier = Modifier.wrapContentWidth().align(Alignment.Start))
                    }
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(assemblings.size) {
                AssemblingCard(
                    assembling = assemblings[it],
                    onCardClick = onCardClick
                )
            }
        }
    }
}