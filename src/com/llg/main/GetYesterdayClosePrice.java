package com.llg.main;

import com.ib.controller.ApiController;
import com.llg.utils.PublicMethod;

public class GetYesterdayClosePrice implements Runnable{
    private Main main;
    private ApiController ac;

    public GetYesterdayClosePrice(Main main){
        this.main = main;
        this.ac = main.controller();
    }
    @Override
    public void run() {
        PublicMethod.waiteConnect(this.ac);
        while(Const.PREVIOUS_CLOSE == 0){
            this.main.getYesterdayClosePrice();
            PublicMethod.waite(500);
        }
    }
}
