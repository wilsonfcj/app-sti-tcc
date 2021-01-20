package com.ifsc.lages.sti.tcc.ui.meussimulados.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.props.ETipoSimulado
import com.ifsc.lages.sti.tcc.props.EUserType
import com.ifsc.lages.sti.tcc.resources.simulated.SimuladoRequest
import com.ifsc.lages.sti.tcc.ui.login.afterTextChanged
import com.ifsc.lages.sti.tcc.ui.meussimulados.bottomsheet.BottonSheetSimulatedTypeFragment
import com.ifsc.lages.sti.tcc.ui.meussimulados.viewmodel.MeusSimuladosViewModel
import com.ifsc.lages.sti.tcc.ui.meussimulados.viewmodel.MeusSimuladosViewModelFactory
import com.ifsc.lages.sti.tcc.utilidades.KeyPrefs
import com.ifsc.lages.sti.tcc.utilidades.SharedPreferencesUtil
import com.ifsc.lages.sti.tcc.utilidades.StringUtil
import com.ifsc.lages.sti.tcc.utilidades.baseview.BaseFragment
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder
import java.util.*

class RegisterGenericSimulatedFragment : BaseFragment(), BottonSheetSimulatedTypeFragment.CallbackOptions {

    var button : MaterialButton? = null
    var editTextName : EditText? = null
    var editTextDescription : EditText? = null
    var editTextType : EditText? = null

    var editTextStartDate : EditText? = null
    var editTextFinishDate : EditText? = null

    var textInputName : TextInputLayout? = null
    var textInputDescription : TextInputLayout? = null
    var textInputType : TextInputLayout? = null

    var textInputStartDate : TextInputLayout? = null
    var textInputFinishDate : TextInputLayout? = null

    var tipoSimulado: ETipoSimulado? = null
    var meusSimuladosViewModel : MeusSimuladosViewModel? = null
    var register : SimuladoRequest.Register? = null
    var linearLayoutDates : LinearLayout? = null

    private var mStartDateCalendar: Calendar? = null
    private var mFinalDateCalendar: Calendar? = null
    private var idClassroom : Long? = null

    private var bottonSheetSimulatedTypeFragment : BottonSheetSimulatedTypeFragment? = null

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
        arguments?.let {
            idClassroom = arguments?.getLong("id_classroom")
        }

        editTextName = view?.findViewById(R.id.edit_text_name)
        editTextDescription = view?.findViewById(R.id.edit_text_description)
        editTextType = view?.findViewById(R.id.edit_text_type)
        button = view?.findViewById(R.id.button_cadastre)

        editTextStartDate = view?.findViewById(R.id.edit_text_start_date)
        editTextFinishDate = view?.findViewById(R.id.edit_text_finish_date)

        textInputName = view?.findViewById(R.id.text_input_name)
        textInputDescription = view?.findViewById(R.id.text_input_description)
        textInputType = view?.findViewById(R.id.text_input_type)
        linearLayoutDates = view?.findViewById(R.id.linear_layout_date)

        textInputStartDate = view?.findViewById(R.id.text_input_start_date)
        textInputFinishDate = view?.findViewById(R.id.text_input_finish_date)

