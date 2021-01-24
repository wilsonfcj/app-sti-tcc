package com.ifsc.lages.sti.tcc.ui.suport.bottomsheet

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.ViewSwitcher
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.props.ESupport
import com.ifsc.lages.sti.tcc.props.EUserType

class BottonSheetSupportFragment : BottomSheetDialogFragment() {

    private var mView: View? = null
    private var mCallbackOptions: CallbackOptions? = null
    private var mTxtVwSugestao: View? = null
    private var mTxtVwAjuda: View? = null
    private var mTxtVwOutro: View? = null


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
            .inflate(R.layout.bottom_sheet_support, container, false)
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
        mTxtVwSugestao = mView!!.findViewById(R.id.tv_sugestao)
        mTxtVwAjuda = mView!!.findViewById(R.id.tv_ajuda)
        mTxtVwOutro = mView!!.findViewById(R.id.tv_outro)
    }

    private fun actionsViews() {
        mTxtVwSugestao!!.setOnClickListener { _: View? ->
            mCallbackOptions!!.onClick(
                ESupport.SUGESTAO
            )
        }
        mTxtVwAjuda!!.setOnClickListener { _: View? ->
            mCallbackOptions!!.onClick(
                ESupport.AJUDA
            )
        }

        mTxtVwOutro!!.setOnClickListener { _: View? ->
            mCallbackOptions!!.onClick(
                ESupport.OUTRO
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
        fun onClick(typeUser: ESupport?)
    }

    companion object {
        private const val TAG = "BottonSheetOptionsFragment"
        fun newInstance(): BottonSheetSupportFragment {
            return BottonSheetSupportFragment()
        }
    }
}