package com.ifsc.lages.sti.tcc.ui.registersala.adapter

import android.app.Activity
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.model.classrom.Classroom
import com.ifsc.lages.sti.tcc.model.result.ResultSimulated
import com.ifsc.lages.sti.tcc.model.simulated.Simulated
import com.ifsc.lages.sti.tcc.props.ETipoSimulado
import com.ifsc.lages.sti.tcc.props.EUserType
import com.ifsc.lages.sti.tcc.utilidades.StringUtil
import kotlinx.android.synthetic.main.layout_ultimos_simulados.view.tv_date
import kotlinx.android.synthetic.main.layout_ultimos_simulados.view.tv_name
import kotlinx.android.synthetic.main.layout_ultimos_simulados.view.tv_type
import kotlinx.android.synthetic.main.list_item_classroom.view.*
import kotlinx.android.synthetic.main.list_item_simulado.view.*
import kotlinx.android.synthetic.main.list_item_simulado.view.tv_date_create
import kotlinx.android.synthetic.main.list_item_simulado.view.tv_description

class ClassroomAdapter (private val dataset: MutableList<Classroom>, private val aListener : Listener, private var context : Activity, var userType: Int)  : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var lView : View? = null
        if(userType == EUserType.TEACHER.code) {
            lView = LayoutInflater.from(parent.context) .inflate(R.layout.list_item_classroom_teacher, parent, false)
        } else {
            lView = LayoutInflater.from(parent.context) .inflate(R.layout.list_item_classroom, parent, false)
        }

        return ViewHolder(lView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = dataset[position]
        holder as ViewHolder
        holder.txtName.text = item.nome
        holder.txtDate.text = StringUtil.data(item.dataCriacao!!, "dd/MM/yyyy HH:mm")
        holder.txtStudents.text = "${item.qtdParticipantes} / ${item.maxParticipantes} alunos"

        if(item.descricao!!.isNotEmpty()) {
            holder.txtDescription.visibility = View.VISIBLE
            holder.txtDescription.text = item.descricao
        } else {
            holder.txtDescription.visibility = View.GONE
        }

        if(EUserType.TEACHER.code == userType) {
            holder.imgStatus.visibility = View.GONE
            holder.txtTeacher.text = item.nameTeacher
            holder.layoutInfoFinish.visibility = View.GONE
        } else {
            holder.imgStatus.visibility = View.VISIBLE
            holder.layoutInfoFinish.visibility = View.VISIBLE
        }



        if(item.participando) {
            setDrawable(holder.imgStatus, R.drawable.ic_check_circle)
            setColor(holder.imgStatus, R.color.md_green_700)
        } else {
            setDrawable(holder.imgStatus, R.drawable.ic_cancel)
            setColor(holder.imgStatus, R.color.accent)
        }

        with(holder.itemView) {
            tag = item
            setOnClickListener {
                aListener.onItemClick(item)
            }
        }
    }

    fun setDrawable(img : ImageView, drawable : Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            img.setImageDrawable(context.getDrawable(drawable))
        } else {
            img.setImageDrawable(context.resources.getDrawable(drawable))
        }
    }

    fun setColor(img : ImageView, color : Int) {
        img.setColorFilter(ContextCompat.getColor(context, color), android.graphics.PorterDuff.Mode.MULTIPLY);
    }

    fun updateList(datasetUpdate: MutableList<Classroom>) {
        dataset.clear()
        dataset.addAll(datasetUpdate)
        notifyDataSetChanged()
    }

    fun updateList(simulated: Classroom) {
        dataset.add(simulated)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    interface Listener {
        fun onItemClick(response: Classroom)
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var txtName : TextView = itemView.findViewById(R.id.tv_name)
        var txtDate : TextView = itemView.findViewById(R.id.tv_date_create)
        var txtDescription : TextView = itemView.findViewById(R.id.tv_description)
        var layoutInfoFinish : LinearLayout = itemView.findViewById(R.id.container_teacher)
        var txtTeacher : TextView = itemView.findViewById(R.id.tv_teacher)
        var txtStudents : TextView = itemView.findViewById(R.id.tv_student)
        var imgStatus : ImageView = itemView.findViewById(R.id.tv_status)
    }

}