package br.edu.ifsc.cancontrol.ui.monitoring.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.model.user.EducationalInstitution

class InstitutionAdapter (
    private val infos: MutableList<EducationalInstitution>?,
    var listener : Callback?,
    var context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val lView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_education_institution, parent, false)
        return ViewHolder(lView)
    }

    override fun getItemCount(): Int {
        return infos!!.size
    }

    interface Callback {
        fun onClick(educationalInstitution : EducationalInstitution)
    }

    /**
     * Mostrando as informações no componente, conforme o indice e o item selecionado na lista
     **/
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var lItem = infos?.get(position)
        holder as ViewHolder
        holder.txtDescricao.text = "${lItem?.name}"
        with(holder.itemView) {
            tag = lItem
            setOnClickListener{
                listener?.onClick(lItem!!)
            }
        }
    }

    // Classe responsavel por mapear os componentes que serão usados em cada layout
    inner class ViewHolder(aView : View) : RecyclerView.ViewHolder(aView) {
        val txtDescricao : TextView = aView.findViewById(R.id.txt_institution)
    }
}