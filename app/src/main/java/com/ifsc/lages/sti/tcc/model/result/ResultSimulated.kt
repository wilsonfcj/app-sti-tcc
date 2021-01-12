package com.ifsc.lages.sti.tcc.model.result

import io.realm.Realm
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.io.Serializable
import java.util.*

open class ResultSimulated : RealmObject(), Serializable {

    @PrimaryKey
    var _id: Long? = null
    var idUsuario: Long? = null
    var idSimulado: Long? = null
    var nome: String? = null
    var descricao: String? = null
    var dataCriacao: Date? = null
    var dataEnvio: Date? = null
    var tipoSimulado: Int? = null

    var resultadoGeral: ResultQuantitative? = null
    var resultadoMatematica: ResultQuantitative? = null
    var resultadoFundamentoComputacao: ResultQuantitative? = null
    var resultadoTecnologiaComputacao: ResultQuantitative? = null

    object DataBase {
        fun save(resultOverall: ResultSimulated): Long {
            var _id = 1L
            var realm = Realm.getDefaultInstance()
            try {
                if(resultOverall._id == null) {
                    val maxId = realm.where(ResultSimulated::class.java).max("_id")
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

        fun update(resultOverall: ResultSimulated) : Long {
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

        fun loadById(id : Long) : ResultSimulated? {
            var obgect : ResultSimulated? = null
            var realm = Realm.getDefaultInstance()
            try {
                var realInfos = realm.where(ResultSimulated::class.java).equalTo("_id", id).findFirst()
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

        fun loadByIdUser(userId : Long) : MutableList<ResultSimulated>? {
            var obgect : MutableList<ResultSimulated>? = null
            var realm = Realm.getDefaultInstance()
            try {
                var realInfos = realm.where(ResultSimulated::class.java).equalTo("idUsuario", userId).findAll()
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

        fun loadByTypeAndIdUser(id : Long, userId : Long) : ResultSimulated? {
            var obgect : ResultSimulated? = null
            var realm = Realm.getDefaultInstance()
            try {
                var realInfos = realm.where(ResultSimulated::class.java).equalTo("_id", id)
                    .equalTo("idUsuario", userId).findFirst()
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
    }
}