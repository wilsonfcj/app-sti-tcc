package com.ifsc.lages.sti.tcc.resources

class BaseView <out Data : Any?> (
    val success: Data? = null,
    val error: Boolean? = null,
    val message: String? = null
)