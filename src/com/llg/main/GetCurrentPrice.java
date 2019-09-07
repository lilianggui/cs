package com.llg.main;

import com.ib.controller.ApiController;
import com.llg.utils.PublicMethod;

public class GetCurrentPrice implements Runnable{
    private Main main;
    private ApiController apiController;
    public GetCurrentPrice(Main main){
        this.main = main;
        this.apiController = this.main.controller();
    }
    @Override
    public void run(){
        while(true){
            PublicMethod.waiteConnect(this.apiController);
            PublicMethod.waite(100);
            this.main.getCurrentData();//获取现价，今天最高最低
            try {
                while(this.apiController.client().isConnected()){
                    //等待失去连接，用户按下disConnect时跳出循环，等待按下connect
                    Thread.sleep(300);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
