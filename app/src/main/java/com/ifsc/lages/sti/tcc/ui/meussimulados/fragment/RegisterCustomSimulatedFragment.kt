package com.ifsc.lages.sti.tcc.ui.meussimulados.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.model.user.User
import com.ifsc.lages.sti.tcc.props.ETipoSimulado
import com.ifsc.lages.sti.tcc.resources.simulated.SimuladoRequest
import com.ifsc.lages.sti.tcc.ui.login.afterTextChanged
import com.ifsc.lages.sti.tcc.ui.meussimulados.viewmodel.MeusSimuladosViewModel
import com.ifsc.lages.sti.tcc.ui.meussimulados.viewmodel.MeusSimuladosViewModelFactory
import com.ifsc.lages.sti.tcc.utilidades.KeyPrefs
import com.ifsc.lages.sti.tcc.utilidades.SharedPreferencesUtil
import com.ifsc.lages.sti.tcc.utilidades.StringUtil
import com.ifsc.lages.sti.tcc.utilidades.baseview.BaseFragment
import java.util.*

class RegisterCustomSimulatedFragment : BaseFragment() {

    var button : MaterialButton? = null
    var  register : SimuladoRequest.Register? =  null
    var editTextTime : EditText? = null
    var editTextFundamentos : EditText? = null
    var editTextMatematica : EditText? = null
    var editTextTecnologia : EditText? = null

    var editTextFormacaoGeral : EditText? = null
    var editTextFormacaoEspecifica : EditText? = null

    var textInputTime : TextInputLayout? = null
    var textInputFundamentos : TextInputLayout? = null
    var textInputMatematica : TextInputLayout? = null
    var textInputTecnologia : TextInputLayout? = null

    var textInputFormacaoGeral : TextInputLayout? = null
    var textInputFormacaoEspecific : TextInputLayout? = null

