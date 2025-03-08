package com.arthurabreu.allthingsandroid.services

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/*
 * MyWorker: A WorkManager worker that performs a long-running task using a use case.
 * It's designed to run in the background and uses Koin for dependency injection.
 */
class MyWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams), KoinComponent {

    private val myUseCase: MyUseCase by inject() // Inject use case with Koin

    /*
     * doWork: Executes the long-running task within a coroutine on the IO dispatcher.
     * It calls the use case's performLongRunningTask method and returns success or failure.
     */
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        return@withContext try {
            myUseCase.performLongRunningTask().catch { e ->
                Log.e("MyWorker", "Error in long running task", e)
                Result.failure()
            }.collect{
                Log.d("MyWorker", it)
            }
            Result.success()
        } catch (e: Exception) {
            Log.e("MyWorker", "General error in worker", e)
            Result.failure()
        }
    }
}

/*
 * MyUseCase: Contains the business logic for a long-running task.
 * It interacts with the MyRepository to perform data operations.
 */
class MyUseCase(private val myRepository: MyRepository) {

    /*
     * performLongRunningTask: Executes the business logic of the long-running task.
     * It calls the repository's doSomething method.
     */
    fun performLongRunningTask(): Flow<String> {
        return myRepository.doSomething()
    }
}

/*
 * MyRepository: An interface defining the contract for data operations.
 */
interface MyRepository {
    /*
     * doSomething: Performs a data operation.
     */
    fun doSomething(): Flow<String>
}

/*
 * MyRepositoryImpl: An implementation of the MyRepository interface.
 * It provides the actual implementation of data operations.
 */
class MyRepositoryImpl : MyRepository {
    override fun doSomething(): Flow<String> = flow {
        // Simulate a long-running operation with multiple steps
        emit("MyRepositoryImpl: Starting long operation...")
        delay(1000)
        emit("MyRepositoryImpl: Step 1: Processing data...")
        delay(1500)
        emit("MyRepositoryImpl: Step 2: Performing calculations...")
        delay(500)
        emit("MyRepositoryImpl: Step 3: Saving results...")
        delay(1000)
        emit("MyRepositoryImpl: Long operation completed.")
        Log.d("MyRepositoryImpl", "MyRepositoryImpl: Simulated a long running operation...")
    }.flowOn(Dispatchers.IO) // Execute the flow on the IO dispatcher
}

// ... in your Activity or Fragment
//val workRequest = OneTimeWorkRequestBuilder<MyWorker>().build()
//WorkManager.getInstance(this).enqueue(workRequest)