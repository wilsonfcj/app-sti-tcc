package com.ifsc.lages.sti.tcc.ui.meussimulados.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.utilidades.baseview.BaseFragment

class RegisterCustomSimulatedFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register_simulated_custom, container, false)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

    }

    override fun mapComponents() {

    }

    override fun mapActionComponents() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapComponents()
        mapActionComponents()
    }
}