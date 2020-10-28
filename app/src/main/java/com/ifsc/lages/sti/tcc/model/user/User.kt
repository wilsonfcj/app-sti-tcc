package com.ifsc.lages.sti.tcc.model.user

import com.google.gson.annotations.SerializedName
import com.ifsc.lages.sti.tcc.resources.user.UserResponse

class User {
    var _id: Long? = null
    var cpf: String? = null
    var birthDay: String? = null
    var name: String? = null
    var email: String? = null
    var phone: String? = null
    var educationalInstitution: EducationalInstitution? = null
    var imageUser: String? = null
    var userType: String? = null
    var registration: Long? = null
    var anoIngresso: Long? = null


    object UserMappper {

        fun transform(it: UserResponse.Login): User {
            var user =  User()
            user._id = it._id
            user.cpf = it.cpf
            user.birthDay = it.birthDay
            user.name = it.name
            user.email = it.email
            user.phone = it.phone

            var instituition = EducationalInstitution()
            instituition._id = it.educationalInstitution?._id
            instituition.name = it.educationalInstitution?.name

            user.educationalInstitution = instituition
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
}