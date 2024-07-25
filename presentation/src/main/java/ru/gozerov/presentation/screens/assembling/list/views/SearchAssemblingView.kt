package ru.gozerov.presentation.screens.assembling.list.views

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.gozerov.domain.models.assembling.SimpleAssembling
import ru.gozerov.presentation.ui.theme.RoboticsGuideTheme

@Composable
fun SearchAssemblingView(
    assemblings: List<SimpleAssembling>,
    categories: List<String>,
    currentCategory: MutableState<String>,
    onCardClick: (id: Int) -> Unit,
    onCategoryChanged: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val menuInteractionSource = remember { MutableInteractionSource() }
    Column(
        modifier = Modifier
            .animateContentSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                .clickable(menuInteractionSource, null) {
                    expanded = !expanded
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = currentCategory.value,
                color = RoboticsGuideTheme.colors.outline
            )
            Icon(
                modifier = Modifier.padding(start = 16.dp),
                imageVector = Icons.Default.ArrowDropDown,
                tint = RoboticsGuideTheme.colors.outline,
                contentDescription = null
            )
        }
        if (expanded)
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .background(
                        color = RoboticsGuideTheme.colors.secondaryContainer,
                        RoundedCornerShape(8.dp)
                    )
            ) {
                categories.forEachIndexed { ind, value ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(MutableInteractionSource(), null) {
                                currentCategory.value = value
                                expanded = false
                                onCategoryChanged()
                            }
                    ) {
                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = value,
                            color = RoboticsGuideTheme.colors.secondary
                        )
                    }
                    if (ind < categories.lastIndex)
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = RoboticsGuideTheme.colors.dividerColor
                        )
                }
            }
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp)
        ) {
            items(assemblings.size) { ind ->
                AssemblingCard(
                    assembling = assemblings[ind],
                    onCardClick = onCardClick
                )
            }
        }
    }
}