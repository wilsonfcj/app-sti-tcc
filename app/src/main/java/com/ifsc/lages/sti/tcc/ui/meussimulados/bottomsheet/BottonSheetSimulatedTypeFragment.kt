package com.ifsc.lages.sti.tcc.ui.meussimulados.bottomsheet

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.ViewSwitcher
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.props.ETipoSimulado
import com.ifsc.lages.sti.tcc.props.EUserType

class BottonSheetSimulatedTypeFragment : BottomSheetDialogFragment() {

    private var mView: View? = null
    private var mCallbackOptions: CallbackOptions? = null
    private var mTxtVwViewEnade: View? = null
    private var mTxtVwPoscomp: View? = null
    private var mTxtVwPersonalizado: View? = null
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
            .inflate(R.layout.bottom_sheet_simulated_type, container, false)
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
        mTxtVwViewEnade = mView!!.findViewById(R.id.tv_enade)
        mTxtVwPoscomp = mView!!.findViewById(R.id.tv_poscomp)
        mTxtVwPersonalizado = mView!!.findViewById(R.id.tv_custom)
    }

    private fun actionsViews() {
        mTxtVwViewEnade!!.setOnClickListener { _: View? ->
            mCallbackOptions!!.onClick(
                ETipoSimulado.ENADE
            )
        }
        mTxtVwPoscomp!!.setOnClickListener { _: View? ->
            mCallbackOptions!!.onClick(
                ETipoSimulado.POSCOMP
            )
        }

        mTxtVwPersonalizado!!.setOnClickListener { _: View? ->
            mCallbackOptions!!.onClick(
                ETipoSimulado.DEFAULT
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
        fun onClick(typeUser: ETipoSimulado?)
    }

    companion object {
        private const val TAG = "BottonSheetOptionsFragment"
        fun newInstance(): BottonSheetSimulatedTypeFragment {
            return BottonSheetSimulatedTypeFragment()
        }
    }
}