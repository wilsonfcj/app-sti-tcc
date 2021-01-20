package com.ifsc.lages.sti.tcc.model.result

import android.os.Parcel
import android.os.Parcelable
import com.ifsc.lages.sti.tcc.model.simulated.Question
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.io.Serializable
import java.util.*

open class ResultSimulated() : RealmObject(), Serializable, Parcelable {

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
    var resultadoMatters : RealmList<ResultMatters>? = null

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

    constructor(parcel: Parcel) : this() {
        _id = parcel.readValue(Long::class.java.classLoader) as? Long
        idUsuario = parcel.readValue(Long::class.java.classLoader) as? Long
        idSimulado = parcel.readValue(Long::class.java.classLoader) as? Long
        nome = parcel.readString()
        descricao = parcel.readString()
        dataCriacao = parcel.readValue(Date::class.java.classLoader) as? Date
        dataEnvio = parcel.readValue(Date::class.java.classLoader) as? Date
        tipoSimulado = parcel.readValue(Int::class.java.classLoader) as? Int

        resultadoGeral = parcel.readValue(ResultQuantitative::class.java.classLoader) as? ResultQuantitative
        resultadoMatematica = parcel.readValue(ResultQuantitative::class.java.classLoader) as? ResultQuantitative
        resultadoFundamentoComputacao = parcel.readValue(ResultQuantitative::class.java.classLoader) as? ResultQuantitative
        resultadoTecnologiaComputacao = parcel.readValue(ResultQuantitative::class.java.classLoader) as? ResultQuantitative

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(_id)
        parcel.writeValue(idUsuario)
        parcel.writeValue(idSimulado)
        parcel.writeString(nome)
        parcel.writeString(descricao)
        parcel.writeValue(tipoSimulado)
        parcel.writeValue(dataCriacao)
        parcel.writeValue(dataEnvio)

        parcel.writeValue(resultadoGeral)
        parcel.writeValue(resultadoMatematica)
        parcel.writeValue(resultadoFundamentoComputacao)
        parcel.writeValue(resultadoTecnologiaComputacao)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ResultSimulated> {
        override fun createFromParcel(parcel: Parcel): ResultSimulated {
            return ResultSimulated(parcel)
        }

        override fun newArray(size: Int): Array<ResultSimulated?> {
            return arrayOfNulls(size)
        }
    }
}