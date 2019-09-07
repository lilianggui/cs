package com.llg.test;

import java.text.DecimalFormat;

public class TestDoubleFormat {
    public static void main(String ac[]){
        DecimalFormat    df   = new DecimalFormat("######0.00");

        double d1 = 3.23456;
        double d2 = 0.0;
        double d3 = 2.0;
        df.format(d1);
        System.out.println(df.format(d2));
    }
}
