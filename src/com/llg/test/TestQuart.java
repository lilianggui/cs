package com.llg.test;

import com.llg.main.Const;

public class TestQuart {

    public static void main(String[] args){
        double d = 0.7500001;
        double result = ceilQuart(d);
        System.out.println(result);
    }

    public static double ceilQuart(double d){
        double result = Math.ceil(d * 4)/4.0;
        return Double.parseDouble(Const.DECIMAL_FORMAT1.format(result));
    }

    public static double floorQuart(double d){
        double result = Math.floor(d * 4)/4.0;
        return Double.parseDouble(Const.DECIMAL_FORMAT1.format(result));
    }
}
