package com.arthurabreu.allthingsandroid.domain.repos

import com.arthurabreu.allthingsandroid.domain.model.DomainModel

/**
 * Repository interface that defines the methods that the repository should implement
 */
interface ApiRepository {
    suspend fun getData(): DomainModel
}

// Example Test
//class ApiRepositoryTest {
//    @Test
//    fun `should return mapped domain models`() = runTest {
//        val mockService = mockk<ApiService> {
//            coEvery { fetchData() } returns listOf(ApiDto("1", "Test"))
//        }
//        val repository = ApiRepositoryImpl(mockService, ApiMapper)
//
//        val result = repository.getData()
//
//        assertEquals(1, result.size)
//        assertEquals("Test", result[0].title)
//    }
//}