package ru.gozerov.presentation.screens.assembling.list.views

import android.annotation.SuppressLint
import androidx.annotation.StringRes
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ru.gozerov.domain.models.assembling.SimpleAssembling
import ru.gozerov.presentation.R
import ru.gozerov.presentation.ui.theme.RoboticsGuideTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AssemblingListContainer(
    parentPaddingValues: PaddingValues,
    searchFieldState: MutableState<String>,
    onSearchTextChanged: (text: String) -> Unit,
    onCardClick: (id: Int) -> Unit
) {
    val assemblings = listOf(SimpleAssembling(1, "Робот-панда"), SimpleAssembling(1, "Робот-панда"), SimpleAssembling(1, "Робот-панда"))
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = RoboticsGuideTheme.colors.primaryBackground
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            SearchField(
                textState = searchFieldState,
                onSearchTextChanged = onSearchTextChanged,
                hintStringRes = R.string.search
            )
            if (searchFieldState.value.isNotBlank())
                SearchAssemblingView(assemblings = assemblings, onCardClick = onCardClick)
            else
                AssemblingListView(
                    assemblings = assemblings,
                    parentPaddingValues = paddingValues,
                    onCardClick = onCardClick
                )

        }
    }

}

@Composable
fun SearchField(
    textState: MutableState<String>,
    onSearchTextChanged: (text: String) -> Unit,
    @StringRes hintStringRes: Int
) {
    OutlinedTextField(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .height(56.dp)
            .fillMaxWidth(),
        value = textState.value,
        onValueChange = {
            textState.value = it
            onSearchTextChanged(it)
        },
        leadingIcon = {
            Icon(
                modifier = Modifier.padding(start = 8.dp),
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        singleLine = true,
        textStyle = RoboticsGuideTheme.typography.body,
        placeholder = {
            Text(
                text = if (textState.value.isNotBlank()) "" else stringResource(id = hintStringRes),
            )
        },
        shape = CircleShape,
        colors = TextFieldDefaults.colors(
            focusedLabelColor = RoboticsGuideTheme.colors.tintColor,
            focusedContainerColor = RoboticsGuideTheme.colors.secondaryBackground,
            unfocusedContainerColor = RoboticsGuideTheme.colors.secondaryBackground,
            focusedIndicatorColor = RoboticsGuideTheme.colors.secondaryBackground,
            unfocusedIndicatorColor = RoboticsGuideTheme.colors.secondaryBackground,
            cursorColor = RoboticsGuideTheme.colors.tintColor,
            focusedTextColor = RoboticsGuideTheme.colors.primaryText,
            unfocusedLabelColor = RoboticsGuideTheme.colors.secondaryText
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )
}