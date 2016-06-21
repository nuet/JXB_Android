package com.lenso.jixiangbao.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Chung on 2016/6/21.
 */
public class CheckPassword {
    public static boolean check(String password){
        if(password.length() < 8){
            return false;
        }
        Pattern p = Pattern.compile("^[0-9a-zA-Z_]+$");
        Matcher m = p.matcher(password);
        return m.matches();
    }
}
