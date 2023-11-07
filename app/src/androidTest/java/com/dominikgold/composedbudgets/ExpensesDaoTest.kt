package com.dominikgold.composedbudgets

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dominikgold.composedbudgets.common.Percentage
import com.dominikgold.composedbudgets.database.ComposedBudgetsRoomDb
import com.dominikgold.composedbudgets.database.budgets.PersistedBudget
import com.dominikgold.composedbudgets.database.expenses.PersistedExpense
import com.dominikgold.composedbudgets.domain.entities.BudgetId
import com.dominikgold.composedbudgets.domain.entities.BudgetInterval
import com.dominikgold.composedbudgets.domain.entities.ExpenseId
import com.dominikgold.composedbudgets.utils.test
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.time.ZoneOffset
import java.time.ZonedDateTime

@RunWith(AndroidJUnit4::class)
class ExpensesDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: ComposedBudgetsRoomDb

    private val testDateTime = ZonedDateTime.of(2023, 11, 7, 10, 0, 0, 0, ZoneOffset.UTC)

    private val testBudget = PersistedBudget(
        id = BudgetId("test_budget1"),
        name = "budget1",
        interval = BudgetInterval.Monthly,
        limit = 100.0,
        excessCarryOver = Percentage(1f),
        overdraftCarryOver = Percentage(1f),
        createdAt = testDateTime,
    )

    @Before
    fun initDb() {
        db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), ComposedBudgetsRoomDb::class.java)
            .allowMainThreadQueries()
            .build()
        runBlocking {
            db.budgetsDao().upsertBudget(testBudget)
        }
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun Getting_current_expenses_in_monthly_budget() = runTest {
        db.expensesDao().createExpense(PersistedExpense(ExpenseId("1"), "", 10.0, testBudget.id, testDateTime))
        db.expensesDao().createExpense(PersistedExpense(ExpenseId("2"), "", 5.0, testBudget.id, testDateTime.plusDays(1)))
        db.expensesDao().createExpense(PersistedExpense(ExpenseId("3"), "", 15.0, testBudget.id, testDateTime.minusDays(1)))
        db.expensesDao().createExpense(PersistedExpense(ExpenseId("4"), "", 10.0, BudgetId("different_budget"), testDateTime))
        db.expensesDao().createExpense(PersistedExpense(ExpenseId("5"), "", 200.0, testBudget.id, testDateTime.minusDays(11)))
        db.expensesDao().createExpense(PersistedExpense(ExpenseId("6"), "", 200.0, testBudget.id, testDateTime.plusDays(11)))

        db.expensesDao().getExpensesInPeriod(
            testBudget.id,
            startTime = testDateTime.minusDays(10),
            endTime = testDateTime.plusDays(10)
        ).test(this) {
            awaitValue(0).map { it.id } shouldBeEqualTo listOf(
                ExpenseId("1"),
                ExpenseId("2"),
                ExpenseId("3"),
            )

            db.expensesDao().createExpense(PersistedExpense(ExpenseId("7"), "", 20.0, testBudget.id, testDateTime.plusDays(9)))

            awaitValue(1).map { it.id } shouldBeEqualTo listOf(
                ExpenseId("1"),
                ExpenseId("2"),
                ExpenseId("3"),
                ExpenseId("7"),
            )
        }
    }

    @Test
    fun Getting_current_expenses_in_daily_budget() = runTest {
        db.expensesDao().createExpense(PersistedExpense(ExpenseId("1"), "", 10.0, testBudget.id, testDateTime))
        db.expensesDao().createExpense(PersistedExpense(ExpenseId("2"), "", 5.0, testBudget.id, testDateTime.plusHours(6)))
        db.expensesDao().createExpense(PersistedExpense(ExpenseId("3"), "", 15.0, testBudget.id, testDateTime.minusHours(6)))
        db.expensesDao().createExpense(PersistedExpense(ExpenseId("4"), "", 10.0, BudgetId("different_budget"), testDateTime))
        db.expensesDao().createExpense(PersistedExpense(ExpenseId("5"), "", 200.0, testBudget.id, testDateTime.minusHours(13)))
        db.expensesDao().createExpense(PersistedExpense(ExpenseId("6"), "", 200.0, testBudget.id, testDateTime.plusHours(13)))

        db.expensesDao().getExpensesInPeriod(
            testBudget.id,
            startTime = testDateTime.minusHours(12),
            endTime = testDateTime.plusHours(12)
        ).test(this) {
            awaitValue(0).map { it.id } shouldBeEqualTo listOf(
                ExpenseId("1"),
                ExpenseId("2"),
                ExpenseId("3"),
            )

            db.expensesDao().createExpense(PersistedExpense(ExpenseId("7"), "", 20.0, testBudget.id, testDateTime.plusHours(11)))

            awaitValue(1).map { it.id } shouldBeEqualTo listOf(
                ExpenseId("1"),
                ExpenseId("2"),
                ExpenseId("3"),
                ExpenseId("7"),
            )
        }
    }
}
