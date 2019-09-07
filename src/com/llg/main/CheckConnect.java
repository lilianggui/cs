package com.llg.main;

import com.ib.controller.ApiController;
import com.llg.utils.PublicMethod;

import java.awt.*;

public class CheckConnect implements Runnable{
    private Main main;
    private boolean isConnect = false;
    public CheckConnect(Main main){
        this.main = main;
    }
    @Override
    public void run() {
        ApiController ac = this.main.controller();
        while(true){
            //检查连接状态
            if(ac.client().isConnected()){
                if(!isConnect){
                    isConnect = true;
                    this.main.getQuoteOval().setColor(Color.GREEN);
                    this.main.getQuoteOval().repaint();
                }
            }else{
                if(isConnect){
                    isConnect = false;
                    this.main.getQuoteOval().setColor(Color.RED);
                    this.main.getQuoteOval().repaint();
                }
            }
            //每500ms检查一遍
            PublicMethod.waite(500);
        }
    }
}
