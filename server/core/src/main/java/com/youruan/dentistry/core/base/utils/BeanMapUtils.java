package com.youruan.dentistry.core.base.utils;

import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class BeanMapUtils {

    public static Map<String, Object> pick(Object bean, String... propertyNames) {
        Map<String, Object> resultMap = new HashMap<>();
        if (bean == null) {
            return resultMap;
        }
        for (String propertyName : propertyNames) {
            if (propertyName.contains(".")) {
                String[] notationParts = propertyName.split("\\.");
                String firstName = notationParts[0];
                String secondName = notationParts[1];

                Map<String, Object> notationMap = (Map<String, Object>) resultMap.get(firstName);
                if (notationMap == null) {
                    notationMap = new HashMap<>();
                    resultMap.put(firstName, notationMap);
                }
                Object value = null;
                try {
                    value = PropertyUtils.getNestedProperty(bean, propertyName);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | NestedNullException e) {
                    // ignore
                }
                if (value != null) {
                    notationMap.put(secondName, value);
                }
            } else {
                try {
                    resultMap.put(propertyName, PropertyUtils.getSimpleProperty(bean, propertyName));
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return resultMap;
    }

    public static List<Map<String, Object>> pick(Collection<?> beans, String... propertyNames) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        for (Object bean : beans) {
            resultList.add(pick(bean, propertyNames));
        }
        return resultList;
    }


}
