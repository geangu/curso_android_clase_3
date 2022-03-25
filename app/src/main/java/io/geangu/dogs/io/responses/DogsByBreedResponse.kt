package io.geangu.dogs.io.responses

import com.google.gson.annotations.SerializedName

data class DogsByBreedResponse (
    @SerializedName("status") val status: String,
    @SerializedName("message") val images: List<String>
)
