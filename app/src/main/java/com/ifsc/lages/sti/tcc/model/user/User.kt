package com.ifsc.lages.sti.tcc.model.user

import android.content.Context
import android.view.View
import com.ifsc.lages.sti.tcc.model.matter.Matter
import com.ifsc.lages.sti.tcc.props.EUserType
import com.ifsc.lages.sti.tcc.resources.user.UserResponse
import com.ifsc.lages.sti.tcc.utilidades.KeyPrefs
import com.ifsc.lages.sti.tcc.utilidades.SharedPreferencesUtil
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

class User : Serializable {
    var _id: Long? = null
    var cpf: String? = null
    var birthDay: Date? = null
    var name: String? = null
    var email: String? = null
    var phone: String? = null
    var educationalInstitution: EducationalInstitution? = null
    var imageUser: String? = null
    var userType: Int? = null
    var registration: Long? = null
    var anoIngresso: Long? = null
    var matter: MutableList<Matter>? = null

    object UserMappper {

        fun transform(it: UserResponse.Login): User {
            val user =  User()
            user._id = it._id
            user.cpf = it.cpf
            user.birthDay = it.birthDay
            user.name = it.name
            user.email = it.email
            user.phone = it.phone

            if(it.educationalInstitution != null) {
                val instituition = EducationalInstitution()
                instituition._id = it.educationalInstitution?._id
                instituition.name = it.educationalInstitution?.name
                user.educationalInstitution = instituition
            }

            if(it.matters != null) {
                val matter = Matter.MatterMappper.transform(it._id!!, it.matters!!)
                user.matter = matter
            }

            user.imageUser = it.imageUser
            user.userType = it.userType
            user.registration = it.registration
            user.anoIngresso = it.anoIngresso
            return user
        }

        fun transform(list: MutableList<UserResponse.Login>): MutableList<User> {
            val listRes = ArrayList<User>()
            list.forEach {
                listRes.add(transform(it))
            }
            return listRes
        }
    }

    object UserShared {
        fun clear(context : Context) {
            var cpfRemember = SharedPreferencesUtil.get(context, KeyPrefs.USER_REMEMBER_CPF, "")
            SharedPreferencesUtil.clear(context)
            SharedPreferencesUtil.put(context, KeyPrefs.USER_REMEMBER_CPF, cpfRemember)
        }

        fun save(context : Context, t : User) {
            SharedPreferencesUtil.put(context, KeyPrefs.USER_ID, t._id)
            SharedPreferencesUtil.put(context, KeyPrefs.USER_CPF, t.cpf)
            SharedPreferencesUtil.put(context, KeyPrefs.USER_BIRTH_DAY, t.birthDay)
            SharedPreferencesUtil.put(context, KeyPrefs.USER_NAME, t.name)
            SharedPreferencesUtil.put(context, KeyPrefs.USER_TYPE, t.userType)
            SharedPreferencesUtil.put(context, KeyPrefs.USER_REGISTRATION, t.registration)

            if(t.email != null) {
                SharedPreferencesUtil.put(context, KeyPrefs.USER_EMAIL, t.email)
            }

            if(t.phone != null) {
                SharedPreferencesUtil.put(context, KeyPrefs.USER_PHONE, t.phone)
            }

            if(t.imageUser != null) {
                SharedPreferencesUtil.put(context, KeyPrefs.USER_PHOTO, t.imageUser)
            }

            if(t.anoIngresso != null) {
                SharedPreferencesUtil.put(context, KeyPrefs.USER_YEAR_JOIN, t.anoIngresso)
            }

            if(t.educationalInstitution != null) {
                SharedPreferencesUtil.put(context, KeyPrefs.EDUCATION_INSTITUITION_ID, t.educationalInstitution?._id)
                SharedPreferencesUtil.put(context, KeyPrefs.EDUCATION_INSTITUITION_NOME, t.educationalInstitution?.name)
            }

            if(t.matter != null) {
                Matter.DataBase.deleteAll(t._id!!)
                Matter.DataBase.insertOrUpdateAll(t.matter!!)
            }
        }

        fun load(context: Context) : User? {
            val cpf = SharedPreferencesUtil.get(context, KeyPrefs.USER_CPF, "")
            if(cpf.isEmpty()) {
                return null
            }

            val _id = SharedPreferencesUtil.get(context, KeyPrefs.USER_ID, 0L)
            val name = SharedPreferencesUtil.get(context, KeyPrefs.USER_NAME, "Não informado")
            val birthday = SharedPreferencesUtil.get(context, KeyPrefs.USER_BIRTH_DAY, Date())
            val type = SharedPreferencesUtil.get(context, KeyPrefs.USER_TYPE, 1)
            val registration = SharedPreferencesUtil.get(context, KeyPrefs.USER_REGISTRATION, 0L)
            val email =  SharedPreferencesUtil.get(context, KeyPrefs.USER_EMAIL, "Não informado")
            val phone = SharedPreferencesUtil.get(context, KeyPrefs.USER_PHONE, "Não informado")
            val yearJoin = SharedPreferencesUtil.get(context, KeyPrefs.USER_YEAR_JOIN, 2017L)
            val educationName = SharedPreferencesUtil.get(context, KeyPrefs.EDUCATION_INSTITUITION_NOME, "")
            val educationCode = SharedPreferencesUtil.get(context, KeyPrefs.EDUCATION_INSTITUITION_ID, 0L)
            var image = SharedPreferencesUtil.get(context, KeyPrefs.USER_PHOTO, "")

            val educationalInstitution = EducationalInstitution()
            educationalInstitution?._id = educationCode
            educationalInstitution?.name = educationName

            val user = User()
            user.cpf = cpf
            user._id = _id
            user.name = name
            user.email = email
            user.birthDay = birthday
            user.userType = type
            user.phone = phone
            user.imageUser = image
            user.educationalInstitution = educationalInstitution

            if(EUserType.STUDENT.code == type) {
                user.registration = registration
                user.anoIngresso = yearJoin
            } else {
                var matters : MutableList<Matter> = Matter.DataBase.loadAll(_id) as MutableList<Matter>
                user.matter = matters
            }

            return user
        }
    }
}