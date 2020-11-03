package com.ifsc.lages.sti.tcc.ui.register.bottomsheet

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.ViewSwitcher
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.props.EUserType

class BottonSheetUsetTypeFragment : BottomSheetDialogFragment() {

    private var mView: View? = null
    private var mCallbackOptions: CallbackOptions? = null
    private var mTxtVwViewInfos: View? = null
    private var mTxtVwDelete: View? = null
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
            .inflate(R.layout.bottom_sheet_user_type, container, false)
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
        mTxtVwViewInfos = mView!!.findViewById(R.id.tv_student)
        mTxtVwDelete = mView!!.findViewById(R.id.tv_teacher)
    }

    private fun actionsViews() {
        mTxtVwViewInfos!!.setOnClickListener { _: View? ->
            mCallbackOptions!!.onClick(
                EUserType.STUDENT
            )
        }
        mTxtVwDelete!!.setOnClickListener { _: View? ->
            mCallbackOptions!!.onClick(
                EUserType.TEACHER
            )
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    }

    fun setListener(aListener: CallbackOptions?) {
        mCallbackOptions = aListener
    }

    interface CallbackOptions {
        fun onClick(typeUser: EUserType?)
    }

    companion object {
        private const val TAG = "BottonSheetOptionsFragment"
        fun newInstance(): BottonSheetUsetTypeFragment {
            return BottonSheetUsetTypeFragment()
        }
    }
}