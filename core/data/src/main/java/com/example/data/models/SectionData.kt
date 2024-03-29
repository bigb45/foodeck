package com.example.data.models

data class SectionData(
    val id: String,
    val title: String,
    val options: List<Option>,
    val currency: String?,
    val required: Boolean,
)
