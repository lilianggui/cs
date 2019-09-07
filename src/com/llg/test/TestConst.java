package com.llg.test;

import com.llg.main.Const;

import java.io.File;
import java.util.Date;

public class TestConst {
    public static void main(String argvs[]){
        System.out.println(new File(Const.class.getResource("/").getPath()).getParentFile().getPath());
    }

}
