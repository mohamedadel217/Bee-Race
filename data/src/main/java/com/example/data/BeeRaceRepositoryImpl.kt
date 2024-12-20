package com.example.data

import com.example.common.DispatcherProvider
import com.example.common.Resource
import com.example.data.models.CaptchaErrorResponse
import com.example.domain.BeeRaceRepository
import com.example.domain.models.Bee
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.json.JSONException
import org.json.JSONObject

class BeeRaceRepositoryImpl(
    private val api: BeeRaceApi,
    private val dispatcherProvider: DispatcherProvider
) : BeeRaceRepository {

    override fun fetchRaceDuration(): Flow<Int> = flow {
        val response = api.getRaceDuration()
        if (response.isSuccessful) {
            emit(response.body()?.timeInSeconds ?: 0)
        } else {
            throw Exception("Failed to fetch race duration")
        }
    }.flowOn(dispatcherProvider.io)

    override fun fetchBeeList(): Flow<Resource<List<Bee>>> = flow {
        val response = api.getRaceStatus()
        when (response.code()) {
            200 -> {
                val beeList = response.body()?.beeList?.map { Bee(it.name, it.color) } ?: emptyList()
                emit(Resource.Success(beeList))
            }
            403 -> {
                // Use Moshi to parse the error body
                val errorBody = response.errorBody()?.string()
                val captchaUrl = errorBody?.let { body ->
                    try {
                        val moshi = Moshi.Builder().build()
                        val jsonAdapter = moshi.adapter(CaptchaErrorResponse::class.java)
                        val parsedResponse = jsonAdapter.fromJson(body)
                        parsedResponse?.captchaUrl
                    } catch (e: Exception) {
                        null
                    }
                }
                emit(Resource.Error("Captcha required", captchaUrl))
            }
            else -> emit(Resource.Error("Error: ${response.code()}"))
        }
    }.flowOn(dispatcherProvider.io)

}

