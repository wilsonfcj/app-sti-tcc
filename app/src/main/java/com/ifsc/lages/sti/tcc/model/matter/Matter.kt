package com.ifsc.lages.sti.tcc.model.matter

import com.ifsc.lages.sti.tcc.resources.user.UserResponse
import io.realm.Realm
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.io.Serializable

open class Matter : RealmObject(), Serializable {

    @PrimaryKey
    var _id : String? = null
    var idUser: Long = 0
    var code : Int? = null
    var name : String? = null

    object DataBase {
        fun insertOrUpdateAll(matters : List<Matter>) {
            try {
                var realm = Realm.getDefaultInstance()
                realm.beginTransaction()
                realm.insertOrUpdate(matters)
                realm.commitTransaction()
                realm.close()
            } catch (ex : Exception) {
                ex.printStackTrace()
            }
        }

        fun loadAll(idUser : Long) : List<Matter>? {
            val realm = Realm.getDefaultInstance()
            val realmResult = realm
                .where(Matter::class.java)
                .equalTo("idUser", idUser)
                .findAll()
            if(realmResult != null) {
                var list = realm.copyFromRealm(realmResult)
                realm.close()
                return list
            }
            realm.close()
            return null
        }

        fun deleteAll(idUser : Long) {
            try {
                val realm = Realm.getDefaultInstance()
                val realmResult = realm.where(Matter::class.java)
                    .equalTo("idUser", idUser)
                    .findAll()
                realm.beginTransaction()
                realmResult?.deleteAllFromRealm()
                realm.commitTransaction()
                realm.close()
            } catch (ex : Exception) {
                ex.printStackTrace()
            }
        }
    }

    object MatterMappper {
        fun transform(id :  Long, it: UserResponse.Matter): Matter {
            var matter =  Matter()
            matter._id = "$id${it.cod}"
            matter.idUser = id
            matter.code = it.cod
            matter.name = it.name
            return matter
        }

        fun transform(idUser : Long, list: MutableList<UserResponse.Matter>): MutableList<Matter> {
            val listRes = ArrayList<Matter>()
            list.forEach {
                listRes.add(transform(idUser, it))
            }
            return listRes
        }
    }

}