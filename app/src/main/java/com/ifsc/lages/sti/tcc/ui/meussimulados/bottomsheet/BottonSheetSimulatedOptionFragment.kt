package com.ifsc.lages.sti.tcc.ui.meussimulados.bottomsheet

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.ViewSwitcher
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.props.EOptions
import com.ifsc.lages.sti.tcc.props.ETipoSimulado
import com.ifsc.lages.sti.tcc.props.EUserType
import com.ifsc.lages.sti.tcc.utilidades.KeyPrefs
import com.ifsc.lages.sti.tcc.utilidades.SharedPreferencesUtil

class BottonSheetSimulatedOptionFragment : BottomSheetDialogFragment() {

    private var mView: View? = null
    private var mCallbackOptions: CallbackOptions? = null
    private var mTxtVwStart: View? = null
    private var mTxtVwGabarito: View? = null
    private var mTxtVwEdit: View? = null
    private var mTxtVwRemove: View? = null

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
            .inflate(R.layout.bottom_sheet_simulated_options, container, false)
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
        var userType = SharedPreferencesUtil.get(activity, KeyPrefs.USER_TYPE, 0)
        mTxtVwStart = mView!!.findViewById(R.id.tv_start)
        mTxtVwGabarito = mView!!.findViewById(R.id.tv_simulated)
        if(userType == EUserType.TEACHER.code) {
            mTxtVwStart!!.visibility = View.GONE
            mTxtVwGabarito!!.visibility = View.VISIBLE
        } else {
            mTxtVwGabarito!!.visibility = View.GONE
        }
        mTxtVwEdit = mView!!.findViewById(R.id.tv_edit)
        mTxtVwRemove = mView!!.findViewById(R.id.tv_remove)
    }

    private fun actionsViews() {
        mTxtVwStart!!.setOnClickListener { _: View? ->
            mCallbackOptions!!.onClick(
                EOptions.INICIAR
            )
        }
        mTxtVwEdit!!.setOnClickListener { _: View? ->
            mCallbackOptions!!.onClick(
                EOptions.EDITAR
            )
        }

        mTxtVwRemove!!.setOnClickListener { _: View? ->
            mCallbackOptions!!.onClick(
                EOptions.REMOVER
            )
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.window!!.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    }

    fun setListener(aListener: CallbackOptions?) {
        mCallbackOptions = aListener
    }

    interface CallbackOptions {
        fun onClick(options: EOptions?)
    }

    companion object {
        private const val TAG = "BottonSheetOptionsFragment"
        fun newInstance(): BottonSheetSimulatedOptionFragment {
            return BottonSheetSimulatedOptionFragment()
        }
    }
}