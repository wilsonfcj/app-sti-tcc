package com.ifsc.lages.sti.tcc.model.result

import android.os.Parcel
import android.os.Parcelable
import io.realm.Realm
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.io.Serializable

open class ResultMatters() : RealmObject(), Serializable, Parcelable {

    @PrimaryKey
    var _id : String? = null
    var idusuario : Long? = null
    var disciplinaCod : Int? = null
    var disciplinaDes : String? = null

    var erros: Int? = null
    var acertos: Int? = null
    var naoRespondidas: Int? = null
    var total: Int? = null

    constructor(parcel: Parcel) : this() {
        _id = parcel.readString()
        idusuario = parcel.readValue(Long::class.java.classLoader) as? Long
        disciplinaCod = parcel.readValue(Int::class.java.classLoader) as? Int
        disciplinaDes = parcel.readString()
        erros = parcel.readValue(Int::class.java.classLoader) as? Int
        acertos = parcel.readValue(Int::class.java.classLoader) as? Int
        naoRespondidas = parcel.readValue(Int::class.java.classLoader) as? Int
        total = parcel.readValue(Int::class.java.classLoader) as? Int
    }

    object DataBase {
        fun save(resultOverall: ResultMatters): String {
            var _id = resultOverall._id
            var realm = Realm.getDefaultInstance()
            try {
                realm.beginTransaction()
                var transactionMonitoring = realm.copyToRealmOrUpdate(resultOverall)
                _id = transactionMonitoring?._id!!
                realm.commitTransaction()
            } catch (ex: Exception) {
                ex.printStackTrace()
            } finally {
                realm.close()
                return _id!!
            }
        }

        fun update(resultOverall: ResultMatters) : String {
            var _id = ""
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

        fun loadByIdUser(userId : Long) : MutableList<ResultMatters>? {
            var obgect : MutableList<ResultMatters>? = null
            var realm = Realm.getDefaultInstance()
            try {
                var realInfos = realm.where(ResultMatters::class.java).equalTo("idusuario", userId).findAll()
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

        fun deleteById(id : String) :  ResultMatters? {
            var obgect : ResultMatters? = null
            var realm = Realm.getDefaultInstance()
            try {
                var realInfos = realm.where(ResultMatters::class.java).equalTo("_id", id).findFirst()
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

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(_id)
        parcel.writeValue(idusuario)
        parcel.writeValue(disciplinaCod)
        parcel.writeString(disciplinaDes)
        parcel.writeValue(erros)
        parcel.writeValue(acertos)
        parcel.writeValue(naoRespondidas)
        parcel.writeValue(total)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ResultMatters> {
        override fun createFromParcel(parcel: Parcel): ResultMatters {
            return ResultMatters(parcel)
        }

        override fun newArray(size: Int): Array<ResultMatters?> {
            return arrayOfNulls(size)
        }
    }
}