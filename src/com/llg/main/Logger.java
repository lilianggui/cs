package com.llg.main;

import com.ib.controller.ApiConnection;

public class Logger implements ApiConnection.ILogger {
    @Override
    public void log(String s) {
        //System.out.println(s);
    }
}
