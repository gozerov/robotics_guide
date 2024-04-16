package ru.gozerov.presentation.screens.assembling.list.views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.gozerov.domain.models.assembling.SimpleAssembling
import ru.gozerov.presentation.R
import ru.gozerov.presentation.ui.theme.RoboticsGuideTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AssemblingListView(
    newAssemblings: List<SimpleAssembling>,
    allAssemblings: List<SimpleAssembling>,
    parentPaddingValues: PaddingValues,
    onCardClick: (id: Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
            text = stringResource(id = R.string.new_assemblings)
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(newAssemblings.size) {
                SmallAssemblingCard(
                    assembling = newAssemblings[it],
                    onCardClick = onCardClick
                )
            }
        }
        Divider(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
            color = RoboticsGuideTheme.colors.secondaryBackground
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(allAssemblings.size) {
                AssemblingCard(
                    assembling = allAssemblings[it],
                    onCardClick = onCardClick
                )
            }
        }
    }

}