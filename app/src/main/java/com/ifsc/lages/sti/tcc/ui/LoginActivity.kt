package com.ifsc.lages.sti.tcc.ui

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.ViewModelProvider
import androidx.transition.ArcMotion
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import br.com.sistemainfo.tip.utilities.KeyboardUtil
import br.edu.ifsc.cancontrol.utilidades.BaseActivty
import com.google.android.material.textfield.TextInputLayout
import com.ifsc.lages.sti.tcc.MainActivity
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.utilidades.ActivityUtil
import com.ifsc.lages.sti.tcc.utilidades.EditTextMask
import com.ifsc.lages.sti.tcc.utilidades.StringUtil

class LoginActivity : BaseActivty() {

    private val TAG = "LoginActivity"

    private var mShowingKeyboard: Boolean = false
    private var hasPermission = false
    private var firstPermission = true
    private var idUser : Long? = null
    private var login : Button? = null
    var loginUserHelper : TextInputLayout? = null
    var passwordHelper : TextInputLayout? = null
    var loginUser : EditText? = null
    var password : EditText? = null

    private var viewModel :  LoginViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun loginDataChanged(login: String, password: String) : Boolean {
        if (!login.isCpfCnpjValid()) {
            if (login.length <= 11) {
                loginUserHelper?.helperText = null
                loginUserHelper?.helperText = getString(R.string.error_login_invalido)
                return false
            }
        } else {
            loginUserHelper?.helperText = null
        }

        if (!isPasswordValid(password)) {
            passwordHelper?.helperText = null
            passwordHelper?.helperText =  getString(R.string.error_senha_invalida)
            return false
        } else {
            passwordHelper?.helperText = null
        }
        return true
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.isNotBlank() && password.length > 5
    }

    fun String.isCpfCnpjValid(): Boolean {
        return this.isNotBlank() && (StringUtil.isCpfValid(this) || StringUtil.isCNPJValid(this))
    }


   override fun createRestListener() {
       viewModel = ViewModelProvider(this, LoginViewModelFactory(this@LoginActivity)).get(LoginViewModel::class.java)
       viewModel!!.loginViewMonitoring.observe(this, androidx.lifecycle.Observer {
           hideLoading()
           if(it.error!!.not()) {
               ActivityUtil.Builder(applicationContext, MainActivity::class.java)
                   .build()
           } else {
               Toast.makeText(this@LoginActivity, it.message, Toast.LENGTH_LONG).show()
           }
       })
   }


    override fun mapComponents() {
        super.mapComponents()
        login = findViewById(R.id.login)
        loginUserHelper = findViewById(R.id.containerUsername)
        loginUser = findViewById(R.id.username)
        passwordHelper = findViewById(R.id.containerPassword)
        password = findViewById(R.id.password)

//        TODO usar este botão
        val chip = findViewById<SwitchCompat>(R.id.chip)
        val chipTouch = findViewById<SwitchCompat>(R.id.chip2)
        val recoveryLogin = findViewById<TextView>(R.id.recoveryPassword)
        val logoTip = findViewById<ImageView>(R.id.logoTip)
        val imageRegister = findViewById<ImageView>(R.id.imageRegister)
        val registerUser = findViewById<TextView>(R.id.txt_open_password)
        val containerRegister = findViewById<FrameLayout>(R.id.container_register)

        EditTextMask.addCpfMask(loginUser!!)

        password?.apply { transformationMethod = PasswordTransformationMethod() }
        findViewById<LinearLayout>(R.id.containerChipCpf).setOnClickListener {
            chip.performClick()
        }

        findViewById<LinearLayout>(R.id.containerChipTouch).setOnClickListener {
            chipTouch.performClick()
        }

        chip?.setOnCheckedChangeListener { _, isChecked -> }
        chipTouch?.setOnCheckedChangeListener { _, isChecked -> }

        loginUser?.afterTextChanged {
            login?.isEnabled = loginDataChanged(
                loginUser?.text.toString().removeMask(),
                password?.text.toString()
            )
        }

        password?.afterTextChanged {
            login?.isEnabled = loginDataChanged(
                loginUser?.text.toString().removeMask(),
                password?.text.toString()
            )
        }
    }

    override fun mapActionComponents() {
        login?.setOnClickListener {
            showLoading("Autenticando usuário")
            viewModel?.registerMonitoring(loginUser?.text.toString().removeMask(), password?.text.toString())
        }

        KeyboardUtil.addKeyboardToggleListener(
            this,
            object : KeyboardUtil.SoftKeyboardToggleListener {

                override fun onToggleSoftKeyboard(isVisible: Boolean) {
                    mShowingKeyboard = isVisible
                    Log.v("LOGIN", "onToggleSoftKeyboard() $isVisible")

                    Handler().postDelayed(
                        {
                            val constraintLayout = findViewById<ConstraintLayout>(R.id.container)
                            val constraintSet = ConstraintSet()
                            constraintSet.clone(constraintLayout)
                            if (isVisible) {
                                constraintSet.constrainHeight(R.id.logoTip, 80)
                                constraintSet.constrainWidth(R.id.logoTip, 80)
                                constraintSet.setMargin(R.id.logoTip, ConstraintSet.TOP, 8)
                                constraintSet.setHorizontalBias(R.id.logoTip, 1.0f)
                            } else {
                                constraintSet.clone(this@LoginActivity,
                                    R.layout.activity_login
                                )
                            }
                            val transition = ChangeBounds()
                            transition.setPathMotion(ArcMotion())
                            transition.duration = 400
                            transition.interpolator = AccelerateDecelerateInterpolator()

                            TransitionManager.beginDelayedTransition(constraintLayout, transition)
                            constraintSet.applyTo(constraintLayout)
                        }, 400
                    )
                }
            })
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.beforeTextChanged(beforeTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {}

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            beforeTextChanged.invoke(s.toString())
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}


fun String.removeMask(): String {
    return EditTextMask.removeCpfCnpjMask(this)
}