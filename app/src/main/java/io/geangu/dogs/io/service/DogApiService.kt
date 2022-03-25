package io.geangu.dogs.io.service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

import io.geangu.dogs.io.responses.DogsByBreedResponse

interface DogApiService {

    @GET
    suspend fun getDogsByBreed(@Url url: String): Response<DogsByBreedResponse>

}