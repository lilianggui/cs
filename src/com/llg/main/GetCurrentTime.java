package com.llg.main;

import com.ib.client.Contract;
import com.ib.client.Order;
import com.ib.controller.ApiController;
import com.llg.utils.Common;
import com.llg.utils.PublicMethod;

import java.util.Date;

public class GetCurrentTime implements Runnable{
    private boolean IsOpenTimePassed = false;
    private Main main = Main.getInstance();

    @Override
    public void run() {
        ApiController ac = main.controller();
        while(true){
            PublicMethod.waiteConnect(ac);
            ac.reqCurrentTime(new ApiController.ITimeHandler(){
                @Override
                public void currentTime(long l) {
                    Const.SERVICE_CURRENT_TIME = l;//刷新服务器当前时间
                    int currentTime = PublicMethod.getTimeInt(l);
                    int closeTime = PublicMethod.getCloseTime();
                    int firstEXIT = closeTime + 100 - Const.PREFERENCES_DATA.getPositionExitAverage()*100;
                    if(currentTime >= firstEXIT && currentTime <= closeTime){
                        if(Const.IS_LONG_ENTRIED == 1 && Const.IS_SHORT_ENTRIED == 0){
                            if(Const.LONG_AUTO_EXIT == 1 && Const.IS_SENT_AUTO_EXIT_ORDER == 0){
                                Const.IS_SENT_AUTO_EXIT_ORDER = 1;
                                new Thread(new SendAutoExitOrder("long")).start();
                            }
                        }else if(Const.IS_LONG_ENTRIED == 0 && Const.IS_SHORT_ENTRIED == 1){
                            if(Const.SHORT_AUTO_EXIT == 1 && Const.IS_SENT_AUTO_EXIT_ORDER == 0){
                                Const.IS_SENT_AUTO_EXIT_ORDER = 1;
                                new Thread(new SendAutoExitOrder("short")).start();
                            }
                        }
                    }

                    main.getTimeLabel().setText(Const.SF6.format(new Date(l*1000)));
                    if(!IsOpenTimePassed){//如果已经超过开盘时间，就没必要做过多的运算和判断了
                        Integer openTime = Integer.parseInt(Const.PREFERENCES_DATA.getMarketOpenTime().replaceAll(":",""));
                        if(currentTime >= openTime && currentTime <= openTime + 5){
                            PublicMethod.resetMode();//重置模式
                            PublicMethod.resetTradeDetailsData();
                        }else if(currentTime > openTime + 5){
                            IsOpenTimePassed = true;
                        }
                    }
                }
            });
            PublicMethod.waite(1000);
        }
    }

    private  class SendAutoExitOrder implements Runnable{
        private String isLongOrShort;
        public SendAutoExitOrder(String isLongOrShort){
            this.isLongOrShort = isLongOrShort;
        }
        @Override
        public void run() {
            sendAllOrder(isLongOrShort);
        }
    }

    private void sendAllOrder(String isLongOrShort){
        //全部合约数量
        int allPositions = Const.TRADEMANAGERDATA.getuPTRounded().intValue();


        int firstPositions = 0;
        int secondPositions = 0;
        int thirdPositions = 0;
        if(allPositions > 0){
            firstPositions = (int)Math.floor(allPositions/3.0);
            secondPositions = (int)Math.floor(allPositions/3.0);
            thirdPositions =  allPositions - firstPositions -secondPositions;
        }


        //发第一次平仓之前取消所有订单
        Main.getInstance().controller().cancelAllOrders();
        sendOrder(isLongOrShort,firstPositions);

        //延时1分
        PublicMethod.waite(60000);

        sendOrder(isLongOrShort,secondPositions);

        //延时45秒
        PublicMethod.waite(60000 - Integer.parseInt(Const.PREFERENCES_DATA.getLastPositionExit())*1000);
        sendOrder(isLongOrShort,thirdPositions);

    }

    private void sendOrder(String isLongOrShort,double positions){
        if(isLongOrShort.equals("long") && Const.LONG_AUTO_EXIT == 0)return;
        if(isLongOrShort.equals("short") && Const.SHORT_AUTO_EXIT == 0)return;
        String action = getAction(isLongOrShort);
        if(positions > 0){
            Order order = buildOrder(action,positions);
            Contract contract = Common.buildContract("NQ");
            Common.sendOrderApi(contract,order);

        }


    }

    private String getAction(String isLongOrShort){
        if(isLongOrShort.equals("long")){
            return "SELL";
        }else{
            return "BUY";
        }
    }

    private Order buildOrder(String action, double quantity){
        Order order = new Order();
        order.account(Const.PREFERENCES_DATA.getAccount());
        order.action(action);
        order.totalQuantity(quantity);
        order.orderType("MKT");
        order.tif("DAY");
        order.transmit(true);//传送
        return order;
    }

}
