package com.llg.main;

import com.ib.controller.ApiController;
import com.llg.utils.PublicMethod;

public class GetHighLowPrice implements Runnable{
    private Main main;
    private ApiController ac;

    public GetHighLowPrice(Main main){
        this.main = main;
        this.ac = main.controller();
    }
    @Override
    public void run() {
        PublicMethod.waiteConnect(this.ac);
        while(true){
            this.main.getHighLowPrice();
            PublicMethod.waite(1000);
        }
    }
}
