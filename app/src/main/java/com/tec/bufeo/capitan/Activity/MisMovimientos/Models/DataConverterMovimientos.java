package com.tec.bufeo.capitan.Activity.MisMovimientos.Models;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class DataConverterMovimientos implements Serializable {

    @TypeConverter // note this annotation
    public String fromOptionValuesList(List<DetalleMovimientos> optionValues) {
        if (optionValues == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<DetalleMovimientos>>() {
        }.getType();
        String json = gson.toJson(optionValues, type);
        return json;
    }

    @TypeConverter // note this annotation
    public List<DetalleMovimientos> toOptionValuesList(String optionValuesString) {
        if (optionValuesString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<DetalleMovimientos>>() {
        }.getType();
        List<DetalleMovimientos> listaChanchas = gson.fromJson(optionValuesString, type);
        return listaChanchas;
    }
}
