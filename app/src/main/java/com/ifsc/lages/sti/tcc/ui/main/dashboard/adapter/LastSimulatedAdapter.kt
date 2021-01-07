package com.ifsc.lages.sti.tcc.ui.main.dashboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.model.result.ResultadoSimulado
import com.ifsc.lages.sti.tcc.props.ETipoSimulado
import com.ifsc.lages.sti.tcc.utilidades.StringUtil
import kotlinx.android.synthetic.main.layout_ultimos_simulados.view.*

class LastSimulatedAdapter (private val dataset: MutableList<ResultadoSimulado>, private val aListener : Listener)  : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val lView = LayoutInflater.from(parent.context) .inflate(R.layout.layout_ultimos_simulados, parent, false)
        return ViewHolder(lView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = dataset[position]
        holder as ViewHolder
        holder.txtName.text = item.nome
        holder.txtDate.text = StringUtil.data(item.dataEnvio!!, "dd/MM/yyyy HH:mm")
        holder.txtType.text = ETipoSimulado.getEnun(item.tipoSimulado!!).descricao

        with(holder.itemView) {
            tag = item
            setOnClickListener {
                aListener.onItemClick(item)
            }
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    interface Listener {
        fun onItemClick(response: ResultadoSimulado)
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val txtName : TextView = itemView.tv_name
        val txtDate : TextView = itemView.tv_date
        val txtType : TextView = itemView.tv_type
    }

}