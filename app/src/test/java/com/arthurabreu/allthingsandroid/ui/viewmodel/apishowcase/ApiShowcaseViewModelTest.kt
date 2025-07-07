package com.arthurabreu.allthingsandroid.ui.viewmodel.apishowcase

import com.arthurabreu.allthingsandroid.data.config.Resource
import com.arthurabreu.allthingsandroid.domain.exceptions.DomainException
import com.arthurabreu.allthingsandroid.domain.model.DomainData
import com.arthurabreu.allthingsandroid.domain.model.DomainModel
import com.arthurabreu.allthingsandroid.domain.repos.ApiRepository
import com.arthurabreu.allthingsandroid.domain.usecases.DataUseCases
import com.arthurabreu.allthingsandroid.ui.viewmodel.BaseViewModelTest
import com.arthurabreu.allthingsandroid.utils.logger.ClassLogger
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.jvm.isAccessible

@ExperimentalCoroutinesApi
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Required for @BeforeAll
@ExtendWith(MockKExtension::class)
class ApiShowcaseViewModelTest : BaseViewModelTest() {

    /*
     * This test class is designed to test the ApiShowcaseViewModel.
     * It uses MockK for mocking dependencies and JUnit 5 for testing.
     *
     * every, coEvery, coVerify are used to mock the behavior of the repository and use cases.
     *
     * All tests have descriptive names and use @DisplayName for better readability.
     */

    private lateinit var viewModel: ApiShowcaseViewModel
    private val repository: ApiRepository = mockk()
    private val useCases: DataUseCases = mockk()
    private val logger: ClassLogger = mockk(relaxed = true)

    private val mockDomainModel = DomainModel(
        id = 1,
        userId = 1,
        title = "Test Title",
        completed = false
    )

    private val mockDomainData = DomainData(
        id = 1,
        userId = 1,
        title = "Test Title",
        completed = false
    )

    @BeforeEach
    fun setUp() {
        every { useCases.observeData() } returns flowOf(mockDomainData)
        coEvery { repository.getData() } returns mockDomainModel
    }

    @Test
    @DisplayName("Given repository returns data, when ViewModel is initialized, then apiData is Success")
    fun givenRepositoryReturnsData_whenViewModelIsInitialized_thenApiDataIsSuccess() = runTest {
        //Given
        coEvery { repository.getData() } returns mockDomainModel

        // When
        viewModel = ApiShowcaseViewModel(repository, useCases, logger)

        // Then
        val state = viewModel.apiData.value
        Assertions.assertInstanceOf(Resource.Success::class.java, state)
        Assertions.assertEquals(mockDomainModel, (state as Resource.Success).data)
        coVerify(exactly = 4) { repository.getData() }
    }

    @Test
    @DisplayName("Given repository throws exception, when ViewModel is initialized, then apiData is Error")
    fun givenRepositoryThrowsException_whenViewModelIsInitialized_thenApiDataIsError() = runTest {
        // Given
        val exception = DomainException.NetworkError("Network error")
        coEvery { repository.getData() } throws exception

        // When
        viewModel = ApiShowcaseViewModel(repository, useCases, logger)

        // Then
        val state = viewModel.apiData.value
        Assertions.assertInstanceOf(Resource.Error::class.java, state)
        Assertions.assertEquals(exception, (state as Resource.Error).exception)
    }

    @Test
    @DisplayName("Given use cases return data, when getLatestData is called, then dataState is Success")
    fun givenUseCasesReturnData_whenGetLatestDataIsCalled_thenDataStateIsSuccess() = runTest {
        // Given
        coEvery { useCases.getLatestData() } returns mockDomainData
        viewModel = ApiShowcaseViewModel(repository, useCases, logger)

        // When
        viewModel::class.declaredMemberFunctions
            .first { it.name == "getLatestData" }
            .apply { isAccessible = true }
            .call(viewModel)

        // Then
        val state = viewModel.dataState.value
        Assertions.assertInstanceOf(Resource.Success::class.java, state)
        Assertions.assertEquals(mockDomainData, (state as Resource.Success).data)
        coVerify(exactly = 1) { useCases.getLatestData() }
    }

    @Test
    @DisplayName("Given use cases throw exception, when getLatestData is called, then dataState is Error")
    fun givenUseCasesThrowException_whenGetLatestDataIsCalled_thenDataStateIsError() = runTest {
        // Given
        val exception = RuntimeException("Database error")
        coEvery { useCases.getLatestData() } throws exception
        viewModel = ApiShowcaseViewModel(repository, useCases, logger)

        // When
        viewModel::class.declaredMemberFunctions
            .first { it.name == "getLatestData" }
            .apply { isAccessible = true }
            .call(viewModel)

        // Then
        val state = viewModel.dataState.value
        Assertions.assertInstanceOf(Resource.Error::class.java, state)
        val domainException = (state as Resource.Error).exception
        Assertions.assertInstanceOf(DomainException.UnknownError::class.java, domainException)
        Assertions.assertEquals("Database error", domainException.message)
    }

    @Test
    @DisplayName("Given observeData returns flow, when ViewModel is initialized, then observedData emits data")
    fun givenObserveDataReturnsFlow_whenViewModelIsInitialized_thenObservedDataEmitsData() =
        runTest {
            // Given
            every { useCases.observeData() } returns flowOf(mockDomainData)

            // When
            viewModel = ApiShowcaseViewModel(repository, useCases, logger)

            // Then
            val observed = viewModel.observedData.first()
            Assertions.assertEquals(mockDomainData, observed)
        }
}