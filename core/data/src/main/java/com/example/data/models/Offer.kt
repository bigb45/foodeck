package com.example.data.models

import com.google.gson.annotations.SerializedName

data class Offer(
    @SerializedName("offer_id") val offerId: String,
    @SerializedName("offer_name") val offerName: String,
    @SerializedName("cover_image_url") val offerImageUrl: String,
)
