package com.example.tecknet.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * this class has function how check if the user enter valid details
 * like: if he enter currect phone number like :"1234567890" and not "2fd3sdssd3"
 * or email address like : "username@gmail/walla/... .com
 */
public abstract class ValidInputs {

    public static boolean valid_email(String email){
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean valid_phone(String phone){
        Pattern p = Pattern.compile("(05)?[0-9]{8}");
        Matcher m = p.matcher(phone);
        return (m.find() && m.group().equals(phone));
    }

    public static boolean valid_institution_num(String insNum){

        for(int i = 0 ;i < insNum.length() ; i++){
            if(insNum.charAt(i)>'9' || insNum.charAt(i) <'0') return  false;
        }
        return true;
    }
    public static boolean valid_ins_phone_number(String phone){
        Pattern p = Pattern.compile("(0)?[0-9]{8}");
        Matcher m = p.matcher(phone);
        return (m.find() && m.group().equals(phone));
    }
}
