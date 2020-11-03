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


    open class Register {
        @SerializedName(value = "CPF")
        var cpf: String? = null

        @SerializedName(value = "Nascimento")
        var birthday: Date? = null

        @SerializedName(value = "Nome")
        var name: String? = null

        @SerializedName(value = "Email")
        var email: String? = null

        @SerializedName(value = "Telefone")
        var phone: String? = null

        @SerializedName(value = "Instituicao")
        var educationalinstitution: Long? = null

        @SerializedName(value = "ImagemUsuario")
        var photoPerfil: String? = null

        @SerializedName(value = "TipoUsuario")
        var userType: Int? = null

        @SerializedName(value = "Matricula")
        var registerNumber: Long? = null

        @SerializedName(value = "AnoIngresso")
        var yearsJoin: Int? = null

        @SerializedName(value = "DisciplinasInteressadas")
        var metters: List<String>? = null

        @SerializedName(value = "Senha")
        var password: String? = null
    }
}