package com.davidpl.commons.business

data class ResponseError(
    val code: Int?,
    val method: String? = null,
    val endpoint: String? = null,
    val headers: String? = null,
    val message: String?,
    val body: String? = null
)
