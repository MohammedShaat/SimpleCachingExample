package com.codinginflow.simplecachingexample.util

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

fun <dbT, apiT> networkBoundResource(
    dbQuery: () -> Flow<dbT>,
    apiFetch: suspend () -> apiT,
    saveApiFetch: suspend (apiT) -> Unit,
    shouldFetch: (dbT) -> Boolean = { true }
) = flow {

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