package com.llg.utils;

import com.ib.client.*;
import com.ib.controller.ApiController;
import com.llg.main.Const;
import com.llg.main.HistoryData;
import com.llg.main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;
import java.util.Date;

public class Common {
    private OvalPanel quoteOval;
    private JLabel timeLabel;

    private OvalPanel longAutoExit;
    private OvalPanel shortAutoExit;

    public JPanel getOrderPanelPanel(){
        JPanel orderPanelPanel = new JPanel();

        JPanel titleP = new JPanel();

        JLabel t1 = new JLabel("COMBO ORDER");
        JLabel t2 = new JLabel("AUTO EXIT");
        t1.setOpaque(true);
        t1.setFont(new Font("Arial", Font.BOLD, 18));
        t1.setForeground(Color.BLACK);
        t1.setBounds(0,0,170,20);
        t2.setOpaque(true);
        t2.setFont(new Font("Arial", Font.BOLD, 18));
        t2.setForeground(Color.BLACK);
        t2.setBounds(187,0,200,20);
        titleP.add(t1);
        titleP.add(t2);
        titleP.setLayout(null);
        titleP.setBounds(5,0,600,20);

        JPanel buttonPanel = new JPanel();

        HtmlButton sendLongComboOrderBtn = new HtmlButton( "Send") {
            @Override protected void actionPerformed() {
                int cutOffTime = Integer.parseInt(Const.PREFERENCES_DATA.getEntryCutOffTime().replaceAll(":",""));
                int closeTime = Integer.parseInt(Const.PREFERENCES_DATA.getMarketCloseTime().replaceAll(":",""));
                Integer currentTime = Integer.parseInt(Const.SF7.format(new Date(Const.SERVICE_CURRENT_TIME*1000)));
                if(Const.IS_SHORT_ENTRIED == 1){
                    return;
                }
                if((currentTime >= cutOffTime && currentTime <= closeTime)){
                    if(Const.TI_MODE == 1){
                        return;
                    }
                }
                sendOrder("long");

            }
        };

        HtmlButton cancelLongComboOrderBtn = new HtmlButton( "Cancel") {
            @Override protected void actionPerformed() {
                if(Const.IS_SHORT_ENTRIED == 1)return;
                Main.getInstance().controller().cancelAllOrders();
            }
        };

        HtmlButton sendLongExitOrderBtn = new HtmlButton( "Enable") {
            @Override protected void actionPerformed() {
                Const.LONG_AUTO_EXIT = 1;
                longAutoExit.setColor(Color.green);
                longAutoExit.repaint();
            }
        };

        HtmlButton cancelLongExitOrderBtn = new HtmlButton( "Cancel") {
            @Override protected void actionPerformed() {
                Const.LONG_AUTO_EXIT = 0;
                longAutoExit.setColor(Color.red);
                longAutoExit.repaint();

            }
        };


        HtmlButton sendShortComboOrderBtn = new HtmlButton( "Send") {
            @Override protected void actionPerformed() {
                int cutOffTime = Integer.parseInt(Const.PREFERENCES_DATA.getEntryCutOffTime().replaceAll(":",""));
                int closeTime = Integer.parseInt(Const.PREFERENCES_DATA.getMarketCloseTime().replaceAll(":",""));
                Integer currentTime = Integer.parseInt(Const.SF7.format(new Date(Const.SERVICE_CURRENT_TIME*1000)));
                if(Const.IS_LONG_ENTRIED == 1){
                    return;
                }
                if((currentTime >= cutOffTime && currentTime <= closeTime)){
                    if(Const.TI_MODE == 1){
                        return;
                    }
                }
                sendOrder("short");
            }
        };

        HtmlButton cancelShortComboOrderBtn = new HtmlButton( "Cancel") {
            @Override protected void actionPerformed() {
                if(Const.IS_LONG_ENTRIED == 1)return;
                Main.getInstance().controller().cancelAllOrders();
            }
        };

        HtmlButton sendShortExitOrderBtn = new HtmlButton( "Enable") {
            @Override protected void actionPerformed() {
                Const.SHORT_AUTO_EXIT = 1;
                shortAutoExit.setColor(Color.green);
                shortAutoExit.repaint();

            }
        };

        HtmlButton cancelShortExitOrderBtn = new HtmlButton( "Cancel") {
            @Override protected void actionPerformed() {
                Const.SHORT_AUTO_EXIT = 0;
                shortAutoExit.setColor(Color.red);
                shortAutoExit.repaint();

            }
        };


        sendLongComboOrderBtn.setBounds(5,0,45,20);
        sendLongComboOrderBtn.setFont(new Font("Arial",Font.BOLD,16));

        cancelLongComboOrderBtn.setBounds(70,0,60,20);
        cancelLongComboOrderBtn.setFont(new Font("Arial",Font.BOLD,16));

        sendLongExitOrderBtn.setBounds(165,0,60,20);
        sendLongExitOrderBtn.setFont(new Font("Arial",Font.BOLD,16));

        cancelLongExitOrderBtn.setBounds(240,0,60,20);
        cancelLongExitOrderBtn.setFont(new Font("Arial",Font.BOLD,16));


        sendShortComboOrderBtn.setBounds(5,32,45,20);
        sendShortComboOrderBtn.setFont(new Font("Arial",Font.BOLD,16));

        cancelShortComboOrderBtn.setBounds(70,32,60,20);
        cancelShortComboOrderBtn.setFont(new Font("Arial",Font.BOLD,16));

        sendShortExitOrderBtn.setBounds(165,32,60,20);
        sendShortExitOrderBtn.setFont(new Font("Arial",Font.BOLD,16));

        cancelShortExitOrderBtn.setBounds(240,32,60,20);
        cancelShortExitOrderBtn.setFont(new Font("Arial",Font.BOLD,16));


        buttonPanel.add(sendLongComboOrderBtn);
        buttonPanel.add(cancelLongComboOrderBtn);
        buttonPanel.add(sendLongExitOrderBtn);
        buttonPanel.add(cancelLongExitOrderBtn);

        buttonPanel.add(sendShortComboOrderBtn);
        buttonPanel.add(cancelShortComboOrderBtn);
        buttonPanel.add(sendShortExitOrderBtn);
        buttonPanel.add(cancelShortExitOrderBtn);

        this.longAutoExit = new OvalPanel(Color.RED);
        this.longAutoExit.setBounds(306,3,14,14);

        this.shortAutoExit = new OvalPanel(Color.RED);
        this.shortAutoExit.setBounds(306,35,14,14);
        buttonPanel.add(this.longAutoExit);
        buttonPanel.add(this.shortAutoExit);


        buttonPanel.setLayout(null);
        buttonPanel.setBounds(10,28,325,60);

        orderPanelPanel.add(titleP);
        orderPanelPanel.add(buttonPanel);
        orderPanelPanel.setLayout(null);
        orderPanelPanel.setBounds(680,273,340,90);

        return orderPanelPanel;

    }

