package com.ifsc.lages.sti.tcc.resources.classroom

import com.ifsc.lages.sti.tcc.model.classrom.Classroom
import com.ifsc.lages.sti.tcc.resources.classroom.mapper.ClassroomMapper
import com.ifsc.lages.sti.tcc.resources.generics.BaseResponse
import com.ifsc.lages.sti.tcc.resources.simulated.SimuladoRequest
import com.ifsc.lages.sti.tcc.resources.simulated.SimuladoResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class ClassroomRepository {

    private val service: ClassroomService
        get() {
            return ClassroomService()
        }

    private fun createClassroom(request : ClassroomRequest.Register) : Single<BaseResponse<ClassroomResponse.SalaResponse>> {
        return Single.create {
            try {
                val response = service.createClassroom(request)
                when {
                    response.success!! -> {
                        it.onSuccess(response)
                    }
                    else -> it.onError(Exception(response.message))
                }
            } catch (ex : java.lang.Exception) {
                it.onError(Exception("Erro criar a sala de simulados"))
            }
        }
    }

    fun createClassroom(request : ClassroomRequest.Register, observer: DisposableObserver<Classroom>){
        createClassroom(request)
            .toObservable()
            .map { ClassroomMapper().transform(it.data) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }

    private fun searchClassroom(idUsuario : Long) : Single<BaseResponse<MutableList<ClassroomResponse.SalaResponse>>> {
        return Single.create {
            try {
                val response = service.searchClassroom(idUsuario)
                when {
                    response.success!! -> {
                        it.onSuccess(response)
                    }
                    else -> it.onError(Exception(response.message))
                }
            } catch (ex : java.lang.Exception) {
                it.onError(Exception("Erro ao consultas as salas"))
            }
        }
    }

    fun searchClassroom(idUsuario : Long, observer: DisposableObserver<MutableList<Classroom>>){
        searchClassroom(idUsuario)
            .toObservable()
            .map { ClassroomMapper().transform(it.data!!) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }


    private fun enterClassroom(request : ClassroomRequest.EnterClassroom) : Single<BaseResponse<MutableList<ClassroomResponse.SimuladoBaseResponse>>> {
        return Single.create {
            try {
                val response = service.enterClassroom(request)
                when {
                    response.success!! -> {
                        it.onSuccess(response)
                    }
                    else -> it.onError(Exception(response.message))
                }
            } catch (ex : java.lang.Exception) {
                it.onError(Exception("Erro criar a sala de simulados"))
            }
        }
    }

    fun enterClassroom(request : ClassroomRequest.EnterClassroom, observer: DisposableObserver<Boolean>){
        enterClassroom(request)
            .toObservable()
            .map { it.success }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }


    private fun deleteClassroom(request : ClassroomRequest.DeleteClassroom) : Single<BaseResponse<ClassroomResponse.SalaResponse>> {
        return Single.create {
            try {
                val response = service.deleteClassroom(request)
                when {
                    response.success!! -> {
                        it.onSuccess(response)
                    }
                    else -> it.onError(Exception(response.message))
                }
            } catch (ex : java.lang.Exception) {
                it.onError(Exception("Erro deletar a sala de simulado"))
            }
        }
    }

    fun deleteClassroom(request : ClassroomRequest.DeleteClassroom, observer: DisposableObserver<Classroom>){
        deleteClassroom(request)
            .toObservable()
            .map { ClassroomMapper().transform(it.data) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }
}