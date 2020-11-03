package com.ifsc.lages.sti.tcc.model.user

import android.content.Context
import com.google.gson.annotations.SerializedName
import com.ifsc.lages.sti.tcc.model.matter.Matter
import com.ifsc.lages.sti.tcc.resources.user.UserResponse
import com.ifsc.lages.sti.tcc.utilidades.KeyPrefs
import com.ifsc.lages.sti.tcc.utilidades.SharedPreferencesUtil

class User {
    var _id: Long? = null
    var cpf: String? = null
    var birthDay: String? = null
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
            var user =  User()
            user._id = it._id
            user.cpf = it.cpf
            user.birthDay = it.birthDay
            user.name = it.name
            user.email = it.email
            user.phone = it.phone

            if(it.educationalInstitution != null) {
                var instituition = EducationalInstitution()
                instituition._id = it.educationalInstitution?._id
                instituition.name = it.educationalInstitution?.name
                user.educationalInstitution = instituition
            }

            if(it.matters != null) {
                var matter = Matter.MatterMappper.transform(it._id!!, it.matters!!)
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
                Matter.DB.deleteAll(t._id!!)
                Matter.DB.insertOrUpdateAll(t.matter!!)
            }
        }
    }
}