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

    fun loginUser(cpf : String, password: String) : Single<BaseResponse<UserResponse.Login>> {
        var request = UserRequest.Login(cpf = cpf, password = password)
        return Single.create {
            try {
                val response = service.loginUser(request)
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

    fun loginUser(cpf : String, password: String, observer: DisposableObserver<User>){
        loginUser(cpf, password)
            .toObservable()
            .map { User.UserMappper.transform(it.data!!) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }
}