package com.llg.test;

import com.llg.main.Const;

import java.math.BigDecimal;

public class TestDecimal {
    public static void main(String argvs[]){
        double result = new BigDecimal(9).divide(new BigDecimal(3),3,BigDecimal.ROUND_HALF_DOWN).doubleValue();
        System.out.println(Const.DECIMAL_FORMAT3.format(result));



    }


}
