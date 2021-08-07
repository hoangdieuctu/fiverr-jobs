package com.raunheim.los.util;

import com.raunheim.los.exception.LosException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {

    private static SimpleDateFormat losFormatter = new SimpleDateFormat("yyyyMMdd-HHmmss");

    public static Date convertSimpleDateTime(String value) {
        try {
            return losFormatter.parse(value);
        } catch (ParseException e) {
            throw new LosException(String.format("Invalid date time format for %s", value), e);
        }
    }

}
