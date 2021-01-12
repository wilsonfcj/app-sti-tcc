package com.ifsc.lages.sti.tcc.ui.meussimulados.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.model.result.ResultSimulated
import com.ifsc.lages.sti.tcc.model.simulated.Simulated
import com.ifsc.lages.sti.tcc.props.ETipoSimulado
import com.ifsc.lages.sti.tcc.utilidades.StringUtil
import kotlinx.android.synthetic.main.layout_ultimos_simulados.view.tv_date
import kotlinx.android.synthetic.main.layout_ultimos_simulados.view.tv_name
import kotlinx.android.synthetic.main.layout_ultimos_simulados.view.tv_type
import kotlinx.android.synthetic.main.list_item_simulado.view.*

class SimulatedAdapter (private val dataset: MutableList<Simulated>, private val aListener : Listener)  : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val lView = LayoutInflater.from(parent.context) .inflate(R.layout.list_item_simulado, parent, false)
        return ViewHolder(lView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = dataset[position]
        holder as ViewHolder
        holder.txtName.text = item.nome
        holder.txtDate.text = StringUtil.data(item.dataCriacao!!, "dd/MM/yyyy HH:mm")
        holder.txtType.text = ETipoSimulado.getEnun(item.tipoSimulado!!).descricao
        if(item.descricao!!.isNotEmpty()) {
            holder.txtDescription.visibility = View.VISIBLE
            holder.txtDescription.text = item.descricao
        } else {
            holder.txtDescription.visibility = View.GONE
        }

        if(item.tipoSimulado!! == ETipoSimulado.DEFAULT.codigo) {
            holder.txtType.setBackgroundResource(R.drawable.shape_text_view)
        } else if (item.tipoSimulado!! == ETipoSimulado.ENADE.codigo) {
            holder.txtType.setBackgroundResource(R.drawable.shape_text_view_one)
        } else {
            holder.txtType.setBackgroundResource(R.drawable.shape_text_view_two)
        }

        if(item.simuladoResultado != null) {
            holder.tvAcerto.text = item.simuladoResultado!!.resultadoGeral!!.acertos!!.toString()
            holder.tvError.text = item.simuladoResultado!!.resultadoGeral!!.erros!!.toString()
            holder.tvNaoRespondidas.text = item.simuladoResultado!!.resultadoGeral!!.naoRespondidas!!.toString()
            holder.layoutInfoFinish.visibility = View.VISIBLE
        } else {
            holder.layoutInfoFinish.visibility = View.GONE
        }

        with(holder.itemView) {
            tag = item
            setOnClickListener {
                aListener.onItemClick(item)
            }
        }
    }

    fun updateList(datasetUpdate: MutableList<Simulated>) {
        dataset.clear()
        dataset.addAll(datasetUpdate)
        notifyDataSetChanged()
    }

    fun updateList(simulated: Simulated) {
        dataset.add(simulated)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    interface Listener {
        fun onItemClick(response: Simulated)
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val txtName : TextView = itemView.tv_name
        val txtDate : TextView = itemView.tv_date_create
        val txtHour : TextView = itemView.tv_clock_create
        val txtType : TextView = itemView.tv_type
        val txtDescription : TextView = itemView.tv_description
        val layoutInfoFinish : LinearLayout = itemView.layout_info_send

//        Resultado
        val txtDateSend : TextView = itemView.tv_date
        val txtHourSend : TextView = itemView.tv_clock

        var tvAcerto : TextView = itemView.tv_geralI
        var tvError : TextView = itemView.tv_geralII
        var tvNaoRespondidas : TextView = itemView.tv_geralIII
    }

}