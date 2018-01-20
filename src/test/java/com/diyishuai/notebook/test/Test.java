package com.diyishuai.notebook.test;

public class Test {

    public static void main(String[] args) {
        String s = "asjhbd|ajsbdjasbd|aksjbdkjad";
        String[] split = s.split("\\|");
        for (String s1 : split) {
            System.out.println(s1);
        }
    }

}
