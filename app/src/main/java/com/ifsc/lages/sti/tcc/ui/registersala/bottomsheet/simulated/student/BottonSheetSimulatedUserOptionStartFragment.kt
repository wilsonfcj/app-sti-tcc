package com.ifsc.lages.sti.tcc.ui.registersala.bottomsheet.simulated.student

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.ViewSwitcher
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.props.EOptionsStart
import com.ifsc.lages.sti.tcc.props.EOptionsStudent
import com.ifsc.lages.sti.tcc.utilidades.KeyPrefs
import com.ifsc.lages.sti.tcc.utilidades.SharedPreferencesUtil

class BottonSheetSimulatedUserOptionStartFragment : BottomSheetDialogFragment() {

    private var mView: View? = null
    private var mCallbackOptions: CallbackOptions? = null
    private var mTxtVwStart: View? = null
    private var mTxtVwGabarito: View? = null

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
            .inflate(R.layout.bottom_sheet_simulated_classroom_options, container, false)
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
        mTxtVwStart = mView!!.findViewById(R.id.tv_start)
    }

    private fun actionsViews() {
        mTxtVwStart!!.setOnClickListener { _: View? ->
            mCallbackOptions!!.onClick(
                EOptionsStart.INICIAR
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
        fun onClick(options: EOptionsStart?)
    }

    companion object {
        private const val TAG = "BottonSheetOptionsFragment"
        fun newInstance(): BottonSheetSimulatedUserOptionStartFragment {
            return BottonSheetSimulatedUserOptionStartFragment()
        }
    }
}