package com.ifsc.lages.sti.tcc.ui.registersala.classroom

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import br.edu.ifsc.cancontrol.utilidades.BaseActivty
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.model.user.User
import com.ifsc.lages.sti.tcc.resources.classroom.ClassroomRequest
import com.ifsc.lages.sti.tcc.ui.login.afterTextChanged
import com.ifsc.lages.sti.tcc.ui.registersala.viewmodel.ClassRoomViewModel
import com.ifsc.lages.sti.tcc.ui.registersala.viewmodel.ClassRoomViewModelFactory
import com.ifsc.lages.sti.tcc.utilidades.ConnectionUtil

class RegisterClassroomActivity : BaseActivty() {

    var idClasroom : Long? = null
    var button : MaterialButton? = null
    var editTextName : EditText? = null
    var editTextDescription : EditText? = null
    var editTextQTD : EditText? = null
    var editTextPassword : EditText? = null
    var editTextConfirmPasswod : EditText? = null

//
    var textInputName : TextInputLayout? = null
    var textInputDescription : TextInputLayout? = null
    var textInputQTD : TextInputLayout? = null
    var textInputPassword : TextInputLayout? = null
    var textInputConfirmPasswod : TextInputLayout? = null

    var request : ClassroomRequest.Register? = null
    var viewModel :  ClassRoomViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_classroom)
        setTitleToolbar(getString(R.string.title_toolbar_register_simulated))

        intent.extras?.let {
            idClasroom = intent.extras?.getLong("id_classroom")
        }
    }

    override fun mapComponents() {
        super.mapComponents()
        editTextName = findViewById(R.id.edit_text_name)
        editTextDescription = findViewById(R.id.edit_text_description)
        editTextQTD = findViewById(R.id.edit_text_qtd)
        editTextPassword = findViewById(R.id.edit_text_password)
        editTextConfirmPasswod = findViewById(R.id.edit_text_confirm_password)


        textInputName = findViewById(R.id.text_input_name)
        textInputDescription = findViewById(R.id.text_input_description)
        textInputQTD = findViewById(R.id.text_input_qtd)
        textInputPassword = findViewById(R.id.text_input_password)
        textInputConfirmPasswod = findViewById(R.id.text_input_confirm_password)

        button = findViewById(R.id.button_cadastre)
    }

    override fun mapActionComponents() {
        super.mapActionComponents()

        editTextName?.afterTextChanged {
            enableButtonNext()
        }

        editTextDescription?.afterTextChanged {
            enableButtonNext()
        }

        editTextQTD?.afterTextChanged {
            enableButtonNext()
        }

        editTextPassword?.afterTextChanged {
            enableButtonNext()
        }

        editTextConfirmPasswod?.afterTextChanged {
            enableButtonNext()
        }

        button?.setOnClickListener {
            showLoading("Registrado informações...")
            viewModel?.registerClassroom(request!!)
        }
    }

    fun enableButtonNext()  {
        button?.isEnabled = validateFileds()
    }

    fun validateFileds() : Boolean {
        if(validateName().not()) { return false }
        if(validateDescription().not()) { return false }
        if(validateQTD().not()) { return false }
        if(validatePassword().not()) { return false }
        if(validateConfirmPassword().not()) { return false }
        updateRequest()
        return true
    }

    fun validateName() : Boolean {
        if (editTextName!!.text.toString().isNotEmpty()) {
            textInputName?.helperText = null
            return true
        }
        textInputName?.helperText = null
        textInputName?.helperText = getString(R.string.error_invalid_name_simulated)
        return false
    }

    fun validateDescription() : Boolean {
        if (editTextDescription!!.text.toString().isNotEmpty()) {
            textInputDescription?.helperText = null
            return true
        }
        textInputDescription?.helperText = null
        textInputDescription?.helperText = getString(R.string.error_invalid_name_description)
        return false
    }


    fun validateQTD() : Boolean {
        if (editTextQTD!!.text.toString().isEmpty()) {
            textInputQTD?.helperText = null
            textInputQTD?.helperText = getString(R.string.error_invalid_qtd_students_empty)
            return false
        } else if (editTextQTD!!.text.toString().toInt() <= 2) {
            textInputQTD?.helperText = null
            textInputQTD?.helperText = getString(R.string.error_invalid_qtd_students)
            return false
        }
        textInputQTD?.helperText = null
        return true
    }

    fun validatePassword() : Boolean {
        var password = editTextPassword?.text.toString()
        textInputPassword?.helperText = null
        if (password.length < 6) {
            textInputPassword?.helperText = getString(R.string.error_invalid_password)
            return false
        }
        return true
    }

    fun validateConfirmPassword() : Boolean {
        var password = editTextPassword?.text.toString()
        var passwordConfirm = editTextConfirmPasswod?.text.toString()
        textInputConfirmPasswod?.helperText = null
        if (password.isEmpty()) {
            textInputConfirmPasswod?.helperText = getString(R.string.error_invalid_password)
            return false
        } else if (passwordConfirm.equals(password).not()) {
            textInputConfirmPasswod?.helperText = getString(R.string.error_invalid_confirm_password)
            return false
        } else {
            return true
        }
    }

    fun updateRequest() {
        request = ClassroomRequest.Register()
        val user = User.UserShared.load(this@RegisterClassroomActivity)
        request!!.idUsuario = user!!._id
        request!!.nome = editTextName!!.text.toString()
        request!!.descricao = editTextDescription!!.text.toString()
        request!!.maxParticipantes = editTextQTD!!.text.toString().toInt()
        request!!.senha = editTextConfirmPasswod!!.text.toString()
    }

    override fun createRestListener() {
        super.createRestListener()
        viewModel =
            ViewModelProvider(this, ClassRoomViewModelFactory(this@RegisterClassroomActivity)).get(
                ClassRoomViewModel::class.java
            )
        viewModel!!.registerClassroom.observe(this, androidx.lifecycle.Observer {
            hideLoading()
            if (it.error!!.not()) {
                finish()
            } else {
                Toast.makeText(this@RegisterClassroomActivity, it.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}