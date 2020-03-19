package com.tec.bufeo.capitan.Activity.DetalleEquipo.Chanchas.Models;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class DataConverterChanchas implements Serializable {

    @TypeConverter // note this annotation
    public String fromOptionValuesList(List<DetalleChancha> optionValues) {
        if (optionValues == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<DetalleChancha>>() {
        }.getType();
        String json = gson.toJson(optionValues, type);
        return json;
    }

    @TypeConverter // note this annotation
    public List<DetalleChancha> toOptionValuesList(String optionValuesString) {
        if (optionValuesString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<DetalleChancha>>() {
        }.getType();
        List<DetalleChancha> productCategoriesList = gson.fromJson(optionValuesString, type);
        return productCategoriesList;
    }

}