    private void sendOrder(String longOrShort){
        Contract contract = buildContract("NQ");
        String action = "BUY";
        Double lmtPrice = 0d;
        Double auxPrice = 0d;
        Double child_lmtPrice = 0d;
        Double child_auxPrice = 0d;
        if("short".equals(longOrShort)){
            action = "SELL";
            lmtPrice = Const.ShortEntry - Const.PREFERENCES_DATA.getSlippage();
            auxPrice = (double)Const.ShortEntry;
            child_lmtPrice = Const.ShortStoploss + Const.PREFERENCES_DATA.getSlippage();
            child_auxPrice = (double)Const.ShortStoploss;
        }else if("long".equals(longOrShort)){
            lmtPrice = Const.LongEntry + Const.PREFERENCES_DATA.getSlippage();
            auxPrice = (double)Const.LongEntry;
            child_lmtPrice = Const.LongStoploss - Const.PREFERENCES_DATA.getSlippage();
            child_auxPrice = (double)Const.LongStoploss;

        }
        int NQCount = (int)Math.floor(Const.TRADEMANAGERDATA.getuPTRounded());

        if(NQCount > 0){
            Order parent_order = buildOrder(action,NQCount,lmtPrice,auxPrice);
            if(Const.TI_MODE == 1){
                sendOrderApi(contract,parent_order);
                PublicMethod.waite(100);
            }

            String childAction = getChildAction(action);
            Order child_order = buildOrder(childAction,parent_order.totalQuantity(),child_lmtPrice,child_auxPrice);
            child_order.parentId(parent_order.orderId());
            sendOrderApi(contract.clone(),child_order);

        }

    }

    private String getChildAction(String action){
        if("BUY".equals(action)){
            return "SELL";
        }else{
            return "BUY";
        }
    }

    private Order buildOrder(String action,double quantity,double lmtPrice,double auxPrice){
        Order order = new Order();
        order.account(Const.PREFERENCES_DATA.getAccount());
        order.action(action);
        order.totalQuantity(quantity);
        order.orderType("STP LMT");
        order.lmtPrice(lmtPrice);
        order.auxPrice(auxPrice);
        order.tif("DAY");
        order.transmit(true);//传送
        return order;
    }

    public static Contract buildContract(String symbol){
        Contract contract = new Contract();
        contract.symbol(symbol);
        contract.secType("FUT");
        contract.lastTradeDateOrContractMonth(Const.PREFERENCES_DATA.getContract());
        contract.exchange("GLOBEX");
        return contract;
    }

    public static void sendOrderApi(Contract contract,Order order){
        Main.getInstance().controller().placeOrModifyOrder(contract, order, new ApiController.IOrderHandler() {
            @Override public void orderState(OrderState orderState) {
            }
            @Override public void orderStatus(OrderStatus status, double filled, double remaining, double avgFillPrice, long permId, int parentId, double lastFillPrice, int clientId, String whyHeld) {
            }
            @Override public void handle(int errorCode, final String errorMsg) {
            }
        });
    }

