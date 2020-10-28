package com.ifsc.lages.sti.tcc.resources.user

import com.google.gson.annotations.SerializedName
import java.util.*

sealed class UserRequest {
    open class Login(
        @SerializedName("CPF")
        var cpf: String? = null,

        @SerializedName("Senha")
        var password: String? = null
    )
}