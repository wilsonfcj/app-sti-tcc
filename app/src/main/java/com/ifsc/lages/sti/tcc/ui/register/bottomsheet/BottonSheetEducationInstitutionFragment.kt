package com.ifsc.lages.sti.tcc.ui.register.bottomsheet

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.ViewSwitcher
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsc.cancontrol.ui.monitoring.adapter.InstitutionAdapter
import br.edu.ifsc.cancontrol.ui.monitoring.adapter.MatterAdapter
import br.edu.ifsc.cancontrol.utilidades.MapElement
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.model.user.EducationalInstitution

class BottonSheetEducationInstitutionFragment : BottomSheetDialogFragment(), MapElement,
    InstitutionAdapter.Callback {

    private var mView: View? = null
    private var recyclerView : RecyclerView? = null
    private var mCallbackOptions: CallbackOptions? = null
    private val mViewSwitcher: ViewSwitcher? = null
    private var institutionAdapter : InstitutionAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val lContextThemeWrapper: Context = ContextThemeWrapper(activity, R.style.AppTheme)
        return inflater.cloneInContext(lContextThemeWrapper).inflate(R.layout.bottom_sheet_education_institution, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mView = view
        mapComponents()
        mapActionComponents()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.window!!.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    }

    fun setListener(aListener: CallbackOptions) {
        mCallbackOptions = aListener
    }

    companion object {
        private const val TAG = "BottonSheetOptionsFragment"
        fun newInstance(): BottonSheetEducationInstitutionFragment {
            return BottonSheetEducationInstitutionFragment()
        }
    }

    override fun mapComponents() {
        recyclerView = mView!!.findViewById(R.id.recycler_view)
        var institution = EducationalInstitution.DB.loadAll()
        if(institution != null) {
            initializeRecycler(institution)
        }
    }

    private fun initializeRecycler(_info : MutableList<EducationalInstitution>) {
        institutionAdapter = InstitutionAdapter(_info, this, activity!!)
        val layoutManager = LinearLayoutManager(activity!!, LinearLayoutManager.VERTICAL, false)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = institutionAdapter
        institutionAdapter?.notifyDataSetChanged()

    }

    interface CallbackOptions {
        fun onClick(typeUser: EducationalInstitution?)
    }

    override fun mapActionComponents() {}

    override fun onClick(educationalInstitution: EducationalInstitution) {
        mCallbackOptions?.onClick(educationalInstitution)
    }
}