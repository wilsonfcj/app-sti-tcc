package com.ifsc.lages.sti.tcc.resources.user

import com.google.gson.annotations.SerializedName
import com.ifsc.lages.sti.tcc.model.user.User
import com.ifsc.lages.sti.tcc.props.EUserType
import com.ifsc.lages.sti.tcc.utilidades.StringUtil
import java.util.*
import kotlin.collections.ArrayList

sealed class UserRequest {

    open class Email(
        @SerializedName("Assunto")
        var assunto: String? = null,

        @SerializedName("Mensagem")
        var mensagem: String? = null
    )

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
        var birthday: String? = null

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


        fun transform(it: User): Register {
            val user =  Register()
            user.cpf = it.cpf
            user.birthday = StringUtil.data( it.birthDay!!,"yyyy-MM-dd'T'HH:mm:ss")
            user.name = it.name
            user.email = it.email
            user.phone = it.phone
            user.educationalinstitution = it.educationalInstitution?._id
            user.userType = it.userType

            if(it.imageUser != null) {
                user.photoPerfil = it.imageUser
            }

            if(user.userType == EUserType.STUDENT.code) {
                user.registerNumber = it.registration
                user.yearsJoin = it.anoIngresso?.toInt()
            } else {
                var matters = ArrayList<String>()
                for (matter in it.matter!!) {
                    matters.add(matter.name!!)
                }
                user.metters = matters
            }
            return user
        }

        fun transform(list: MutableList<User>): MutableList<Register> {
            val listRes = ArrayList<Register>()
            list.forEach {
                listRes.add(transform(it))
            }
            return listRes
        }
    }
}