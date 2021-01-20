package com.ifsc.lages.sti.tcc.resources.classroom

import com.google.gson.annotations.SerializedName

class ClassroomRequest {

    class EnterClassroom {
        @SerializedName(value = "IdUsuario")
        var idUsuario: Long? = null

        @SerializedName(value = "IdSala")
        var idSala: Long? = null

        @SerializedName(value = "Senha")
        var senha: String? = null
    }

    class DeleteClassroom {
        @SerializedName(value = "IdUsuario")
        var idUsuario: Long? = null

        @SerializedName(value = "IdSala")
        var idSala: Long? = null
    }

    class Register {
        @SerializedName(value = "IdProfessor")
        var idUsuario: Long? = null

        @SerializedName(value = "Nome")
        var nome: String? = null

        @SerializedName(value = "Descricao")
        var descricao: String? = null

        @SerializedName(value = "MaxParticipantes")
        var maxParticipantes: Int? = null

        @SerializedName(value = "Senha")
        var senha: String? = null
    }
}