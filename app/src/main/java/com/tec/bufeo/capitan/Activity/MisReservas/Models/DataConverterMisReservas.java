package com.tec.bufeo.capitan.Activity.MisReservas.Models;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class DataConverterMisReservas implements Serializable {

    @TypeConverter // note this annotation
    public String fromOptionValuesList(List<DetalleReservas> optionValues) {
        if (optionValues == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<DetalleReservas>>() {
        }.getType();
        String json = gson.toJson(optionValues, type);
        return json;
    }

    @TypeConverter // note this annotation
    public List<DetalleReservas> toOptionValuesList(String optionValuesString) {
        if (optionValuesString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<DetalleReservas>>() {
        }.getType();
        List<DetalleReservas> listaChanchas = gson.fromJson(optionValuesString, type);
        return listaChanchas;
    }
}
