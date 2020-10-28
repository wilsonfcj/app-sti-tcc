package com.ifsc.lages.sti.tcc.resources.user

import com.google.gson.annotations.SerializedName

sealed class UserResponse {

    open class Login(
        @SerializedName("IdUsuario")
        var _id: Long? = null,

        @SerializedName("CPF")
        var cpf: String? = null,

        @SerializedName("Nascimento")
        var birthDay: String? = null,

        @SerializedName("Nome")
        var name: String? = null,

        @SerializedName("Email")
        var email: String? = null,

        @SerializedName("Telefone")
        var phone: String? = null,

        @SerializedName("Instituicao")
        var educationalInstitution: EducationalInstitution? = null,

        @SerializedName("ImagemUsuario")
        var imageUser: String? = null,

        @SerializedName("TipoUsuario")
        var userType: String? = null,

        @SerializedName("Matricula")
        var registration: Long? = null,

        @SerializedName("AnoIngresso")
        var anoIngresso: Long? = null
    )

    open class EducationalInstitution (
        @SerializedName("Id")
        var _id: Long? = null,

        @SerializedName("Nome")
        var name: String? = null
    )


}