        startTimesDisplay()
    }

    override fun mapActionComponents() {
        button?.setOnClickListener {
            if(tipoSimulado!!.codigo == ETipoSimulado.DEFAULT.codigo) {
                var bundle = Bundle()
                bundle.putSerializable("register_simulated",register!!)
                view?.findNavController()!!.navigate(R.id.action_genericFragment_to_registerSimulatedFragment, bundle)
            } else {
                showLoading("Registrando Simulado")
                meusSimuladosViewModel?.createSimulated(register!!)
            }
        }

        editTextStartDate?.setOnClickListener {
            showDialogStartDay()
        }

        editTextFinishDate?.setOnClickListener {
            showDialogFinishDay()
        }

        editTextDescription?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                showDialogSelected()
            }
            false
        }

        editTextName?.afterTextChanged {enableButtonNext()}
        editTextDescription?.afterTextChanged {enableButtonNext()}

        editTextType?.setOnClickListener {
            showDialogSelected()
        }
    }

    fun startTimesDisplay() {
        val userType = SharedPreferencesUtil.get(activity, KeyPrefs.USER_TYPE, 1)
        if(EUserType.TEACHER.code == userType) {
            linearLayoutDates?.visibility = View.VISIBLE
        } else {
            linearLayoutDates?.visibility = View.INVISIBLE
        }
        mStartDateCalendar = StringUtil.getFirstDayMonth(Calendar.getInstance())
        mFinalDateCalendar = StringUtil.getLastDayMonth(Calendar.getInstance())

        editTextStartDate?.setText(StringUtil.data(mStartDateCalendar!!.getTime(), "dd/MM/yyyy"))
        editTextFinishDate?.setText(StringUtil.data(mFinalDateCalendar!!.getTime(), "dd/MM/yyyy"))
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
        createRestListener()
    }

    fun enableButtonNext() {
        button?.isEnabled = validateFileds()
    }

    fun validateFileds() : Boolean {
        if(validateName().not()) { return false }
        if(validateDescription().not()) { return false }
        if(validateTypeSimulated().not()) { return false }
        updateRequest()
        return true
    }

    fun updateRequest() {
        val userId = SharedPreferencesUtil.get(activity, KeyPrefs.USER_ID, 0L)
        if(register == null) {
            register = SimuladoRequest.Register()
        }
        register?.nome = editTextName!!.text.toString()
        register?.descricao = editTextDescription!!.text.toString()
        register?.tipoSimulado = tipoSimulado!!.codigo
        register?.dataInicio = StringUtil.data(mStartDateCalendar!!.time, "yyyy-MM-dd'T'HH:mm:ss")
        register?.dataFimSimulado = StringUtil.data(mFinalDateCalendar!!.time, "yyyy-MM-dd'T'HH:mm:ss")
        register?.idUsuario = userId

        idClassroom?.let {
            register?.idSala = idClassroom
        }

        if (tipoSimulado!!.codigo == ETipoSimulado.POSCOMP.codigo ||
            tipoSimulado!!.codigo == ETipoSimulado.ENADE.codigo) {
            register?.tempoMaximo = 240
        }
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

    fun showDialogStartDay() {
        var calendarMaxDate = Calendar.getInstance()
        SpinnerDatePickerDialogBuilder()
            .context(activity)
            .callback { _, year, monthOfYear, dayOfMonth ->
                mStartDateCalendar?.set(Calendar.YEAR, year)
                mStartDateCalendar?.set(Calendar.MONTH, monthOfYear)
                mStartDateCalendar?.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                editTextStartDate?.setText(StringUtil.data("dd/MM/yyyy", Date(mStartDateCalendar!!.timeInMillis)))
                editTextStartDate?.clearFocus()
                updateRequest()
            }
            .spinnerTheme(R.style.DatePickerSpinner)
            .defaultDate(mStartDateCalendar!!.get(Calendar.YEAR),
                mStartDateCalendar!!.get(Calendar.MONTH),
                mStartDateCalendar!!.get(Calendar.DAY_OF_MONTH))
            .minDate(calendarMaxDate.get(Calendar.YEAR),
                calendarMaxDate.get(Calendar.MONTH),
                calendarMaxDate.get(Calendar.DAY_OF_MONTH))
            .maxDate(mFinalDateCalendar!!.get(Calendar.YEAR),
                mFinalDateCalendar!!.get(Calendar.MONTH),
                mFinalDateCalendar!!.get(Calendar.DAY_OF_MONTH))
            .build()
            .show()
    }

    fun showDialogFinishDay() {
        var calendarMaxDate = Calendar.getInstance()
        SpinnerDatePickerDialogBuilder()
            .context(activity)
            .callback { _, year, monthOfYear, dayOfMonth ->
                mFinalDateCalendar?.set(Calendar.YEAR, year)
                mFinalDateCalendar?.set(Calendar.MONTH, monthOfYear)
                mFinalDateCalendar?.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                editTextFinishDate?.setText(StringUtil.data("dd/MM/yyyy", Date(mFinalDateCalendar!!.timeInMillis)))
                editTextFinishDate?.clearFocus()
                updateRequest()
            }
            .spinnerTheme(R.style.DatePickerSpinner)
            .defaultDate(mFinalDateCalendar!!.get(Calendar.YEAR),
                mFinalDateCalendar!!.get(Calendar.MONTH),
                mFinalDateCalendar!!.get(Calendar.DAY_OF_MONTH))
            .minDate(calendarMaxDate.get(Calendar.YEAR),
                calendarMaxDate.get(Calendar.MONTH),
                calendarMaxDate.get(Calendar.DAY_OF_MONTH))
            .build()
            .show()
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