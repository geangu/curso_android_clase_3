package io.geangu.dogs.io.helper

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


fun getRetrofit(): Retrofit =
    Retrofit.Builder()
        .baseUrl("https://dog.ceo/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
