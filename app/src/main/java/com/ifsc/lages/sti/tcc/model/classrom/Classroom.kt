package com.ifsc.lages.sti.tcc.model.classrom

import io.realm.Realm
import io.realm.RealmObject
import io.realm.Sort
import io.realm.annotations.PrimaryKey
import java.util.*

open class Classroom : RealmObject() {

    @PrimaryKey
    var _id: Long? = null
    var idUsuario: Long? = null
    var nome: String? = null
    var descricao: String? = null
    var idTeacher: Long? = null
    var nameTeacher: String? = null
    var maxParticipantes: Int? = null
    var qtdParticipantes = 0
    var dataCriacao: Date? = null
    var participando = false


    object DataBase {
        fun save(resultOverall: Classroom): Long {
            var _id = 1L
            var realm = Realm.getDefaultInstance()
            try {
                if(resultOverall._id == null)  {
                    val maxId = realm.where(Classroom::class.java).max("_id")
                    val nextId = if (maxId == null) 1 else maxId.toLong() + 1
                    resultOverall._id = nextId
                }
                realm.beginTransaction()
                var transactionMonitoring = realm.copyToRealmOrUpdate(resultOverall)
                _id = transactionMonitoring?._id!!
                realm.commitTransaction()
            } catch (ex: Exception) {
                ex.printStackTrace()
            } finally {
                realm.close()
                return _id
            }
        }

        fun update(resultOverall: Classroom) : Long {
            var _id = 1L
            var realm = Realm.getDefaultInstance()
            try {
                realm.beginTransaction()
                var transaction  = realm.copyToRealmOrUpdate(resultOverall)
                _id = transaction?._id!!
                realm.commitTransaction()
            } catch (ex : Exception) {
                ex.printStackTrace()
            } finally {
                realm.close()
                return _id
            }
        }

        fun loadById(id : Long) : Classroom? {
            var obgect : Classroom? = null
            var realm = Realm.getDefaultInstance()
            try {
                var realInfos = realm.where(Classroom::class.java).equalTo("_id", id).findFirst()
                if(realInfos!= null) {
                    obgect = realm.copyFromRealm(realInfos)
                }
            } catch (ex : Exception) {
                ex.printStackTrace()
            } finally {
                realm.close()
                return obgect
            }
        }

        fun loadByIdUser(userId : Long) : MutableList<Classroom>? {
            var obgect : MutableList<Classroom>? = null
            var realm = Realm.getDefaultInstance()
            try {
                var realInfos = realm.where(Classroom::class.java).equalTo("idUsuario", userId)
                    .sort("dataCriacao", Sort.DESCENDING).findAll()
                if(realInfos!= null) {
                    obgect = realm.copyFromRealm(realInfos)
                }
            } catch (ex : Exception) {
                ex.printStackTrace()
            } finally {
                realm.close()
                return obgect
            }
        }

        fun loadByIdTeacher(idTeacher : Long) : MutableList<Classroom>? {
            var obgect : MutableList<Classroom>? = null
            var realm = Realm.getDefaultInstance()
            try {
                var realInfos = realm.where(Classroom::class.java).equalTo("idTeacher", idTeacher)
                    .sort("dataCriacao", Sort.DESCENDING).findAll()
                if(realInfos!= null) {
                    obgect = realm.copyFromRealm(realInfos)
                }
            } catch (ex : Exception) {
                ex.printStackTrace()
            } finally {
                realm.close()
                return obgect
            }
        }

        fun loadByTypeAndIdUser(id : Long, userId : Long) : Classroom? {
            var obgect : Classroom? = null
            var realm = Realm.getDefaultInstance()
            try {
                var realInfos = realm.where(Classroom::class.java).equalTo("_id", id)
                    .equalTo("idUsuario", userId).findFirst()

                if(realInfos!= null) {
                    obgect = realm.copyFromRealm(realInfos)
                }
                realm.commitTransaction()
            } catch (ex : Exception) {
                ex.printStackTrace()
            } finally {
                realm.close()
                return obgect
            }
        }

        fun deleteByIdUser(userId : Long) :  MutableList<Classroom>? {
            var obgect : MutableList<Classroom>? = null
            var realm = Realm.getDefaultInstance()
            try {
                var realInfos = realm.where(Classroom::class.java).equalTo("idUsuario", userId).findAll()
                if(realInfos != null) {
                    realm.beginTransaction()
                    obgect = realm.copyFromRealm(realInfos)
                    realInfos.deleteAllFromRealm()
                    realm.commitTransaction()
                }
            } catch (ex : Exception) {
                ex.printStackTrace()
            } finally {
                realm.close()
                return obgect
            }
        }


        fun deleteById(simulatedId : Long) :  Classroom? {
            var obgect : Classroom? = null
            var realm = Realm.getDefaultInstance()
            try {
                var realInfos = realm.where(Classroom::class.java).equalTo("_id", simulatedId).findFirst()
                if(realInfos != null) {
                    realm.beginTransaction()
                    obgect = realm.copyFromRealm(realInfos)
                    realInfos.deleteFromRealm()
                    realm.commitTransaction()
                }
            } catch (ex : Exception) {
                ex.printStackTrace()
            } finally {
                realm.close()
                return obgect
            }
        }
    }
}