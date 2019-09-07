package com.llg.test;

import com.llg.main.Const;

import java.util.Calendar;

public class TestCal {
    public static void main(String[] aa){
        Calendar c = Calendar.getInstance();
//        c.add(Calendar.DAY_OF_YEAR,-1);//昨天数据
        c.set(Calendar.HOUR_OF_DAY,23);
        c.set(Calendar.MINUTE,59);
        c.set(Calendar.SECOND,59);
        String endDate = Const.SF3.format(c.getTime());
        System.out.println(endDate);
    }
}
