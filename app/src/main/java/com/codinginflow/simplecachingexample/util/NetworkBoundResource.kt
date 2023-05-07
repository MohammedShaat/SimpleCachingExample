package com.codinginflow.simplecachingexample.util

import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

private const val TAG = "NetworkBoundResource"

fun <dbT, apiT> networkBoundResource(
    dbQuery: () -> Flow<dbT>,
    apiFetch: suspend () -> apiT,
    saveApiFetch: suspend (apiT) -> Unit,
    shouldFetch: (dbT) -> Boolean = { true },
) = flow {

    Log.i(TAG, "networkBoundResource: called")

    val data = dbQuery().first()
    val flow = if (shouldFetch(data)) {
        emit(Resource.Loading(data))
        try {
            delay(2000)
            val fetchedData = apiFetch()
            saveApiFetch(fetchedData)
            dbQuery().map { Resource.Success(it) }
        } catch (e: Throwable) {
            dbQuery().map { Resource.Error(e, it) }
        }
    } else {
        dbQuery().map { Resource.Success(it) }
    }

    emitAll(flow)
}
