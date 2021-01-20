package com.ifsc.lages.sti.tcc.ui.registersala.bottomsheet.simulated.teacher

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.ViewSwitcher
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.props.EOptionsTeacher

class BottonSheetSimulatedOptionTeacherFragment : BottomSheetDialogFragment() {

    private var mView: View? = null
    private var mCallbackOptions: CallbackOptions? = null
    private var mTxtVwStart: View? = null
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
            .inflate(R.layout.bottom_sheet_simulated_options_teacher, container, false)
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
        mTxtVwEdit = mView!!.findViewById(R.id.tv_simulated)
        mTxtVwRemove = mView!!.findViewById(R.id.tv_remove)
    }

    private fun actionsViews() {
        mTxtVwStart!!.setOnClickListener { _: View? ->
            mCallbackOptions!!.onClick(
                EOptionsTeacher.GERAL
            )
        }

        mTxtVwEdit!!.setOnClickListener { _: View? ->
            mCallbackOptions!!.onClick(
                EOptionsTeacher.CONFERIR_GABARITO
            )
        }

        mTxtVwRemove!!.setOnClickListener { _: View? ->
            mCallbackOptions!!.onClick(
                EOptionsTeacher.REPOSTA_ALUNO
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
        fun onClick(options: EOptionsTeacher?)
    }

    companion object {
        private const val TAG = "BottonSheetOptionsFragment"
        fun newInstance(): BottonSheetSimulatedOptionTeacherFragment {
            return BottonSheetSimulatedOptionTeacherFragment()
        }
    }
}