package com.ifsc.lages.sti.tcc.resources.classroom.mapper

import com.ifsc.lages.sti.tcc.model.classrom.Classroom
import com.ifsc.lages.sti.tcc.resources.classroom.ClassroomResponse
import com.ifsc.lages.sti.tcc.utilidades.mapper.MapperUtil
import java.util.*

class ClassroomMapper : MapperUtil<ClassroomResponse.SalaResponse, Classroom>() {

    public override fun transform(aObject: ClassroomResponse.SalaResponse?): Classroom {
        var response = Classroom()
        response._id = aObject!!.id
        response.nome = aObject!!.nome
        response.descricao = aObject!!.descricao
        response.idTeacher = aObject!!.professor!!.id!!
        response.nameTeacher = aObject!!.professor!!.nome!!
        response.maxParticipantes = aObject!!.maxParticipantes
        response.qtdParticipantes = aObject!!.qtdParticipantes
        response.dataCriacao = aObject!!.dataCriacao
        response.participando = aObject!!.participando
        return response;
    }
}