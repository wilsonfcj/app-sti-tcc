package com.ifsc.lages.sti.tcc.resources.user

import com.ifsc.lages.sti.tcc.model.user.User
import com.ifsc.lages.sti.tcc.resources.generics.BaseResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class UserRepository {

    private val service: UserService
        get() {
            return UserService()
        }

    private fun login(cpf : String, password: String) : Single<BaseResponse<UserResponse.Login>> {
        var request = UserRequest.Login(cpf = cpf, password = password)
        return Single.create {
            try {
                val response = service.login(request)
                when {
                    response.success!! -> {
                        it.onSuccess(response)
                    }
                    else -> it.onError(Exception(response.message))
                }
            } catch (ex : java.lang.Exception) {
                it.onError(Exception("Erro ao autenticar o usuário"))
            }
        }
    }

    fun login(cpf : String, password: String, observer: DisposableObserver<User>){
        login(cpf, password)
            .toObservable()
            .map { User.UserMappper.transform(it.data!!) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }

    private fun register(user: User, password: String) : Single<BaseResponse<UserResponse.Login>> {
        var request = UserRequest.Register().transform(user)
        request.password = password
        return Single.create {
            try {
                val response = service.register(request)
                when {
                    response.success!! -> {
                        it.onSuccess(response)
                    }
                    else -> it.onError(Exception(response.message))
                }
            } catch (ex : java.lang.Exception) {
                it.onError(Exception("Erro ao registrar o usuário"))
            }
        }
    }

    fun register(user: User, password: String, observer: DisposableObserver<User>){
        register(user, password)
            .toObservable()
            .map { User.UserMappper.transform(it.data!!) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }

    private fun update(user: User, password: String) : Single<BaseResponse<UserResponse.Login>> {
        var request = UserRequest.Register().transform(user)
        if(password.isNotEmpty())
            request.password = password
        return Single.create {
            try {
                val response = service.update(request)
                when {
                    response.success!! -> {
                        it.onSuccess(response)
                    }
                    else -> it.onError(Exception(response.message))
                }
            } catch (ex : java.lang.Exception) {
                it.onError(Exception("Erro ao atualizar o registro"))
            }
        }
    }

    fun update(user: User, password: String, observer: DisposableObserver<User>){
        update(user, password)
            .toObservable()
            .map { User.UserMappper.transform(it.data!!) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }
}