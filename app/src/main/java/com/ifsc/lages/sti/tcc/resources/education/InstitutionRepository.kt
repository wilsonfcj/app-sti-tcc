package com.ifsc.lages.sti.tcc.resources.education

import com.ifsc.lages.sti.tcc.model.user.EducationalInstitution
import com.ifsc.lages.sti.tcc.resources.generics.BaseResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class InstitutionRepository {

    private val service: InstitutionService
        get() {
            return InstitutionService()
        }

    fun queryInstitution() : Single<BaseResponse<MutableList<InstitutionResponse.EducationalInstitution>>> {
        return Single.create {
            try {
                val response = service.queryInstitution()
                when {
                    response.success!! -> {
                        it.onSuccess(response)
                    }
                    else -> it.onError(Exception(response.message))
                }
            } catch (ex : java.lang.Exception) {
                it.onError(Exception("Erro ao consultar as instituições"))
            }
        }
    }

    fun queryInstitution(observer: DisposableObserver<MutableList<EducationalInstitution>>){
        queryInstitution()
            .toObservable()
            .map { EducationalInstitution.EducationalInstitutionMappper.transform(it.data!!) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }
}
