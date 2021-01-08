package com.ifsc.lages.sti.tcc.resources.classroom

import com.google.gson.annotations.SerializedName

class ClassroomRequest {

    class EnterClassroom {
        @SerializedName(value = "IdUsuario")
        private val idUsuario: Long? = null

        @SerializedName(value = "IdSala")
        private val idSala: Long? = null

        @SerializedName(value = "Senha")
        private val senha: String? = null
    }

    class DeleteClassroom {
        @SerializedName(value = "IdUsuario")
        private val idUsuario: Long? = null

        @SerializedName(value = "IdSala")
        private val idSala: Long? = null
    }
}