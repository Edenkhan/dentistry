package com.youruan.dentistry.core.base.utils;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Collections;
import java.util.Formatter;
import java.util.List;

public class CommonUtil {

    public static List<String> dictSort(String... strArr){
        List<String> list = Arrays.asList(strArr);
        Collections.sort(list);
        return list;
    }

    public static String listToString(List<String> list){
        if(list == null || list.size() == 0){
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (String str : list) {
            sb.append(str);
        }
        return sb.toString();
    }

    public static String sha1(String input, String charset) {
        try {
            if (input == null) {
                return null;
            }
            MessageDigest md;
            md = MessageDigest.getInstance("SHA-1");
            return hexFormat(md.digest(charset==null?input.getBytes():input.getBytes(charset)));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String hexFormat(byte hash[]){
        Formatter formatter = new Formatter();
        try {
            for (byte b : hash) {
                formatter.format("%02x", b);
            }
            return formatter.toString();

        } finally {
            formatter.close();
        }
    }



}
