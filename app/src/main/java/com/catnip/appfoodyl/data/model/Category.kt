package com.catnip.appfoodyl.data.model

import java.util.UUID

data class Category (
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val imageUrl: String
)