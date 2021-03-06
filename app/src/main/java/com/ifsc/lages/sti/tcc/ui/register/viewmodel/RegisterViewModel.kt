package com.ifsc.lages.sti.tcc.ui.register.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.model.user.EducationalInstitution
import com.ifsc.lages.sti.tcc.model.user.User
import com.ifsc.lages.sti.tcc.resources.BaseView
import com.ifsc.lages.sti.tcc.resources.education.InstitutionRepository
import com.ifsc.lages.sti.tcc.resources.user.UserRepository
import com.ifsc.lages.sti.tcc.utilidades.ConnectionUtil
import com.ifsc.lages.sti.tcc.utilidades.KeyPrefs
import com.ifsc.lages.sti.tcc.utilidades.SharedPreferencesUtil
import io.reactivex.observers.DisposableObserver

class RegisterViewModel (var activity: Context) : ViewModel() {

    var repository1 : UserRepository = UserRepository()
    var repository2 : InstitutionRepository = InstitutionRepository()

    val _registerUserView = MutableLiveData<BaseView<User>>()
    var registerUserView : LiveData<BaseView<User>> = _registerUserView

    val _queryInstitution = MutableLiveData<BaseView<MutableList<EducationalInstitution>>>()
    var queryInstitution : LiveData<BaseView<MutableList<EducationalInstitution>>> = _queryInstitution

    val _updateUserView = MutableLiveData<BaseView<User>>()
    var updateUserView : LiveData<BaseView<User>> = _updateUserView

    fun registerUser(user : User, password : String) {
        repository1.register(user, password, object : DisposableObserver<User>() {
            override fun onComplete() {

            }

            override fun onNext(t: User) {
                User.UserShared.save(activity, t)
                SharedPreferencesUtil.put(activity, KeyPrefs.USER_REMEMBER_CPF, t.cpf)
                _registerUserView.value = BaseView(success = t, error = false, message = "Usuário cadastrado com sucesso")
            }

            override fun onError(e: Throwable) {
                var msm = e.message
                if (ConnectionUtil.isNetworkAvailable(activity).not()) {
                    msm = activity.getString(R.string.error_conection)
                }
                _registerUserView.value = BaseView(success = null, error = true, message = msm)
            }
        })
    }

    fun updateUser(user : User, password : String) {
        repository1.update(user, password, object : DisposableObserver<User>() {
            override fun onComplete() {

            }

            override fun onNext(t: User) {
                User.UserShared.save(activity, t)
                _updateUserView.value = BaseView(success = t, error = false, message = "Usuário alterado com sucesso")
            }

            override fun onError(e: Throwable) {
                var msm = e.message
                if (ConnectionUtil.isNetworkAvailable(activity).not()) {
                    msm = activity.getString(R.string.error_conection)
                }
                _updateUserView.value = BaseView(success = null, error = true, message = msm)
            }
        })
    }

    fun registerMonitoring() {
        repository2.queryInstitution(object : DisposableObserver<MutableList<EducationalInstitution>>() {

            override fun onComplete() {
            }

            override fun onNext(t: MutableList<EducationalInstitution>) {
                EducationalInstitution.DB.deleteAll()
                EducationalInstitution.DB.insertOrUpdateAll(t)
                _queryInstitution.value = BaseView(success = t, error = false, message = "Instituições carregadas com sucesso")
            }

            override fun onError(e: Throwable) {
                var msm = e.message
                if (ConnectionUtil.isNetworkAvailable(activity).not()) {
                    msm = activity.getString(R.string.error_conection)
                }
                _queryInstitution.value = BaseView(success = null, error = true, message = msm)
            }
        })
    }
}