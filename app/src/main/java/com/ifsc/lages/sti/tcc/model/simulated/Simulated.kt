package com.ifsc.lages.sti.tcc.model.simulated

import com.ifsc.lages.sti.tcc.model.result.ResultSimulated
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.Sort
import io.realm.annotations.PrimaryKey
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

open class Simulated : RealmObject(), Serializable {

    @PrimaryKey
    var _id: Long? = null
    var idSala: Long? = null
    var nome: String? = null
    var descricao: String? = null
    var dataInicio: Date? = null
    var dataCriacao: Date? = null
    var dataFimSimulado: Date? = null
    var tempoMaximo: Long? = null
    var idUsuario: Long? = null
    var quantidadeQuestoes: Int? = null
    var tipoSimulado: Int? = null
    var respondido = false
    var simuladoResultado : ResultSimulated? = null
    var quantidadeResposta : Int? = null
    var questoes : RealmList<Question>? = null

    object DataBase {
        fun save(resultOverall: Simulated): Long {
            var _id = 1L
            var realm = Realm.getDefaultInstance()
            try {
                if(resultOverall._id == null) {
                    val maxId = realm.where(Simulated::class.java).max("_id")
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

        fun update(resultOverall: Simulated) : Long {
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

        fun loadById(id : Long) : Simulated? {
            var obgect : Simulated? = null
            var realm = Realm.getDefaultInstance()
            try {
                var realInfos = realm.where(Simulated::class.java).equalTo("_id", id).findFirst()
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

        fun loadByIdUser(userId : Long) : MutableList<Simulated>? {
            var obgect : MutableList<Simulated>? = null
            var realm = Realm.getDefaultInstance()
            try {
                var realInfos = realm.where(Simulated::class.java).equalTo("idUsuario", userId)
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

        fun loadByTypeAndIdUser(id : Long, userId : Long) : Simulated? {
            var obgect : Simulated? = null
            var realm = Realm.getDefaultInstance()
            try {
                var realInfos = realm.where(Simulated::class.java).equalTo("_id", id)
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