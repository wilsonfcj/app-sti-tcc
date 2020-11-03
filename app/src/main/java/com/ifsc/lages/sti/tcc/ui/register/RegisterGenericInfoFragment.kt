package com.ifsc.lages.sti.tcc.ui.register

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import br.edu.ifsc.cancontrol.ui.monitoring.adapter.InstitutionAdapter
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.model.user.EducationalInstitution
import com.ifsc.lages.sti.tcc.model.user.User
import com.ifsc.lages.sti.tcc.props.EUserType
import com.ifsc.lages.sti.tcc.ui.login.afterTextChanged
import com.ifsc.lages.sti.tcc.ui.register.bottomsheet.BottonSheetEducationInstitutionFragment
import com.ifsc.lages.sti.tcc.ui.register.bottomsheet.BottonSheetUsetTypeFragment
import com.ifsc.lages.sti.tcc.ui.register.viewmodel.RegisterViewModel
import com.ifsc.lages.sti.tcc.ui.register.viewmodel.RegisterViewModelFactory
import com.ifsc.lages.sti.tcc.utilidades.BaseFragment
import com.ifsc.lages.sti.tcc.utilidades.EditTextMask
import com.ifsc.lages.sti.tcc.utilidades.StringUtil
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder
import java.util.*

class RegisterGenericInfoFragment : BaseFragment(), BottonSheetUsetTypeFragment.CallbackOptions, BottonSheetEducationInstitutionFragment.CallbackOptions {

    var button : MaterialButton? = null
    var editTextName : EditText? = null
    var editTextCpf : EditText? = null
    var editTextPhone : EditText? = null
    var editTextEmail : EditText? = null
    var editTextBirthDay : EditText? = null
    var editTextEducationInstitution : EditText? = null
    var editTextUserType : EditText? = null


    var textInputName : TextInputLayout? = null
    var textInputCpf : TextInputLayout? = null
    var textInputPhone : TextInputLayout? = null
    var textInputEmail : TextInputLayout? = null
    var textInputBirthDay : TextInputLayout? = null
    var textInputEducationInstitution : TextInputLayout? = null
    var textInputUserType : TextInputLayout? = null
    var registerViewModel :  RegisterViewModel? = null

    private val calendarBirthday : Calendar = Calendar.getInstance()
    private val calendarDateOfIssue : Calendar = Calendar.getInstance()
    private var bottonSheetUsetTypeFragment : BottonSheetUsetTypeFragment? = null
    private var bottonSheetEducationInstitutionFragment : BottonSheetEducationInstitutionFragment? = null
    private var progressBar :  ProgressBar? = null

