package com.tec.bufeo.capitan.Activity.Negocios.Model;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class DataConverterRatings implements Serializable {

    @TypeConverter // note this annotation
    public String fromOptionValuesList(List<Ratings> optionValues) {
        if (optionValues == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Ratings>>() {
        }.getType();
        String json = gson.toJson(optionValues, type);
        return json;
    }

    @TypeConverter // note this annotation
    public List<Ratings> toOptionValuesList(String optionValuesString) {
        if (optionValuesString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Ratings>>() {
        }.getType();
        List<Ratings> listaChanchas = gson.fromJson(optionValuesString, type);
        return listaChanchas;
    }
}
