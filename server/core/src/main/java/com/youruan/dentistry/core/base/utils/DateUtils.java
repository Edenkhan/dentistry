package com.youruan.dentistry.core.base.utils;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Objects;

public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    public static Integer getAge(Date birthday) {
        if (birthday == null) {
            return null;
        }
        LocalDate birthdayDate = birthday.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return Period.between(birthdayDate, LocalDate.now()).getYears();
    }

    public static Date min(Date... dates) {
        return Arrays.stream(dates)
                .filter(Objects::nonNull)
                .sorted()
                .findFirst()
                .orElse(null);
    }

    public static Date max(Date... dates) {
        return Arrays
                .stream(dates)
                .filter(Objects::nonNull)
                .max(Comparator.naturalOrder())
                .orElse(null);
    }

}
