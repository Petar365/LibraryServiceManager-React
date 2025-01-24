package com.example.libraryservicemanager.utils;

public class EmailUtils {

    public static String getEmailMessage(String name,String host,String key){
        return "Hello " + name + ",\n\nYour new account has been created.Please click on the link below to verify your account.\n\n"+
                getVerification(host,key) +"\n\nThe Support Team";
    }

    public static String getResetPasswordMessage(String name,String host,String key){
        return "Hello" + name + ",\n\nYour new account has been created.Please click on the link below to verify your account.\n\n"+
                getResetPassword(host,key) +"\n\nThe Support Team";
    }
    public static String getVerification(String host, String key) {
        return host + "/user/verify/account?key=" + key;
    }

    public static String getResetPassword(String host, String key) {
        return host + "/user/verify/password?token=" + key;
    }
}
