package com.dominikgold.composedbudgets.android.features.expenses.add

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dominikgold.composedbudgets.android.design.Colors
import com.dominikgold.composedbudgets.android.design.ComposedBudgetsTheme
import com.dominikgold.composedbudgets.android.utils.di.scopedKoinViewModel
import com.dominikgold.composedbudgets.entities.Currency
import com.dominikgold.composedbudgets.entities.Expense
import com.dominikgold.composedbudgets.entities.MonetaryValue
import com.dominikgold.composedbudgets.features.expenses.add.AddExpensesUiState
import com.dominikgold.composedbudgets.features.expenses.add.UncategorizedExpense
import com.dominikgold.composedbudgets.utils.files.LocalFileResource
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

@Composable
fun AddExpensesDestination() {
    val viewModel = scopedKoinViewModel<AddExpensesViewModelContainer>().viewModel

    if (!viewModel.hasPickedFile) {
        val resultLauncher = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { fileUri ->
            fileUri?.let { viewModel.onFileSelected(LocalFileResource(it)) }
        }

        resultLauncher.launch(arrayOf("text/csv"))
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AddExpensesContent(uiState)
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun AddExpensesContent(
    uiState: AddExpensesUiState,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Add expenses", style = MaterialTheme.typography.headlineLarge)
                },
            )
        }
    ) { contentPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(top = contentPadding.calculateTopPadding()),
        ) {
            Text(
                uiState.selectedFileName,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleMedium,
                color = Colors.TextMedium.dynamic()
            )
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp + contentPadding.calculateBottomPadding(),
                ),
            ) {
                stickyHeader(key = "uncategorized_title") {
                    Text(
                        "Uncategorized",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleMedium,
                        color = Colors.TextMedium.dynamic()
                    )
                }
            }
        }
    }
}

@Suppress("MagicNumber")
@Preview
@Composable
private fun AddExpensesPreview() {
    ComposedBudgetsTheme {
        AddExpensesContent(
            AddExpensesUiState(
                "expenses.csv",
                listOf(Expense.preview),
                listOf(
                    UncategorizedExpense(
                        "Merch123Groc",
                        LocalDateTime(2025, 1, 1, 0, 0).toInstant(TimeZone.UTC),
                        MonetaryValue(1000, Currency.Euro),
                    )
                )
            )
        )
    }
}
