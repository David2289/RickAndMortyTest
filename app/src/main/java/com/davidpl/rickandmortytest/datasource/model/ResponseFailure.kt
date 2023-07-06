package com.davidpl.commons.business

data class ResponseFailure(
    val code: Int? = null,
    val message: String? = null,
    val exception: Exception
)