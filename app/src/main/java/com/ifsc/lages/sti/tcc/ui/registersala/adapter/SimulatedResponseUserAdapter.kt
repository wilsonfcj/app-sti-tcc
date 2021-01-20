package com.ifsc.lages.sti.tcc.ui.registersala.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.model.result.ResultUser
import com.ifsc.lages.sti.tcc.utilidades.StringUtil


class SimulatedResponseUserAdapter (private val dataset: MutableList<ResultUser>, private val aListener : SimulatedResponseUserAdapter.Listener)  : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val lView = LayoutInflater.from(parent.context) .inflate(R.layout.list_item_simulado_reponse_user, parent, false)
        return ViewHolder(lView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = dataset[position]
        holder as ViewHolder
        holder.txtName.text = item.nome

        holder.txtDateFinish.text = StringUtil.data(item.dataEnvio!!, "dd/MM/yyyy")
        holder.txtHourFinish.text = StringUtil.data(item.dataEnvio!!, "HH:mm")

        holder.tvAcerto.text = item.resultadoGeral!!.acertos!!.toString()
        holder.tvError.text = item.resultadoGeral!!.erros!!.toString()
        holder.tvNaoRespondidas.text = item.resultadoGeral!!.naoRespondidas!!.toString()

        with(holder.itemView) {
            tag = item
            setOnClickListener {
                aListener.onItemClick(item)
            }
        }
    }

    interface Listener {
        fun onItemClick(response: ResultUser)
    }

    fun updateList(datasetUpdate: MutableList<ResultUser>) {
        dataset.clear()
        dataset.addAll(datasetUpdate)
        notifyDataSetChanged()
    }

    fun updateList(simulated: ResultUser) {
        dataset.add(simulated)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val txtName : TextView = itemView.findViewById(R.id.tv_name)
        val txtDateFinish : TextView = itemView.findViewById(R.id.tv_date)
        val txtHourFinish : TextView = itemView.findViewById(R.id.tv_clock)

        var tvAcerto : TextView = itemView.findViewById(R.id.tv_geralI)
        var tvError : TextView = itemView.findViewById(R.id.tv_geralII)
        var tvNaoRespondidas : TextView = itemView.findViewById(R.id.tv_geralIII)
    }

}