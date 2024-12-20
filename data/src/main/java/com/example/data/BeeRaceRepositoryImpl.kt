package com.example.data

import com.example.common.DispatcherProvider
import com.example.common.Resource
import com.example.domain.BeeRaceRepository
import com.example.domain.models.Bee
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.json.JSONObject

class BeeRaceRepositoryImpl(
    private val api: BeeRaceApi,
    private val dispatcherProvider: DispatcherProvider
) : BeeRaceRepository {

    override fun fetchBeeList(): Flow<Resource<List<Bee>>> = flow {
        emit(Resource.Loading) // Emit loading state
        try {
            val response = api.getRaceStatus()
            if (response.isSuccessful) {
                val beeList =
                    response.body()?.beeList?.map { Bee(it.name, it.color) } ?: emptyList()
                emit(Resource.Success(beeList))
            } else if (response.code() == 403) {
                val errorBody = response.errorBody()?.string()
                val captchaUrl = errorBody?.let { body ->
                    JSONObject(body).optString("captchaUrl", null)
                }
                emit(Resource.Error("Captcha required", captchaUrl))
            } else {
                emit(Resource.Error("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Exception: ${e.message}"))
        }
    }.flowOn(dispatcherProvider.io)

    override fun fetchRaceDuration(): Flow<Int> = flow<Int> {
        try {
            val response = api.getRaceDuration()
            if (response.isSuccessful) {
                val duration = response.body()?.timeInSeconds ?: 0
                emit(duration)
            } else {
                emit(0)
            }
        } catch (e: Exception) {
            emit(0)
        }
    }.flowOn(dispatcherProvider.io)
}