    public JPanel getConnectPanel(){
        JPanel connectPanel = new JPanel();
        JLabel jl1 = new JLabel("Host：");
        JLabel jl2 = new JLabel("Port：");

        JPanel jp1 = new JPanel();
        JPanel jp2 = new JPanel();

        jp1.setLayout(null);
        jp1.setBounds(0,0,110,14);
        JTextField hostField = new JTextField("127.0.0.1");
        JTextField portField = new JTextField("7496");
        jl1.setOpaque(true);
        jl1.setBounds(0,0,39,14);
        hostField.setBounds(39,0,70,14);
        jp1.add(jl1);
        jp1.add(hostField);

        jp2.setLayout(null);
        jp2.setBounds(117,0,90,14);
        jl2.setOpaque(true);
        jl2.setBounds(0,0,38,14);
        portField.setBounds(39,0,50,14);
        jp2.add(jl2);
        jp2.add(portField);

//        JButton connectBtn = new JButton("connect");
//        connectBtn.setBounds(210,0,50,26);
//        JButton disConnectBtn = new JButton("disConnect");
//        disConnectBtn.setBounds(263,0,80,26);

        HtmlButton connectBtn = new HtmlButton( "Connect") {
            @Override protected void actionPerformed() {
                //ac.connect("127.0.0.1", 7496, 0,"");
                Main main = Main.getInstance();
                ApiController ac = main.controller();
                if(!ac.client().isConnected()){
                    Object contract = main.getSymbolTable().getModel().getValueAt(0,0);
                    if(contract == null || contract.toString().equals("")){
//                        JOptionPane.showMessageDialog(null,"Enter Contract Month");

                        JButton btn = new JButton("OK");
                        JOptionPane pane = new JOptionPane("Enter Contract Symbol", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_CANCEL_OPTION);
                        pane.setOptions(new JButton[] { btn });
                        pane.setInitialValue(btn);

                        final JDialog dialog = pane.createDialog("Warning");
                        dialog.setModal(true);

                        btn.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                dialog.setVisible(false);
                            }
                        });

                        dialog.setVisible(true);


                    }else{
                        ac.connect(hostField.getText(),Integer.parseInt(portField.getText()),0,"");
                        //昨天收盘
//                        try{
//                            Thread.sleep(500);
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }
//                        HistoryData historicalData = new HistoryData(null,Main.getMain().getDayMaxTable(),null);
//                        Main m = Main.getMain();
//                        m.historicalDataInit(m.m_contract);
//                        Calendar c = Calendar.getInstance();
//                        c.add(Calendar.DAY_OF_YEAR,-1);//昨天数据
//                        c.set(Calendar.HOUR_OF_DAY,16);
//                        c.set(Calendar.MINUTE,30);
//                        c.set(Calendar.SECOND,0);
//                        String endDate = Const.SF3.format(c.getTime());
//                        ac.reqHistoricalData(m.m_contract, endDate, 1, Types.DurationUnit.DAY, Types.BarSize._1_day,
//                                Types.WhatToShow.TRADES, true, historicalData);

                    }
                }else{
                    //Main.getMain().getApiData();
                }
            }
        };
        HtmlButton disConnectBtn = new HtmlButton( "Disconnect") {
            @Override protected void actionPerformed() {
                Main.getInstance().controller().disconnect();
            }
        };
        connectBtn.setBounds(215,0,50,14);
        disConnectBtn.setBounds(268,0,65,14);

        connectPanel.add(jp1);
        connectPanel.add(jp2);
        connectPanel.add(connectBtn);
        connectPanel.add(disConnectBtn);

        connectPanel.setLayout(null);
        connectPanel.setBounds(1030,376,350,14);
        return connectPanel;
    }

    public JPanel getSignalPanel(){
        JPanel signalPanel = new JPanel();
        JLabel jl1 = new JLabel("Connection Status");
        this.timeLabel = new JLabel("");

        JPanel jp1 = new JPanel();
        JPanel jp2 = new JPanel();
        jp1.setLayout(null);
        jp1.setBounds(0,0,125,30);
        this.quoteOval = new OvalPanel(Color.RED);
        this.quoteOval.setBounds(0,0,14,14);
        jl1.setOpaque(true);
        jl1.setBounds(20,1,110,14);
        jp1.add(this.quoteOval);
        jp1.add(jl1);

        jp2.setLayout(null);
        jp2.setBounds(125,0,106,14);
//        JPanel oval2 = new OvalPanel(Color.RED);
//        oval2.setBounds(0,0,14,14);
        this.timeLabel.setOpaque(true);
        this.timeLabel.setBounds(3,1,80,14);
//        jp2.add(oval2);
        jp2.add(this.timeLabel);

        signalPanel.add(jp1);
        signalPanel.add(jp2);

        signalPanel.setLayout(null);
        signalPanel.setBounds(10,374,300,30);
        return signalPanel;
    }

    public OvalPanel getQuoteOval() {
        return this.quoteOval;
    }

    public void setOval1(OvalPanel quoteOval) {
        this.quoteOval = quoteOval;
    }

    public JLabel getTimeLabel() {
        return timeLabel;
    }

    public void setTimeLabel(JLabel timeLabel) {
        this.timeLabel = timeLabel;
    }


}

