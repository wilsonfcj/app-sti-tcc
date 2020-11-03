package com.ifsc.lages.sti.tcc.resources.education

import com.google.gson.annotations.SerializedName

sealed class InstitutionResponse {

    open class EducationalInstitution (
        @SerializedName("id")
        var _id: Long? = null,

        @SerializedName("nome")
        var name: String? = null,

        @SerializedName("cidade")
        var city: String? = null,

        @SerializedName("uf")
        var uf: String? = null
    )

}