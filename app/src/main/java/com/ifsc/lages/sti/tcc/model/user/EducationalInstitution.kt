package com.ifsc.lages.sti.tcc.model.user

import com.ifsc.lages.sti.tcc.resources.education.InstitutionResponse
import io.realm.Realm
import io.realm.RealmObject
import java.io.Serializable

open class EducationalInstitution : RealmObject(), Serializable {

    var _id: Long? = null
    var name: String? = null
    var city: String? = null
    var uf: String? = null

    object DB {
        fun insertOrUpdateAll(matters : List<EducationalInstitution>) {
            try {
                val realm = Realm.getDefaultInstance()
                realm.beginTransaction()
                realm.insertOrUpdate(matters)
                realm.commitTransaction()
                realm.close()
            } catch (ex : Exception) {
                ex.printStackTrace()
            }
        }

        fun loadAll() : MutableList<EducationalInstitution>? {
            val realm = Realm.getDefaultInstance()
            val realmResult = realm
                .where(EducationalInstitution::class.java)
                .findAll()
            if(realmResult != null) {
                val list = realm.copyFromRealm(realmResult)
                realm.close()
                return list
            }
            realm.close()
            return null
        }

        fun deleteAll() {
            try {
                val realm = Realm.getDefaultInstance()
                val realmResult = realm.where(EducationalInstitution::class.java).findAll()
                realm.beginTransaction()
                realmResult?.deleteAllFromRealm()
                realm.commitTransaction()
                realm.close()
            } catch (ex : Exception) {
                ex.printStackTrace()
            }
        }
    }

    object EducationalInstitutionMappper {
        fun transform(it: InstitutionResponse.EducationalInstitution): EducationalInstitution {
            var instituition = EducationalInstitution()
            instituition._id = it._id
            instituition.name = it.name
            instituition.city = it.city
            instituition.uf = it.uf
            return instituition
        }

        fun transform(list: MutableList<InstitutionResponse.EducationalInstitution>): MutableList<EducationalInstitution> {
            val listRes = ArrayList<EducationalInstitution>()
            list.forEach {
                listRes.add(transform(it))
            }
            return listRes
        }
    }
}