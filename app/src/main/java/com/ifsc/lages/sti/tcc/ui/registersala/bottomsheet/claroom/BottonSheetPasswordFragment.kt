package com.ifsc.lages.sti.tcc.ui.registersala.bottomsheet.claroom

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.ViewSwitcher
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ifsc.lages.sti.tcc.R

class BottonSheetPasswordFragment : BottomSheetDialogFragment() {

    private var mView: View? = null
    private var mPassword: EditText? = null
    private var mButton: Button? = null
    private var mCallbackOptions: CallbackPassword? = null

    private val mViewSwitcher: ViewSwitcher? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val lContextThemeWrapper: Context =
            ContextThemeWrapper(activity, R.style.AppTheme)
        return inflater.cloneInContext(lContextThemeWrapper)
            .inflate(R.layout.bottom_sheet_password, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        mView = view
        mapViews()
        actionsViews()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun mapViews() {
        mPassword = mView!!.findViewById(R.id.edit_text_password)
        mButton = mView!!.findViewById(R.id.btn_plus_info)
    }

    fun call() {
        if(mPassword!!.text.toString().isEmpty().not()) {
            mCallbackOptions?.onClick(mPassword!!.text.toString())
            dismiss()
        } else {
            Toast.makeText(activity, "Informe uma senha", Toast.LENGTH_LONG).show()
        }
    }

    private fun actionsViews() {
        mButton!!.setOnClickListener { _: View? ->
            call()
        }

        mPassword?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                call()
            }
            false
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.window!!.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    }

    fun setListener(aListener: CallbackPassword?) {
        mCallbackOptions = aListener
    }

    interface CallbackPassword {
        fun onClick(passwod: String?)
    }

    companion object {
        private const val TAG = "BottonSheetOptionsFragment"
        fun newInstance(): BottonSheetPasswordFragment {
            return BottonSheetPasswordFragment()
        }
    }
}