    var user : User? = null;
    var typeUser : EUserType? = null
    var educationalInstitution : EducationalInstitution? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//            columnCount = it.getInt(ARG_COLUMN_COUNT)
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register_info_generic, container, false)
    }

    override fun mapComponents() {
        editTextName = view?.findViewById(R.id.edit_text_name)
        editTextPhone = view?.findViewById(R.id.edit_text_cell_fone)
        editTextCpf = view?.findViewById(R.id.edit_text_cpf)
        editTextEmail = view?.findViewById(R.id.edit_text_email)
        editTextBirthDay = view?.findViewById(R.id.edit_text_birthday)
        editTextEducationInstitution = view?.findViewById(R.id.edit_text_education_institution)
        editTextUserType = view?.findViewById(R.id.edit_text_user_type)
        button = view?.findViewById(R.id.button_cadastre)

        textInputName = view?.findViewById(R.id.text_input_name)
        textInputCpf = view?.findViewById(R.id.text_input_cpf)
        textInputPhone = view?.findViewById(R.id.text_input_cell_fone)
        textInputEmail = view?.findViewById(R.id.text_input_email)
        textInputBirthDay = view?.findViewById(R.id.text_input_birthday)
        textInputEducationInstitution = view?.findViewById(R.id.text_input_education_institution)
        textInputUserType = view?.findViewById(R.id.text_input_user_type)

        progressBar = view?.findViewById(R.id.progress_education)

        EditTextMask.addCpfMask(editTextCpf!!)
        EditTextMask.addTelefoneMask(editTextPhone!!)
    }

    override fun mapActionComponents() {
        button?.setOnClickListener {
            view?.findNavController()!!.navigate(R.id.action_genericFragment_to_registerTeacherFragment)//, bundle)
        }

        editTextName?.afterTextChanged {
            enableButtonNext()
        }

        editTextCpf?.afterTextChanged {
            enableButtonNext()
        }

        editTextPhone?.afterTextChanged {
            enableButtonNext()
        }

        editTextEmail?.afterTextChanged {
            enableButtonNext()
        }

        editTextEmail?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                showDialogBirthDay()
            }
            false
        }

        editTextBirthDay?.afterTextChanged {
            enableButtonNext()
        }

        editTextBirthDay?.setOnClickListener {
            showDialogBirthDay()
        }

        editTextUserType?.afterTextChanged {
            enableButtonNext()
        }

        editTextEducationInstitution?.afterTextChanged {
            enableButtonNext()
        }

        editTextEducationInstitution?.setOnClickListener {
            bottonSheetEducationInstitutionFragment = BottonSheetEducationInstitutionFragment.newInstance()
            bottonSheetEducationInstitutionFragment!!.show(activity!!.supportFragmentManager, null)
            bottonSheetEducationInstitutionFragment!!.setListener(this)
        }

        editTextUserType?.setOnClickListener {
            bottonSheetUsetTypeFragment = BottonSheetUsetTypeFragment.newInstance()
            bottonSheetUsetTypeFragment!!.show(activity!!.supportFragmentManager, null)
            bottonSheetUsetTypeFragment!!.setListener(this)
        }
    }

    fun showDialogBirthDay() {
        var calendarMaxDate = Calendar.getInstance()
        SpinnerDatePickerDialogBuilder()
            .context(activity)
            .callback { _, year, monthOfYear, dayOfMonth ->
                calendarBirthday.set(Calendar.YEAR, year)
                calendarBirthday.set(Calendar.MONTH, monthOfYear)
                calendarBirthday.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                editTextBirthDay?.setText(StringUtil.data("dd/MM/yyyy", Date(calendarBirthday.timeInMillis)))
                editTextBirthDay?.clearFocus()
            }
            .spinnerTheme(R.style.DatePickerSpinner)
            .defaultDate(calendarBirthday.get(Calendar.YEAR),
                calendarBirthday.get(Calendar.MONTH),
                calendarBirthday.get(Calendar.DAY_OF_MONTH))
            .maxDate(calendarMaxDate.get(Calendar.YEAR),
                calendarMaxDate.get(Calendar.MONTH),
                calendarMaxDate.get(Calendar.DAY_OF_MONTH))
            .build()
            .show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapComponents()
        mapActionComponents()

        registerViewModel = ViewModelProvider(this, RegisterViewModelFactory(activity!!)).get(RegisterViewModel::class.java)
        registerViewModel!!.queryInstitution.observe(this, androidx.lifecycle.Observer {
            progressBar?.visibility = View.INVISIBLE
            if(it.error!!.not()) {

            }
        })

        registerViewModel?.registerMonitoring()
    }

    fun enableButtonNext() {
        button?.isEnabled = validateFileds()
    }

    fun validateFileds() : Boolean {
        if(validateName().not()) {
            return false
        }
        if(validateCPF().not()) {
            return false
        }
        if(validatePhone().not()) {
            return false
        }

        if(validateEmail().not()) {
            return false
        }

        if(validateBirthaday().not()) {
            return false
        }

        if( validateInstituicao().not()) {
            return false
        }

        if(validateTypeUser().not()) {
            return false
        }

        user = User()
        user?.name = editTextName?.text.toString()
        user?.cpf = EditTextMask.removeCpfCnpjMask(editTextCpf?.text.toString())
        user?.phone = editTextPhone?.text.toString()
        user?.email = editTextEmail?.text.toString()
        user?.educationalInstitution = educationalInstitution
        user?.userType = typeUser!!.code
        return true
    }

    fun validateName() : Boolean {
        if (editTextName!!.text.toString().isValidFullName()) {
            textInputName?.helperText = null
            return true
        }
        textInputName?.helperText = null
        textInputName?.helperText = getString(R.string.error_invalid_name)
        return false
    }

    fun validateCPF() : Boolean {
        var cpf = editTextCpf?.text.toString()
        if (!EditTextMask.removeCpfCnpjMask(cpf).isCpfCnpjValid()) {
            textInputCpf?.helperText = null
            textInputCpf?.helperText = getString(R.string.error_invalid_cpf)
            return false
        }
        textInputCpf?.helperText = null
        return true
    }

    fun validatePhone() : Boolean {
        if (editTextPhone!!.text.toString().isPhoneValid()) {
            textInputPhone?.helperText = null
            return true
        }
        textInputPhone?.helperText = null
        textInputPhone?.helperText = getString(R.string.error_invalid_phone)
        return false
    }

    fun validateEmail() : Boolean {
        var email = editTextEmail?.text.toString()
         if (email.isEmailValid()) {
             textInputEmail?.helperText = null
             return true
         }
        textInputEmail?.helperText = null
        textInputEmail?.helperText = getString(R.string.error_invalid_email)
        return false
    }

    fun validateBirthaday() : Boolean {
        var birthDay = editTextBirthDay?.text.toString()
        if (birthDay.isNotEmpty()) {
            textInputBirthDay?.helperText = null
            return true
        }
        textInputBirthDay?.helperText = null
        textInputBirthDay?.helperText = getString(R.string.error_invalid_birthday)
        return false
    }

    fun validateInstituicao() : Boolean {
        var userType = editTextEducationInstitution?.text.toString()
        if (userType.isNotEmpty()) {
            textInputEducationInstitution?.helperText = null
            return true
        }
        textInputEducationInstitution?.helperText = null
        textInputEducationInstitution?.helperText = getString(R.string.error_invalid_education)
        return false
    }

    fun validateTypeUser() : Boolean {
        var userType = editTextUserType?.text.toString()
        if (userType.isNotEmpty()) {
            textInputUserType?.helperText = null
            return true
        }
        textInputUserType?.helperText = null
        textInputUserType?.helperText = getString(R.string.error_invalid_user_type)
        return false
    }

    override fun onClick(typeUser: EUserType?) {
        this.typeUser = typeUser
        editTextUserType?.setText(typeUser!!.description)
        bottonSheetUsetTypeFragment?.dismiss()
    }

    override fun onClick(educationalInstitution: EducationalInstitution?) {
        this.educationalInstitution = educationalInstitution
        editTextEducationInstitution?.setText(educationalInstitution?.name)
        bottonSheetEducationInstitutionFragment?.dismiss()
    }
}

fun String.isCpfCnpjValid(): Boolean {
    return this.isNotBlank() && (StringUtil.isCpfValid(this) || StringUtil.isCNPJValid(this))
}

fun String.isValidFullName(): Boolean {
    val moreThanOneName = this.split("\\s+".toRegex()).size != 1
    if (moreThanOneName) {
        return this.split("\\s+".toRegex())[1].isNotEmpty()
    } else {
        return false
    }
}

fun String.isPhoneValid(): Boolean {
    val regex = "^\\([0-9]{2}\\)[0-9]{5}-[0-9]{4}\$".toRegex()
    return this.matches(regex)
}

fun String.isEmailValid(): Boolean {
    return this.isNotBlank() && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}
