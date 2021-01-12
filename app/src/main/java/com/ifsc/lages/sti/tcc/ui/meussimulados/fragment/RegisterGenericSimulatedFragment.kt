package com.ifsc.lages.sti.tcc.ui.meussimulados.fragment

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.ifsc.lages.sti.tcc.BuildConfig
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.model.user.EducationalInstitution
import com.ifsc.lages.sti.tcc.model.user.User
import com.ifsc.lages.sti.tcc.props.EDisciplina
import com.ifsc.lages.sti.tcc.props.ETipoSimulado
import com.ifsc.lages.sti.tcc.props.EUserType
import com.ifsc.lages.sti.tcc.ui.login.afterTextChanged
import com.ifsc.lages.sti.tcc.ui.meussimulados.bottomsheet.BottonSheetSimulatedTypeFragment
import com.ifsc.lages.sti.tcc.ui.register.adapter.MatterInfo
import com.ifsc.lages.sti.tcc.ui.register.bottomsheet.BottonSheetEducationInstitutionFragment
import com.ifsc.lages.sti.tcc.ui.register.bottomsheet.BottonSheetUserTypeFragment
import com.ifsc.lages.sti.tcc.ui.register.viewmodel.RegisterViewModel
import com.ifsc.lages.sti.tcc.ui.register.viewmodel.RegisterViewModelFactory
import com.ifsc.lages.sti.tcc.utilidades.*
import com.ifsc.lages.sti.tcc.utilidades.baseview.BaseFragment
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RegisterGenericSimulatedFragment : BaseFragment(), BottonSheetSimulatedTypeFragment.CallbackOptions {

    var button : MaterialButton? = null
    var editTextName : EditText? = null
    var editTextDescription : EditText? = null
    var editTextType : EditText? = null

    var textInputName : TextInputLayout? = null
    var textInputDescription : TextInputLayout? = null
    var textInputType : TextInputLayout? = null

    private var bottonSheetSimulatedTypeFragment : BottonSheetSimulatedTypeFragment? = null
    private var progressBar :  ProgressBar? = null

    var tipoSimulado: ETipoSimulado? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register_simulated_generic, container, false)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

    }

    override fun mapComponents() {
        editTextName = view?.findViewById(R.id.edit_text_name)
        editTextDescription = view?.findViewById(R.id.edit_text_description)
        editTextType = view?.findViewById(R.id.edit_text_type)
        button = view?.findViewById(R.id.button_cadastre)

        textInputName = view?.findViewById(R.id.text_input_name)
        textInputDescription = view?.findViewById(R.id.text_input_description)
        textInputType = view?.findViewById(R.id.text_input_type)
    }

    override fun mapActionComponents() {
        button?.setOnClickListener {
            if(tipoSimulado!!.codigo == ETipoSimulado.DEFAULT.codigo) {
                view?.findNavController()!!.navigate(R.id.action_genericFragment_to_registerSimulatedFragment)
            } else {
//                button?.setText(getString(R.string.prompt_register_simulated))
            }
        }

        editTextName?.afterTextChanged {enableButtonNext()}
        editTextDescription?.afterTextChanged {enableButtonNext()}
//        editTextType?.afterTextChanged {enableButtonNext()}

        editTextType?.setOnClickListener {
            showDialogSelected()
        }
    }

    fun showDialogSelected() {
        bottonSheetSimulatedTypeFragment = BottonSheetSimulatedTypeFragment.newInstance()
        bottonSheetSimulatedTypeFragment!!.show(activity!!.supportFragmentManager, null)
        bottonSheetSimulatedTypeFragment!!.setListener(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapComponents()
        mapActionComponents()
    }

    fun enableButtonNext() {
        button?.isEnabled = validateFileds()
    }

    fun validateFileds() : Boolean {
        if(validateName().not()) { return false }
        if(validateDescription().not()) { return false }
        if(validateTypeSimulated().not()) { return false }
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

    fun validateTypeSimulated() : Boolean {
        var userType = editTextType?.text.toString()
        if (userType.isNotEmpty()) {
            textInputType?.helperText = null
            return true
        }
        textInputType?.helperText = null
        textInputType?.helperText = getString(R.string.error_invalid_simulated_type)
        return false
    }

    override fun onClick(tipoSimulado: ETipoSimulado?) {
        this.tipoSimulado = tipoSimulado
        bottonSheetSimulatedTypeFragment?.dismiss()
        editTextType?.setText(tipoSimulado!!.descricao.toUpperCase(Locale.getDefault()))
        if(tipoSimulado!!.codigo == ETipoSimulado.DEFAULT.codigo) {
            button?.setText(getString(R.string.prompt_next))
        } else {
            button?.setText(getString(R.string.prompt_register_simulated))
        }
        enableButtonNext()
    }
}

fun String.isValidFullName(): Boolean {
    val moreThanOneName = this.split("\\s+".toRegex()).size != 1
    if (moreThanOneName) {
        return this.split("\\s+".toRegex())[1].isNotEmpty()
    } else {
        return false
    }
}