package com.ifsc.lages.sti.tcc.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.google.android.material.button.MaterialButton
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.utilidades.BaseFragment

class RegisterGenericInfoFragment : BaseFragment() {

    var button : MaterialButton? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//            columnCount = it.getInt(ARG_COLUMN_COUNT)
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register_info_generic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button = view.findViewById(R.id.button_cadastre)
        button?.setOnClickListener {
            view.findNavController().navigate(R.id.action_genericFragment_to_registerTeacherFragment)//, bundle)
        }
    }
}