package com.ifsc.lages.sti.tcc.ui.registersala.bottomsheet.result

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.ViewSwitcher
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.props.EOptionsResponse
import com.ifsc.lages.sti.tcc.props.EOptionsStart
import com.ifsc.lages.sti.tcc.props.EOptionsStudent
import com.ifsc.lages.sti.tcc.utilidades.KeyPrefs
import com.ifsc.lages.sti.tcc.utilidades.SharedPreferencesUtil

class BottonSheetResponseUserFragment : BottomSheetDialogFragment() {

    private var mView: View? = null
    private var mCallbackOptions: CallbackOptions? = null
    private var mTxtVwResultado: View? = null
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
            .inflate(R.layout.bottom_sheet_response_user, container, false)
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
        mTxtVwResultado = mView!!.findViewById(R.id.tv_resultado)
        mTxtVwGabarito = mView!!.findViewById(R.id.tv_resultado)
    }

    private fun actionsViews() {
        mTxtVwResultado!!.setOnClickListener { _: View? ->
            mCallbackOptions!!.onClick(
                EOptionsResponse.RESULTADO
            )
        }

        mTxtVwGabarito!!.setOnClickListener { _: View? ->
            mCallbackOptions!!.onClick(
                EOptionsResponse.GABARITO
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
        fun onClick(options: EOptionsResponse?)
    }

    companion object {
        private const val TAG = "BottonSheetOptionsFragment"
        fun newInstance(): BottonSheetResponseUserFragment {
            return BottonSheetResponseUserFragment()
        }
    }
}