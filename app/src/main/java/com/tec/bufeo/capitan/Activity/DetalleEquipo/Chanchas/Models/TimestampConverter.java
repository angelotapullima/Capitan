package com.tec.bufeo.capitan.Activity.DetalleEquipo.Chanchas.Models;

import androidx.room.TypeConverter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimestampConverter {
    @TypeConverter
    public static Date toDate(String stringdate){
        return stringdate == null ? null : new Date(stringdate);
    }

    @TypeConverter
    public static String toTimestamp(Date date){
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy/MM/dd");
        return dateTimeFormat.format(date);
    }

}
