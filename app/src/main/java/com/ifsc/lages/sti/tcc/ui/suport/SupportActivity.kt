package com.ifsc.lages.sti.tcc.ui.suport

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import br.edu.ifsc.cancontrol.utilidades.BaseActivty
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.model.user.User
import com.ifsc.lages.sti.tcc.props.ESupport
import com.ifsc.lages.sti.tcc.props.EUserType
import com.ifsc.lages.sti.tcc.ui.login.afterTextChanged
import com.ifsc.lages.sti.tcc.ui.suport.bottomsheet.BottonSheetSupportFragment
import com.ifsc.lages.sti.tcc.ui.suport.viewmodel.SupportViewModel
import com.ifsc.lages.sti.tcc.ui.suport.viewmodel.SupportViewModelFactory

class SupportActivity : BaseActivty(), BottonSheetSupportFragment.CallbackOptions {

    var viewModel : SupportViewModel ? = null
    var editTextAssunto : EditText? = null
    var editTextOutroAssunto: EditText? = null
    var editTextMensagem : EditText? = null

    var button : MaterialButton? = null

    var textInputAssunto : TextInputLayout? = null
    var textInputOutroAssunto : TextInputLayout? = null
    var textInputMensagem : TextInputLayout? = null
    var bottonSheetSupportFragment : BottonSheetSupportFragment? = null

    var eSupport: ESupport? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_support)
    }

    override fun mapComponents() {
        super.mapComponents()
        setTitleToolbar(getString(R.string.title_toolbar_support))

        editTextAssunto = findViewById<EditText>(R.id.edit_text_assunto)
        editTextOutroAssunto = findViewById<EditText>(R.id.edit_text_outro_assunto)
        editTextMensagem = findViewById<EditText>(R.id.edit_text_mensagem)

        textInputAssunto = findViewById<TextInputLayout>(R.id.text_input_assunto)
        textInputOutroAssunto = findViewById<TextInputLayout>(R.id.text_input_outro_assunto)
        textInputMensagem = findViewById<TextInputLayout>(R.id.text_input_mensagem)

        button = findViewById(R.id.button_cadastre)
    }

    override fun mapActionComponents() {
        super.mapActionComponents()

        editTextAssunto?.setOnClickListener {
            showBottonSheet()
        }

        editTextAssunto?.afterTextChanged {
            enableButtonNext()
        }

        editTextOutroAssunto?.afterTextChanged {
            enableButtonNext()
        }

        editTextMensagem?.afterTextChanged {
            enableButtonNext()
        }

        button?.setOnClickListener {
            showLoading("Enviando informações...")
            var mensagem = editTextMensagem!!.text.toString() + "\n"

            mensagem += "\n Contato Usuário"
            var user = User.UserShared.load(this@SupportActivity)
            if(user!!.name.isNullOrEmpty().not()) {
                mensagem += "\n Nome: " + user!!.name
            }

            if(user!!.email.isNullOrEmpty().not()) {
                mensagem += "\n Email: " + user!!.email
            }

            if(user!!.educationalInstitution != null) {
                mensagem += "\n Instituição: " + user!!.educationalInstitution!!.name
            }

            if(user!!.userType != null) {
                mensagem += "\n Usuário: " + EUserType.getUserType(user!!.userType!!).description
            }

            if(user!!.phone.isNullOrEmpty().not()) {
                mensagem += "\n Telefone:" + user!!.phone!!
            }

            var title = editTextAssunto!!.text.toString()
            if(eSupport == ESupport.OUTRO) {
                title += " - " + editTextOutroAssunto!!.text.toString()
            }
            viewModel?.send(title, mensagem)
        }
    }


   fun showBottonSheet() {
       bottonSheetSupportFragment = BottonSheetSupportFragment.newInstance()
       bottonSheetSupportFragment!!.show(supportFragmentManager, null)
       bottonSheetSupportFragment!!.setListener(this)
    }

    override fun onClick(eSupport: ESupport?) {
        this.eSupport = eSupport
        bottonSheetSupportFragment?.dismiss()
        if(ESupport.OUTRO == eSupport) {
            textInputOutroAssunto!!.visibility = View.VISIBLE
            editTextAssunto!!.setText(eSupport.descricao)
            editTextOutroAssunto!!.setText("")
            editTextOutroAssunto!!.requestFocus()
            textInputMensagem?.helperText = null
        } else {
            textInputOutroAssunto!!.visibility = View.GONE
            editTextAssunto!!.setText(eSupport!!.descricao)
            editTextOutroAssunto!!.setText(eSupport.descricao)
            editTextMensagem!!.requestFocus()
        }
    }

    override fun createRestListener() {
        super.createRestListener()
        viewModel = ViewModelProvider(this, SupportViewModelFactory(this@SupportActivity)).get(SupportViewModel::class.java)
        viewModel!!.sendMsm.observe(this, androidx.lifecycle.Observer {
            hideLoading()
            if (it.error!!.not()) {
                Toast.makeText(this@SupportActivity, "Informações enviadas com sucesso", Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(this@SupportActivity, it.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    fun validateAssunto() : Boolean {
        textInputAssunto?.helperText = null
        if (this.eSupport == null) {
            textInputAssunto?.helperText = getString(R.string.error_invalid_topic)
            return false
        }
        return true
    }

    fun validateOutroAssunto() : Boolean {
        var outroAssunto = editTextOutroAssunto?.text.toString()
        textInputOutroAssunto?.helperText = null
        if (outroAssunto.isEmpty()) {
            textInputOutroAssunto?.helperText = getString(R.string.error_empty_topic)
            return false
        }
        return true
    }

    fun validateMensagem() : Boolean {
        var mensagem = editTextMensagem?.text.toString()
        textInputMensagem?.helperText = null
        if (mensagem.isEmpty()) {
            textInputMensagem?.helperText = getString(R.string.error_msm_empty)
            return false
        } else if(mensagem.length < 5) {
            textInputMensagem?.helperText = getString(R.string.error_msm_legth)
            return false
        }
        return true
    }

    fun enableButtonNext()  {
        button?.isEnabled = validateFileds()
    }

    fun validateFileds() : Boolean {
        if(validateAssunto().not()) { return false }
        if(validateOutroAssunto().not()) { return false }
        if(validateMensagem().not()) { return false }
        return true
    }
}
