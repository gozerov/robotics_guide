package ru.gozerov.presentation.screens.assembling.list.views

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ru.gozerov.domain.models.assembling.SimpleAssembling
import ru.gozerov.presentation.R
import ru.gozerov.presentation.ui.theme.RoboticsGuideTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AssemblingListContainer(
    newAssemblings: List<SimpleAssembling>,
    allAssemblings: List<SimpleAssembling>,
    searchedAssemblings: List<SimpleAssembling>,
    categories: List<String>,
    currentCategory: MutableState<String>,
    parentPaddingValues: PaddingValues,
    searchFieldState: MutableState<String>,
    onSearchTextChanged: (text: String) -> Unit,
    onCardClick: (id: Int) -> Unit,
    onCategoryChanged: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = RoboticsGuideTheme.colors.surfaceVariant
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
            if (searchFieldState.value.isNotBlank()) {
                SearchAssemblingView(
                    assemblings = searchedAssemblings,
                    categories = categories,
                    currentCategory = currentCategory,
                    onCardClick = onCardClick,
                    onCategoryChanged = onCategoryChanged
                )
            } else
                AssemblingListView(
                    newAssemblings = newAssemblings,
                    allAssemblings = allAssemblings,
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
    val keyboardController = LocalSoftwareKeyboardController.current
    val interactionSource = remember { MutableInteractionSource() }
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
                tint = RoboticsGuideTheme.colors.primary,
                contentDescription = null
            )
        },
        singleLine = true,
        textStyle = RoboticsGuideTheme.typography.body,
        placeholder = {
            Text(
                text = if (textState.value.isNotBlank()) "" else stringResource(id = hintStringRes),
                color = RoboticsGuideTheme.colors.secondary
            )
        },
        trailingIcon = {
            if (textState.value.isNotEmpty()) {
                Icon(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .clickable(interactionSource, indication = null) {
                            textState.value = ""
                            keyboardController?.hide()
                        },
                    imageVector = Icons.Default.Clear,
                    tint = RoboticsGuideTheme.colors.primary,
                    contentDescription = null
                )
            }
        },
        shape = CircleShape,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = RoboticsGuideTheme.colors.surfaceContainerHigh,
            unfocusedContainerColor = RoboticsGuideTheme.colors.surfaceContainerHigh,
            focusedIndicatorColor = RoboticsGuideTheme.colors.surfaceContainerHigh,
            unfocusedIndicatorColor = RoboticsGuideTheme.colors.surfaceContainerHigh,
            cursorColor = RoboticsGuideTheme.colors.primary,
            focusedTextColor = RoboticsGuideTheme.colors.secondary,
            unfocusedTextColor = RoboticsGuideTheme.colors.secondary
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )
}