    var simuladoConfigPoscomp: SimuladoRequest.PoscompInfosRegister? = null
    var simuladoConfigEnade: SimuladoRequest.EnadeInfosRegister? = null
    var meusSimuladosViewModel : MeusSimuladosViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            register = it.getSerializable("register_simulated") as SimuladoRequest.Register?
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register_simulated_custom, container, false)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

    }

    override fun mapComponents() {
        editTextTime = view?.findViewById(R.id.edit_text_time)
        editTextFundamentos = view?.findViewById(R.id.edit_text_fundamentos)
        editTextMatematica = view?.findViewById(R.id.edit_text_matematica)
        editTextTecnologia = view?.findViewById(R.id.edit_text_tecnologia)

        editTextFormacaoGeral = view?.findViewById(R.id.edit_text_form_geral)
        editTextFormacaoEspecifica = view?.findViewById(R.id.edit_text_form_especifica)

        textInputTime = view?.findViewById(R.id.text_input_time)
        textInputFundamentos = view?.findViewById(R.id.text_input_fundamentos)
        textInputMatematica = view?.findViewById(R.id.text_input_matematica)
        textInputTecnologia = view?.findViewById(R.id.text_input_tecnologia)

        textInputFormacaoGeral = view?.findViewById(R.id.text_input_form_geral)
        textInputFormacaoEspecific = view?.findViewById(R.id.text_input_form_especifica)
        button = view?.findViewById(R.id.button_cadastre)
    }

    override fun mapActionComponents() {
        editTextTime?.afterTextChanged {enableButtonNext()}
        editTextFundamentos?.afterTextChanged {enableButtonNext()}
        editTextMatematica?.afterTextChanged {enableButtonNext()}
        editTextTecnologia?.afterTextChanged {enableButtonNext()}
        editTextFormacaoGeral?.afterTextChanged {enableButtonNext()}
        editTextFormacaoEspecifica?.afterTextChanged {enableButtonNext()}

        button?.setOnClickListener {
            showLoading("Registrando Simulado")
            meusSimuladosViewModel?.createSimulated(register!!)
        }
    }

    fun validateFileds() : Boolean {
        if(validateTime().not()) { return false }
        if(validateFundamentos().not()) { return false }
        if(validateMatematica().not()) { return false }
        if(validateTecnologia().not()) { return false }
        if(validateFormGeral().not()) { return false }
        if(validateFormEspecifica().not()) { return false }
        if(validateAllFilds().not()) { return false }

        register?.tempoMaximo = editTextTime?.text.toString().toLong()
        register?.sumuladoConfigEnade = simuladoConfigEnade
        register?.sumuladoConfigPoscomp = simuladoConfigPoscomp
        return true
    }

    fun enableButtonNext() {
        button?.isEnabled = validateFileds()
    }

    fun validateTime() : Boolean {
        var valor = editTextTime?.text.toString()
        textInputTime?.helperText = null
        if (valor.isEmpty()) {
            textInputTime?.helperText = "Informe o tempo de simulado"
            return false
        }  else if(valor.toInt() <= 5) {
            textInputTime?.helperText = "O tempo deve ser maior do que 5 minutos"
            return false
        }
        register?.tempoMaximo = valor.toLong()
        return true
    }

    fun createSimulatedPoscomp() {
        if(simuladoConfigPoscomp == null) {
            simuladoConfigPoscomp = SimuladoRequest.PoscompInfosRegister()
        }
    }

    fun createSimulatedEnade() {
        if(simuladoConfigEnade == null) {
            simuladoConfigEnade = SimuladoRequest.EnadeInfosRegister()
        }
    }

    fun validateFundamentos() : Boolean {
        var valor = editTextFundamentos?.text.toString()
        textInputFundamentos?.helperText = null
        if (valor.isNotEmpty()) {
            if(valor.toInt() > 30) {
                textInputFundamentos?.helperText = "O número máximo questões é 30"
                return false
            } else {
                createSimulatedPoscomp()
                simuladoConfigPoscomp!!.qtdFundamentos = valor.toInt()
            }
        }
        return true
    }

    fun validateMatematica() : Boolean {
        var valor = editTextMatematica?.text.toString()
        textInputMatematica?.helperText = null
        if (valor.isNotEmpty()) {
            if(valor.toInt() > 30) {
                textInputMatematica?.helperText = "O número máximo questões é 30"
                return false
            } else {
                createSimulatedPoscomp()
                simuladoConfigPoscomp!!.qtdMatematica = valor.toInt()
            }
        }
        return true
    }

    fun validateTecnologia() : Boolean {
        var valor = editTextTecnologia?.text.toString()
        textInputTecnologia?.helperText = null
        if (valor.isNotEmpty()) {
            if(valor.toInt() > 30) {
                textInputTecnologia?.helperText = "O número máximo questões é 30"
                return false
            } else {
                createSimulatedPoscomp()
                simuladoConfigPoscomp!!.qtdTecnologia = valor.toInt()
            }
        }
        return true
    }

    fun validateFormGeral() : Boolean {
        var valor = editTextFormacaoGeral?.text.toString()
        textInputFormacaoGeral?.helperText = null
        if (valor.isNotEmpty()) {
            if(valor.toInt() > 30) {
                textInputFormacaoGeral?.helperText = "O número máximo questões é 30"
                return false
            } else {
                createSimulatedEnade()
                simuladoConfigEnade!!.qtdFormacaoGeral = valor.toInt()
            }
        }
        return true
    }

    fun validateFormEspecifica() : Boolean {
        var valor = editTextFormacaoEspecifica?.text.toString()
        textInputFormacaoEspecific?.helperText = null
        if (valor.isNotEmpty()) {
            if(valor.toInt() > 30) {
                textInputFormacaoEspecific?.helperText = "O número máximo questões é 30"
                return false
            } else {
                createSimulatedEnade()
                simuladoConfigEnade!!.qtdFormacaoEspecifica = valor.toInt()
            }
        }
        return true
    }

    fun validateAllFilds() : Boolean {
        var valor1 = editTextFundamentos?.text.toString()
        var valor2 = editTextTecnologia?.text.toString()
        var valor3 = editTextMatematica?.text.toString()

        var valor4 = editTextFormacaoGeral?.text.toString()
        var valor5 = editTextFormacaoEspecifica?.text.toString()

        if (valor1.isEmpty() && valor2.isEmpty() && valor3.isEmpty() && valor4.isEmpty() && valor5.isEmpty()) {
            return false
        }
        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapComponents()
        mapActionComponents()
        createRestListener()
    }

    override fun createRestListener() {
        super.createRestListener()
        meusSimuladosViewModel = ViewModelProvider(this, MeusSimuladosViewModelFactory(activity!!)).get(MeusSimuladosViewModel::class.java)
        meusSimuladosViewModel!!.simuladoCompletoRegister.observe(this, androidx.lifecycle.Observer {
            hideLoading()
            if(it.error!!.not()) {
                Toast.makeText(activity, "Simulado registrado com sucesso", Toast.LENGTH_LONG).show()
                activity?.finish()
            } else {
                Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}