package com.ifsc.lages.sti.tcc.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anton46.collectionitempicker.CollectionPicker
import com.anton46.collectionitempicker.Item
import com.anton46.collectionitempicker.OnItemClickListener
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.utilidades.BaseFragment


class RegisterTeacherFragment : BaseFragment() {

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
        return inflater.inflate(R.layout.fragment_register_teacher, container, false)
    }

    override fun mapComponents() {
        TODO("Not yet implemented")
    }

    override fun mapActionComponents() {
        TODO("Not yet implemented")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val items: MutableList<Item> = ArrayList()
        items.add(Item("1", "Matemática discreta"))
        items.add(Item("2", "Introdução a programação"))
        items.add(Item("3", "Banco de dados I"))
        items.add(Item("4", "Banco de dados II"))

        val picker = view.findViewById(R.id.collection_item_picker) as CollectionPicker
        picker.items = items
        picker.setOnItemClickListener { _, _ ->  }
    }
}