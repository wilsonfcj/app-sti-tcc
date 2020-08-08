package com.ifsc.lages.sti.tcc.utilidades.mapper;

/**
 * Created by lucas.orso on 08/11/2017.
 */

public interface MapperBase<T, Z> {

    T transform(Z aObject);
}
