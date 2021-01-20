package com.ifsc.lages.sti.tcc.model.result

import android.os.Parcel
import android.os.Parcelable
import io.realm.RealmObject
import java.io.Serializable

open class ResultQuantitative() : RealmObject(), Serializable, Parcelable{

    var erros: Int? = null
    var acertos: Int? = null
    var naoRespondidas: Int? = null
    var total: Int? = null

    constructor(parcel: Parcel) : this() {
        erros = parcel.readValue(Int::class.java.classLoader) as? Int
        acertos = parcel.readValue(Int::class.java.classLoader) as? Int
        naoRespondidas = parcel.readValue(Int::class.java.classLoader) as? Int
        total = parcel.readValue(Int::class.java.classLoader) as? Int
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(erros)
        parcel.writeValue(acertos)
        parcel.writeValue(naoRespondidas)
        parcel.writeValue(total)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ResultQuantitative> {
        override fun createFromParcel(parcel: Parcel): ResultQuantitative {
            return ResultQuantitative(parcel)
        }

        override fun newArray(size: Int): Array<ResultQuantitative?> {
            return arrayOfNulls(size)
        }
    }
}


